package objetos;

//Esta clase se realiza para mapear cada Situación de paciente
//codpaciente: Codigo del Paciente que es atendido, numerico de 5 caracteres
//codmedico: Codigo del Medico que atiende al paciente, numerico de 5 caracteres
//situacion: Situación del estado del paciente
public class SituacionPaciente {
	private int codpaciente;
	private int codmedico;
	private String situacion;
	
	//Constructor
	public SituacionPaciente(int codp, int codm, String situacion)
	{
		this.codpaciente = codp;
		this.codmedico = codm;
		this.situacion = situacion;
	}

	//Getters y Setters
	public int getCodpaciente() {
		return codpaciente;
	}

	public void setCodpaciente(int codpaciente) {
		this.codpaciente = codpaciente;
	}

	public int getCodmedico() {
		return codmedico;
	}

	public void setCodmedico(int codmedico) {
		this.codmedico = codmedico;
	}

	public String getSituacion() {
		return situacion;
	}

	public void setSituacion(String situacion) {
		this.situacion = situacion;
	}

	//Funcion Imprimir: Realiza la impresion de los datos de modo:
	//Codigo_Paciente::Codigo_medico::Situacion
	public String Imprimir()
	{
		return this.getCodpaciente() + " :: " + this.getCodmedico() + " :: " + this.getSituacion();
	}

}
