package entidades;

//Esta clase se realiza para mapear cada Paciente
//codigo: Codigo del Paciente, numerico de 5 caracteres
//nombre: Nombre y Apellido del Paciente
public class Paciente {
	private int codigo;
	private String nombre;

	// Constructor
	public Paciente(int cod, String nom) {
		this.codigo = cod;
		this.nombre = nom;
	}

	// Getters y Setters
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

	// Funcion Imprimir: Realiza la impresion de los datos de modo:
	// Codigo_Paciente::Nombre
	public String Imprimir() {
		return this.getCodigo() + " :: " + this.getNombre();
	}

}
