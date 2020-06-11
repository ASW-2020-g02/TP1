package otros;

//En esta clase contiene las constantes que se utilizaran en el sistema:
//pathArchivos: Directorio donde se almacenaran los archivos del sistema
//archivoPacientes: path donde se almacenaran los datos de los pacientes
//archivoMedicos: path donde se almacenaran los datos de los Medicos
//archivoSituacionPaciente : path donde se almacenaran las situaciones de los pacientes
//archivoEspecializaciones: path donde se almacenaran las especializaciones disponibles para los medicos
//archivoUsuarios: path donde se almacenaran los datos de los usuarios del sistema
public class Constantes {
	private static String pathArchivos = System.getProperty("user.dir") + "\\archivos\\";
	public static String archivoPacientes = pathArchivos + "datopac.txt";
	public static String archivoMedicos = pathArchivos + "datomed.txt";
	public static String archivoSituacionPaciente = pathArchivos + "situpac.txt";
	public static String archivoEspecializaciones = pathArchivos + "especializaciones.txt";
	public static String archivoUsuarios = pathArchivos + "usuarios.txt";
}
