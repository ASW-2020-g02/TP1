package com.unlam.asw.entities;

public class Paciente {
	private int codigo;
	private String nombre;

	public Paciente(String codigo, String nombre) throws Exception {
		setCodigo(codigo);
		setNombre(nombre);
	}

	public int getCodigo() {
		return codigo;
	}

	public final void setCodigo(String codigo) throws Exception {
		try {
			if (codigo == null || codigo.trim().isEmpty())
				throw new NumberFormatException();

			this.codigo = Integer.parseInt(codigo);
		} catch (NumberFormatException e) {
			throw new Exception("El codigo del paciente debe ser un número entero.");
		}
	}

	public String getNombre() {
		return nombre;
	}

	public final void setNombre(String nombre) throws Exception {
		if (nombre != null && !nombre.trim().isEmpty()) {
			if (this.soloCaracteres(nombre))
				this.nombre = nombre;
			else
				throw new Exception("El paciente debe contener caracteres válidos!");
		} else {
			throw new Exception("El paciente debe tener un nombre!");
		}
	}

	private boolean soloCaracteres(String cadena) {
		for (int i = 0; i != cadena.length(); ++i) {
			if (!Character.isLetter(cadena.charAt(i)) && !Character.isWhitespace(cadena.charAt(i)))
				return false;
		}
		return true;
	}
}
