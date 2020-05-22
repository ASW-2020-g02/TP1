package com.unlam.asw.entities;

public class Usuario {
	private String nombre;
	private String password;
	private String email;

	public Usuario(String nombre, String password, String email) throws Exception {
		setNombre(nombre);
		setPassword(password);
		setEmail(email);
	}

	public String getNombre() {
		return nombre;
	}

	/**
	 * Setter del nombre del usuario. Recibe una cadena y verifica que no sea vacía ni esté conformada por espacios en blanco.
	 * 
	 * @param nombre (string) cadena que cotiene el nombre del usuario
	 * @throws Exception en caso de que la cadena no pase las verificaciones
	 */
	public final void setNombre(String nombre) throws Exception {
		//Chequeamos que la cadena recibida no sea nula, y además chequeamos que no hayamos recibido
		//una cadena con espacios en blanco
		if (nombre != null && !nombre.trim().isEmpty())
			this.nombre = nombre;
		else
			throw new Exception("El usuario debe tener un nombre!");
	}

	public String getPassword() {
		return password;
	}

	/**
	 * Setter de la contraseña del usuario. Recibe una cadena y verifica que no sea vacía ni esté conformada por espacios en blanco.
	 * 
	 * @param nombre (string) cadena que cotiene el nombre del usuario
	 * @throws Exception en caso de que la cadena no pase las verificaciones
	 */
	public final void setPassword(String password) throws Exception {
		//Chequeamos que la cadena recibida no sea nula, y además chequeamos que no hayamos recibido
		//una cadena con espacios en blanco
		if (password != null && !password.trim().isEmpty())
			this.password = password;
		else
			throw new Exception("El usuario debe tener una contraseña.");
	}

	public String getEmail() {
		return email;
	}

	/**
	 * Setter del email del usuario. Recibe una cadena y verifica que no sea vacía ni esté conformada por espacios en blanco.
	 * 
	 * @param nombre (string) cadena que cotiene el nombre del usuario
	 * @throws Exception en caso de que la cadena no pase las verificaciones
	 */
	public final void setEmail(String email) throws Exception {
		//Chequeamos que la cadena recibida no sea nula, y además chequeamos que no hayamos recibido
		//una cadena con espacios en blanco
		if (email != null && !email.trim().isEmpty())
			this.email = email;
		else
			throw new Exception("El usuario debe tener un email.");
	}
}
