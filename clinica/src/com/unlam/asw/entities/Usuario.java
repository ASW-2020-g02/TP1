package com.unlam.asw.entities;

public class Usuario {
	private String nombre;
	private String password;
	private String email;

	/**
	 *
	 * @param nombre
	 * @param password
	 * @param email
	 * @throws Exception
	 */
	public Usuario(String nombre, String password, String email) throws Exception {
		setNombre(nombre);
		setPassword(password);
		setEmail(email);
	}

	/**
	 *
	 * @return
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 *
	 * @param nombre
	 * @throws Exception
	 */
	public final void setNombre(String nombre) throws Exception {
		if (nombre != null && !nombre.trim().isEmpty())
			this.nombre = nombre;
		else
			throw new Exception("El usuario debe tener un nombre!");
	}

	/**
	 *
	 * @return String
	 */
	public String getPassword() {
		return password;
	}

	/**
	 *
	 * @param password
	 * @throws Exception
	 */
	public final void setPassword(String password) throws Exception {
		if (password != null && !password.trim().isEmpty())
			this.password = password;
		else
			throw new Exception("El usuario debe tener una contraseña.");
	}

	/**
	 *
	 * @return String
	 */
	public String getEmail() {
		return email;
	}

	/**
	 *
	 * @param email
	 * @throws Exception
	 */
	public final void setEmail(String email) throws Exception {
		if (email != null && !email.trim().isEmpty())
			this.email = email;
		else
			throw new Exception("El usuario debe tener un mail.");
	}
}