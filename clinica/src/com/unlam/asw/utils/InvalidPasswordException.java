package com.unlam.asw.utils;

//Class for user-defined InvalidPasswordException 
public class InvalidPasswordException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5822677962405241487L;
	int passwordConditionViolated = 0;

	public InvalidPasswordException(int conditionViolated) {
		super("");
		// Seteo el int de la excepción para saber de que error se trata
		passwordConditionViolated = conditionViolated;
	}

	public String printMessage() {
		switch (passwordConditionViolated) {

		// La longitud de la contraseña debe ser entre 8 y 15 caracteres
		case 1:
			return ("La longitud de la contraseña debe ser entre 8 y 15 caracteres");

		// La contraseña no debe contener espacio
		case 2:
			return ("La contraseña no debe contener espacio");

		// La contraseña debe contener al menos un digito (0-9)
		case 3:
			return ("La contraseña debe contener al menos un digito (0-9)");

		// La contraseña debe contener al menos una letra mayuscula (A-Z)
		case 4:
			return ("La contraseña debe contener al menos una letra mayuscula (A-Z)");

		// La contraseña debe contener al menos una letra minuscula (A-Z)
		case 5:
			return ("La contraseña debe contener al menos una letra minuscula (A-Z)");
		}

		return ("");
	}
}
