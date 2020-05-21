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

	public final void setId(String id) throws Exception {
		try {
			if (id == null || id.trim().isEmpty())
				throw new NumberFormatException();
			this.id = Integer.parseInt(id);
		} catch (NumberFormatException e) {
			throw new Exception("El ID debe ser un n�mero v�lido.");
		}
	}

	public int getCodPaciente() {
		return codPaciente;
	}

	public final void setCodPaciente(String codPaciente) throws Exception {
		try {
			if (codPaciente == null || codPaciente.trim().isEmpty())
				throw new NumberFormatException();

			this.codPaciente = Integer.parseInt(codPaciente);
		} catch (NumberFormatException e) {
			throw new Exception("El codigo del paciente debe ser un n�mero entero.");
		}
	}

	public int getCodMedico() {
		return codMedico;
	}

	public final void setCodMedico(String codMedico) throws Exception {
		try {
			if (codMedico == null || codMedico.trim().isEmpty())
				throw new NumberFormatException();

			this.codMedico = Integer.parseInt(codMedico);
		} catch (NumberFormatException e) {
			throw new Exception("El codigo del m�dico debe ser un n�mero entero.");
		}
	}

	public String getDiagnostico() {
		return diagnostico;
	}

	public final void setDiagnostico(String diagnostico) throws Exception {
		if (diagnostico != null && !diagnostico.trim().isEmpty())
			this.diagnostico = diagnostico;
		else
			throw new Exception("La situaci�n debe tener un diagn�stico.");
	}
}
