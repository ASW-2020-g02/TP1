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

	/**
	 * Setter del código del paciente. El código debe ser un número entero.
	 * 
	 * @param codigo (string) cadena que será convertida a entero.
	 * @throws Exception En caso de no serlo arroja una excepción. En caso de
	 *                   recibir una cadena vacía, arroja excepción.
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
			throw new Exception("El codigo del paciente debe ser un número entero.");
		}
	}

	public String getNombre() {
		return nombre;
	}

	/**
	 * Setter del nombre. Verifica que el nombre contenga solamente caracteres
	 * válidos.
	 * 
	 * @param nombre (string) cadena de texto que será asignada como nombre al
	 *               paciente, en caso de pasar la validación.
	 * @throws Exception Arroja excepciones en caso de que el nombre contenga
	 *                   caracteres no válidos o en caso de que la cadena recibida
	 *                   sea nula.
	 */
	public final void setNombre(String nombre) throws Exception {
		// Chequeamos que la cadena recibida no sea nula, y además chequeamos que no
		// hayamos recibido
		// una cadena con espacios en blanco
		if (nombre != null && !nombre.trim().isEmpty()) {
			// Ejecutamos el metodo soloCaracteres en la cadena para chequear que contenga
			// unicamente caracteres validos
			if (this.soloCaracteres(nombre))
				this.nombre = nombre;
			else
				throw new Exception("¡El paciente debe contener caracteres válidos!");
		} else {
			throw new Exception("¡El paciente debe tener un nombre!");
		}
	}

	/**
	 * Chequea que la cadena contenga solo caracteres válidos, considerando
	 * caracteres válidos aquellos que sean letras y que no sean whiteSpace
	 * (tabulaciones, linefeed, espacios en blanco, etc). Los números no son
	 * aceptados. Solo se aceptan letras.
	 * 
	 * Retorna true en caso de que no hayan caracteres no validos, y false en caso
	 * de que sí hayan.
	 * 
	 * @param cadena (string) cadena que será comprobada en busca de caracteres no
	 *               válidos.
	 */
	private boolean soloCaracteres(String cadena) {
		// Recorremos la cadena de principio a fin
		for (int i = 0; i != cadena.length(); ++i) {
			// isLetter chequea que la letra sea una letra unicode válida
			if (!Character.isLetter(cadena.charAt(i)))
				// Si el caracter no es una letra, retornamos falso
				return false;
		}
		// Si no se encontraron caracteres no validos, retornamos true
		return true;
	}
}
