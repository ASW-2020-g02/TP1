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
			//Chequeamos no recibir una cadena nula ni una cadena que solo contenga espacios en blanco,
			//caso contrario arrojamos una excepcion de formato
			if (codigo == null || codigo.trim().isEmpty())
				throw new NumberFormatException();
			//Asignamos el codigo al atributo del objeto
			this.codigo = Integer.parseInt(codigo);
		} catch (NumberFormatException e) {
			throw new Exception("El codigo del paciente debe ser un n�mero entero.");
		}
	}

	public String getNombre() {
		return nombre;
	}
	
	/** 
	 * Setter del nombre. Verifica que el nombre contenga solamente caracteres v�lidos. 
	 * 
	 * Arroja excepciones en caso de que el nombre contenga caracteres no v�lidos o en caso de que la cadena recibida sea nula.
	 * 
	 * @param nombre (string) cadena de texto que ser� asignada como nombre al paciente, en caso de pasar la validaci�n. 
	 */
	public final void setNombre(String nombre) throws Exception {
		//Chequeamos que la cadena recibida no sea nula, y adem�s chequeamos que no hayamos recibido
		//una cadena con espacios en blanco
		if (nombre != null && !nombre.trim().isEmpty()) {
			//Ejecutamos el metodo soloCaracteres en la cadena para chequear que contenga unicamente caracteres validos
			if (this.soloCaracteres(nombre))
				this.nombre = nombre;
			else
				throw new Exception("El m�dico debe contener caracteres v�lidos!");
		} else {
			throw new Exception("El m�dico debe tener un nombre!");
		}
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) throws Exception {
		//Chequeamos si la cadena recibida es nula o si es solo una cadena que contiene caracteres
		//vacios
		if (especialidad != null && !especialidad.trim().isEmpty())
			this.especialidad = especialidad;
		else
			throw new Exception("El m�dico debe tener una especialidad.");
	}

	/**
	 * Chequea que la cadena contenga solo caracteres v�lidos, considerando caracteres v�lidos
	 * aquellos que sean letras y que no sean whiteSpace (tabulaciones, linefeed, espacios en blanco, etc). 
	 * Los n�meros no son aceptados. Solo se aceptan letras. 
	 * 
	 * Retorna true en caso de que no hayan caracteres no validos, y false en caso de que s� hayan.
	 * 
	 * @param cadena (string) cadena que ser� comprobada en busca de caracteres no v�lidos.
	 */
	private boolean soloCaracteres(String cadena) {
		//Recorremos la cadena de principio a fin
		for (int i = 0; i != cadena.length(); ++i) {
			//isLetter chequea que la letra sea una letra unicode v�lida
			//if (!Character.isLetter(cadena.charAt(i)) && !Character.isWhitespace(cadena.charAt(i)))
			if (!Character.isLetter(cadena.charAt(i)))
				//Si el caracter no es una letra, retornamos falso
				return false;
		}
		//Si no se encontraron caracteres no validos, retornamos true
		return true;
	}
}