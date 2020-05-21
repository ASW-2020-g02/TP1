package com.unlam.asw.entities;

public class Medico {
	private int codigo;
	private String nombre;
	private String especialidad;

	public Medico(String codigo, String nombre, String especialidad) throws Exception {
		setCodigo(codigo);
		setNombre(nombre);
		setEspecialidad(especialidad);
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) throws Exception {
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
				throw new Exception("El médico debe contener caracteres válidos!");
		} else {
			throw new Exception("El médico debe tener un nombre!");
		}
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) throws Exception {
		if (especialidad != null && !especialidad.trim().isEmpty())
			this.especialidad = especialidad;
		else
			throw new Exception("El médico debe tener una especialidad.");
	}

	private boolean soloCaracteres(String cadena) {
		for (int i = 0; i != cadena.length(); ++i) {
			if (!Character.isLetter(cadena.charAt(i)) && !Character.isWhitespace(cadena.charAt(i)))
				return false;
		}
		return true;
	}

}
