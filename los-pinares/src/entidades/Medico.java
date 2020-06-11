package entidades;

public class Medico {
	private int codigo;
	private String nombre;
	private String especializacion;

	// Constructor
	public Medico(int cod, String nom, String esp) {
		this.codigo = cod;
		this.nombre = nom;
		this.especializacion = esp;
	}

	// Getters y Setters
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

	// Permite obtener el código del médico, así como su nombre y especialización
	public String Imprimir() {
		return this.getCodigo() + " - " + this.getNombre() + " - " + this.getEspecializacion();
	}
}
