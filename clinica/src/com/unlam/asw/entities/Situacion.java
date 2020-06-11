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

	public int getId() {
		return id;
	}
	
	/**
	 * Setter del ID de la situaci�n. Recibe una cadena y verifica que pueda convertirse a n�mero entero.
	 * 
	 * @param id (string) cadena que contiene el id de la situacion
	 * @throws Exception en caso de que la cadena recibida no pueda convertirse a n�mero entero.
	 */
	public final void setId(String id) throws Exception {
		try {
			//Chequeamos que la cadena recibida no sea nula, y adem�s chequeamos que no hayamos recibido
			//una cadena con espacios en blanco
			if (id == null || id.trim().isEmpty())
				throw new NumberFormatException();			
			//Convertimos la cadena a un entero y la asignamos al atributo id del objeto
			this.id = Integer.parseInt(id);
		} catch (NumberFormatException e) {
			throw new Exception("El ID debe ser un n�mero v�lido.");
		}
	}

	public int getCodPaciente() {
		return codPaciente;
	}

	/**
	 * Setter del c�digo del paciente. Recibe una cadena y verifica que pueda convertirse a n�mero entero.
	 * 
	 * @param codPaciente (string) cadena que contiene el c�digo del paciente
	 * @throws Exception en caso de que la cadena recibida no pueda convertirse a n�mero entero.
	 */
	public final void setCodPaciente(String codPaciente) throws Exception {
		try {
			//Chequeamos que la cadena recibida no sea nula, y adem�s chequeamos que no hayamos recibido
			//una cadena con espacios en blanco
			if (codPaciente == null || codPaciente.trim().isEmpty())
				throw new NumberFormatException();
			//Convertimos la cadena a un entero y la asignamos al atributo codPaciente del objeto
			this.codPaciente = Integer.parseInt(codPaciente);
		} catch (NumberFormatException e) {
			throw new Exception("El codigo del paciente debe ser un n�mero entero.");
		}
	}

	public int getCodMedico() {
		return codMedico;
	}

	/**
	 * Setter del c�digo del m�dico. Recibe una cadena y realiza verificaciones sobre la misma.
	 * 
	 * @param codMedico (string) cadena que recibe y que ser� convertida a entero
	 * @throws Exception arroja una excepci�n en caso de que el n�mero no sea entero
	 */	
	public final void setCodMedico(String codMedico) throws Exception {
		try {
			//Chequeamos que la cadena recibida no sea nula, y adem�s chequeamos que no hayamos recibido
			//una cadena con espacios en blanco
			if (codMedico == null || codMedico.trim().isEmpty())
				throw new NumberFormatException();
			//Convertimos la cadena a un entero y la asignamos al atributo codMedico del objeto
			this.codMedico = Integer.parseInt(codMedico);
		} catch (NumberFormatException e) {
			throw new Exception("El codigo del m�dico debe ser un n�mero entero.");
		}
	}

	public String getDiagnostico() {
		return diagnostico;
	}

	/**
	 * Setter del diagn�stico. Verifica que la cadena recibida no sea nula ni sea solo espacios en blancos.
	 * 
	 * @param diagnostico (string) la cadena a setear como diagnostico 
	 * @throws Exception en caso de que la cadena no pase las verificaciones
	 */
	public final void setDiagnostico(String diagnostico) throws Exception {
		//Chequeamos que la cadena recibida no sea nula, y adem�s chequeamos que no hayamos recibido
		//una cadena con espacios en blanco
		if (diagnostico != null && !diagnostico.trim().isEmpty())
			//Asignamos el diagn�stico al atributo diagnostico del objeto
			this.diagnostico = diagnostico;
		else
			throw new Exception("La situaci�n debe tener un diagn�stico.");
	}
}
