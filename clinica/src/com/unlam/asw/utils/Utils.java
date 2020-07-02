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
		byte[] bytes = SALT.getBytes();

		PBEKeySpec spec = new PBEKeySpec(chars, bytes, ITERATIONS, KEY_LENGTH);

		Arrays.fill(chars, Character.MIN_VALUE);

		try {
			SecretKeyFactory fac = SecretKeyFactory.getInstance(ALGORITHM);
			byte[] securePassword = fac.generateSecret(spec).getEncoded();
			return Optional.of(Base64.getEncoder().encodeToString(securePassword)).get();
		} catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
			return "";
		} finally {
			spec.clearPassword();
		}
	}

	public static boolean esEmailValido(String strEmail) {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return strEmail.matches(regex);
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

		// A continuación, se verifica la cantidad de digitos en la contraseña
		int count = 0;

		// Verifico que tenga algun número, del 0 al 9 inclusive
		for (int i = 0; i <= 9; i++) {

			// Convierto de Integer a String
			String str1 = Integer.toString(i);

			// En caso de que la contraseña contenga un determinado número, se modificara el
			// flag count y se detendra la iteración
			if (password.contains(str1)) {
				count = 1;
				break;
			}
		}

		// Se requiere que la contraseña tenga al menos un digito
		if (count == 0) {
			throw new InvalidPasswordException(3);
		}

		// En esta oportunidad, se contabiliza la ocurrencia de al menos una letra
		// mayuscula
		int countUpperCase = 0;

		// Recorro las letras mayusculas
		for (int i = 65; i <= 90; i++) {

			// Casteo el int a char
			char c = (char) i;

			String str1 = Character.toString(c);
			if (password.contains(str1)) {
				countUpperCase = 1;
			}
		}

		// Se requiere que la contraseña tenga al menos una mayuscula
		if (countUpperCase == 0) {
			throw new InvalidPasswordException(4);
		}

		int countLowerCase = 0;

		// Recorro las letras minusculas
		for (int i = 90; i <= 122; i++) {

			// Casteo el int a char
			char c = (char) i;
			String str1 = Character.toString(c);

			if (password.contains(str1)) {
				countLowerCase = 1;
			}
		}

		// Se requiere que la contraseña tenga al menos una minuscula
		if (countLowerCase == 0) {
			throw new InvalidPasswordException(5);
		}

		return true;
	}
}
