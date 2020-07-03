package com.unlam.asw.utils;

import java.util.Base64;

public class Criptografia {

	private static Criptografia singleton;
	private static String k = "asw2020";

	public static Criptografia obtenerInstancia() {
		// En caso de no existir una instancia de la clase, debo crear una
		if (singleton == null) {
			singleton = new Criptografia();
		}
		// Devuelvo una instancia de la clase
		return singleton;
	}

	public String encrypt(String str) throws Exception {
		// Utilizo base 64 para codificar
		return Base64.getEncoder().encodeToString(str.getBytes("UTF-8"));
	}

	public String decrypt(String str) throws Exception {
		// Utilizo base 64 para decodificar
		return new String(Base64.getDecoder().decode(str));
	}

}
