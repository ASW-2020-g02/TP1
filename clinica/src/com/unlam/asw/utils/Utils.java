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
}
