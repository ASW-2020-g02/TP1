package com.unlam.asw.utils;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.AbstractListModel;
import javax.swing.JList;

public class Utils {
	private static final int ITERATIONS = 65536;
	private static final int KEY_LENGTH = 512;
	private static final String ALGORITHM = "PBKDF2WithHmacSHA512";
	private static final String SALT = "Ha97m4oFANostwyoBC_jpzzJWjlrhwu8D";

	public static void actualizarLista(JList<String> lista, String[] elementos) {
		lista.setModel(new AbstractListModel<String>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -2834588284593084928L;

			// Se setean los elementos como los values de la lista
			String[] values = elementos;

			// Metodos especificos de la lista
			public int getSize() {
				return values.length;
			}

			public String getElementAt(int index) {
				return values[index];
			}
		});
	}

	public static String hashPassword(String password) {
		char[] chars = password.toCharArray();
		// Obtengo el array de bytes del salt
		byte[] bytes = SALT.getBytes();

		PBEKeySpec spec = new PBEKeySpec(chars, bytes, ITERATIONS, KEY_LENGTH);

		Arrays.fill(chars, Character.MIN_VALUE);

		try {
			SecretKeyFactory fac = SecretKeyFactory.getInstance(ALGORITHM);
			// Obtengo la contraseña, en forma de bytes
			byte[] securePassword = fac.generateSecret(spec).getEncoded();
			// Obtengo la contraseña hasheada
			return Optional.of(Base64.getEncoder().encodeToString(securePassword)).get();
		} catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
			return "";
		} finally {
			spec.clearPassword();
		}
	}

	public static boolean esEmailValido(String strEmail) {
		// Tomo el regex y valido que se ajuste a mi string
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return strEmail.matches(regex);
	}

	private static int obtenerCantContain(String password, int limiteInferior, int limiteSuperior, boolean esLetra) {
		int count = 0;
		// Verifico que tenga algun número, del 0 al 9 inclusive
		for (int i = limiteInferior; i <= limiteSuperior; i++) {

			// Convierto de Integer a String
			String str1 = esLetra ? Character.toString((char) i) : Integer.toString(i);

			// En caso de que la contraseña contenga un determinado número, se modificara el
			// flag count y se detendra la iteración
			if (password.contains(str1)) {
				count = 1;
				break;
			}
		}
		return count;
	}

	public static boolean esPasswordValida(String password) throws InvalidPasswordException {
		// Se verifica que la contraseña tenga entre 8 y 15 caracteres
		if (!((password.length() >= 8) && (password.length() <= 15))) {
			throw new InvalidPasswordException(1);
		}

		// Se verifica que no contenga espacios
		if (password.contains(" ")) {
			throw new InvalidPasswordException(2);
		}

		// Se requiere que la contraseña tenga al menos un digito
		if (obtenerCantContain(password, 0, 9, false) == 0) {
			throw new InvalidPasswordException(3);
		}

		// Se requiere que la contraseña tenga al menos una mayuscula
		if (obtenerCantContain(password, 65, 90, true) == 0) {
			throw new InvalidPasswordException(4);
		}

		// Se requiere que la contraseña tenga al menos una minuscula
		if (obtenerCantContain(password, 90, 122, true) == 0) {
			throw new InvalidPasswordException(5);
		}

		return true;
	}
}
