package entidades;

//Esta clase se realiza para mapear cada Usuario
//usuario: Nombre de usuario
//contrasenia: contraseña del usuario
public class Usuario {
	private String usuario;
	private String contrasenia;

	// Constructor
	public Usuario(String u, String c) {
		usuario = u;
		contrasenia = c;
	}

	// Getters y Setters
	public String getUsuario() {
		return usuario;
	}

	public String getContrasenia() {
		return contrasenia;
	}
}
