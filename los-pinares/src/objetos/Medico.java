package objetos;

//Esta clase se realiza para mapear cada Medico
//codigo: Codigo del medico, numerico de 5 caracteres
//nombre: Nombre y Apellido del medico
//especializacion: Especialización del medico
public class Medico {
	private int codigo;
	private String nombre;
	private String especializacion;

	//Constructor
	public Medico(int cod, String nom, String esp)
	{
		this.codigo= cod;
		this.nombre = nom;
		this.especializacion = esp;
	}

	//Getters y Setters
	public String getEspecializacion() {
		return especializacion;
	}

	public void setEspecializacion(String especializacion) {
		this.especializacion = especializacion;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	//Funcion Imprimir: Realiza la impresion de los datos de modo:
	//Codigo_Medico::Nombre::Especializacion
	public String Imprimir()
	{
		return this.getCodigo() + " :: " + this.getNombre() + " :: " + this.getEspecializacion();
	}
}
