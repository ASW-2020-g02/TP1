package com.unlam.asw.entities;

public class Paciente {
	private int codigo;
	private String nombre;

	public Paciente(String codigo, String nombre) throws Exception {
		// Debo setear el c�digo y nombre
		// Cada atributo necesita ser validado
		setCodigo(codigo);
		setNombre(nombre);
	}

	public int getCodigo() {
		return codigo;
	}

	/**
	 * Setter del c�digo del paciente. El c�digo debe ser un n�mero entero.
	 * 
	 * @param codigo (string) cadena que ser� convertida a entero.
	 * @throws Exception En caso de no serlo arroja una excepci�n. En caso de
	 *                   recibir una cadena vac�a, arroja excepci�n.
	 */
	public final void setCodigo(String codigo) throws Exception {
		try {
			// Chequeamos no recibir una cadena nula ni una cadena que solo contenga
			// espacios en blanco,
			// caso contrario arrojamos una excepcion de formato
			if (codigo == null || codigo.trim().isEmpty())
				throw new NumberFormatException();
			// Convertimos la cadena a un entero y lo asignamos al atributo codigo del
			// objeto
			this.codigo = Integer.parseInt(codigo);
		} catch (NumberFormatException e) {
			throw new Exception("El codigo del paciente debe ser un n�mero entero.");
		}
	}

	public String getNombre() {
		return nombre;
	}

	/**
	 * Setter del nombre. Verifica que el nombre contenga solamente caracteres
	 * v�lidos.
	 * 
	 * @param nombre (string) cadena de texto que ser� asignada como nombre al
	 *               paciente, en caso de pasar la validaci�n.
	 * @throws Exception Arroja excepciones en caso de que el nombre contenga
	 *                   caracteres no v�lidos o en caso de que la cadena recibida
	 *                   sea nula.
	 */
	public final void setNombre(String nombre) throws Exception {
		// Chequeamos que la cadena recibida no sea nula, y adem�s chequeamos que no
		// hayamos recibido
		// una cadena con espacios en blanco
		if (nombre != null && !nombre.trim().isEmpty()) {
			// Ejecutamos el metodo soloCaracteres en la cadena para chequear que contenga
			// unicamente caracteres validos
			if (this.soloCaracteres(nombre))
				this.nombre = nombre;
			else
				throw new Exception("�El paciente debe contener caracteres v�lidos!");
		} else {
			throw new Exception("�El paciente debe tener un nombre!");
		}
	}

	/**
	 * Chequea que la cadena contenga solo caracteres v�lidos, considerando
	 * caracteres v�lidos aquellos que sean letras y que no sean whiteSpace
	 * (tabulaciones, linefeed, espacios en blanco, etc). Los n�meros no son
	 * aceptados. Solo se aceptan letras.
	 * 
	 * Retorna true en caso de que no hayan caracteres no validos, y false en caso
	 * de que s� hayan.
	 * 
	 * @param cadena (string) cadena que ser� comprobada en busca de caracteres no
	 *               v�lidos.
	 */
	private boolean soloCaracteres(String cadena) {
		// Recorremos la cadena de principio a fin
		for (int i = 0; i != cadena.length(); ++i) {
			// isLetter chequea que la letra sea una letra unicode v�lida
			if (!Character.isLetter(cadena.charAt(i)) && !Character.isWhitespace(cadena.charAt(i)))
				// Si el caracter no es una letra, retornamos falso
				return false;
		}
		// Si no se encontraron caracteres no validos, retornamos true
		return true;
	}
}
