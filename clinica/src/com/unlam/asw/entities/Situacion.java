package com.unlam.asw.entities;

public class Situacion {
	private int id;
	private int codPaciente;
	private int codMedico;
	private String diagnostico;

	public Situacion(String id, String codPaciente, String codMedico, String diagnostico) throws Exception {
		setDiagnostico(diagnostico);
		setCodMedico(codMedico);
		setCodPaciente(codPaciente);
		setId(id);
	}

	public Situacion(int id, int codPaciente, int codMedico, String diagnostico) {
		this.id = id;
		this.codPaciente = codPaciente;
		this.codMedico = codMedico;
		this.diagnostico = diagnostico;
	}

	public int getId() {
		return id;
	}

	/**
	 * Setter del ID de la situación. Recibe una cadena y verifica que pueda
	 * convertirse a número entero.
	 * 
	 * @param id (string) cadena que contiene el id de la situacion
	 * @throws Exception en caso de que la cadena recibida no pueda convertirse a
	 *                   número entero.
	 */
	public final void setId(String id) throws Exception {
		try {
			// Chequeamos que la cadena recibida no sea nula, y además chequeamos que no
			// hayamos recibido
			// una cadena con espacios en blanco
			if (id == null || id.trim().isEmpty())
				throw new NumberFormatException();
			// Convertimos la cadena a un entero y la asignamos al atributo id del objeto
			this.id = Integer.parseInt(id);
		} catch (NumberFormatException e) {
			throw new Exception("El ID debe ser un número válido.");
		}
	}

	public int getCodPaciente() {
		return codPaciente;
	}

	/**
	 * Setter del código del paciente. Recibe una cadena y verifica que pueda
	 * convertirse a número entero.
	 * 
	 * @param codPaciente (string) cadena que contiene el código del paciente
	 * @throws Exception en caso de que la cadena recibida no pueda convertirse a
	 *                   número entero.
	 */
	public final void setCodPaciente(String codPaciente) throws Exception {
		try {
			// Chequeamos que la cadena recibida no sea nula, y además chequeamos que no
			// hayamos recibido
			// una cadena con espacios en blanco
			if (codPaciente == null || codPaciente.trim().isEmpty())
				throw new NumberFormatException();
			// Convertimos la cadena a un entero y la asignamos al atributo codPaciente del
			// objeto
			this.codPaciente = Integer.parseInt(codPaciente);
		} catch (NumberFormatException e) {
			throw new Exception("El codigo del paciente debe ser un número entero.");
		}
	}

	public int getCodMedico() {
		return codMedico;
	}

	/**
	 * Setter del código del médico. Recibe una cadena y realiza verificaciones
	 * sobre la misma.
	 * 
	 * @param codMedico (string) cadena que recibe y que será convertida a entero
	 * @throws Exception arroja una excepción en caso de que el número no sea entero
	 */
	public final void setCodMedico(String codMedico) throws Exception {
		try {
			// Chequeamos que la cadena recibida no sea nula, y además chequeamos que no
			// hayamos recibido
			// una cadena con espacios en blanco
			if (codMedico == null || codMedico.trim().isEmpty())
				throw new NumberFormatException();
			// Convertimos la cadena a un entero y la asignamos al atributo codMedico del
			// objeto
			this.codMedico = Integer.parseInt(codMedico);
		} catch (NumberFormatException e) {
			throw new Exception("El codigo del médico debe ser un número entero.");
		}
	}

	public String getDiagnostico() {
		return diagnostico;
	}

	/**
	 * Setter del diagnóstico. Verifica que la cadena recibida no sea nula ni sea
	 * solo espacios en blancos.
	 * 
	 * @param diagnostico (string) la cadena a setear como diagnostico
	 * @throws Exception en caso de que la cadena no pase las verificaciones
	 */
	public final void setDiagnostico(String diagnostico) throws Exception {
		// Chequeamos que la cadena recibida no sea nula, y además chequeamos que no
		// hayamos recibido
		// una cadena con espacios en blanco
		if (diagnostico != null && !diagnostico.trim().isEmpty())
			// Asignamos el diagnóstico al atributo diagnostico del objeto
			this.diagnostico = diagnostico;
		else
			throw new Exception("La situación debe tener un diagnóstico.");
	}
}
