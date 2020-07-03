package com.unlam.asw.DB;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.sqlite.SQLiteConfig;

import com.unlam.asw.entities.Medico;
import com.unlam.asw.entities.Paciente;
import com.unlam.asw.entities.Situacion;
import com.unlam.asw.entities.Usuario;
import com.unlam.asw.utils.Criptografia;
import com.unlam.asw.utils.Utils;

/*
 * La base de datos se puede abrir utilizando DB Browser (https://sqlitebrowser.org/)
 */
public class DAO {
	private static DAO singleton = null;
	final static String DB = "centro-asistencial-los-pinares.db";
	Connection c = null;
	Criptografia cripto;

	public DAO() {
		// Creo una variable de tipo File para la ubicación del archivo de base de datos
		File archivo = new File(DB);

		cripto = Criptografia.obtenerInstancia();

		try {
			// En caso de que exista, conecto el SQL
			if (archivo.exists()) {
				SQLiteConfig config = new SQLiteConfig();
				config.enforceForeignKeys(true);
				c = DriverManager.getConnection("jdbc:sqlite:" + DB, config.toProperties());
			} else {
				// En caso de no existir, debo crear la base de datos
				Class.forName("org.sqlite.JDBC");
				SQLiteConfig config = new SQLiteConfig();
				// Habilito las foreing key
				config.enforceForeignKeys(true);
				c = DriverManager.getConnection("jdbc:sqlite:" + DB, config.toProperties());
				// Creo las tablas necesarias
				inicializar();
//				System.out.println("Instanciado archivo de base de datos.");
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
//		System.out.println("Conexión con base de datos establecida.");
	}

	public static DAO obtenerInstancia() {
		// En caso de no existir una instancia de la clase, debo crear una
		if (singleton == null)
			singleton = new DAO();

		return singleton;
	}

	private void inicializar() {
		String sql = null;
		try {
			// Creación de tablas por defecto
			Statement stmt = c.createStatement();

			// Tabla de pacientes
			sql = "CREATE TABLE PACIENTES " + "(CODIGO INTEGER PRIMARY KEY NOT NULL,"
					+ " NOMBRE     TEXT        NOT NULL)";
			stmt.executeUpdate(sql);

			// Tabla de médicos
			sql = "CREATE TABLE MEDICOS " + "(CODIGO INTEGER PRIMARY KEY NOT NULL,"
					+ " ESPECIALIDAD   TEXT    NOT NULL," + " NOMBRE            TEXT    NOT NULL)";
			stmt.executeUpdate(sql);

			// Tabla de situaciones
			sql = "CREATE TABLE SITUACIONES " + "(ID INTEGER PRIMARY KEY NOT NULL,"
					+ " CODIGOPACIENTE    INT    NOT NULL," + " CODIGOMEDICO      INT    NOT NULL,"
					+ " DIAGNOSTICO   TEXT," + " FOREIGN KEY(CODIGOPACIENTE) REFERENCES PACIENTES (CODIGO)"
					+ " FOREIGN KEY(CODIGOMEDICO) REFERENCES MEDICOS (CODIGO)" + ")";
			stmt.executeUpdate(sql);

			// usuarios
			sql = "CREATE TABLE USUARIOS " + " (NOMBRE    TEXT           PRIMARY KEY     NOT NULL, "
					+ " PASSWORD   CHAR(50)       NOT NULL, " + " EMAIL      TEXT)";
			stmt.executeUpdate(sql);

			// Cierro el statemnt
			stmt.close();
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	public void cerrar() {
		// Para evitar posibles errores, es necesario finalizar la conexión con la base
		// de datos
		try {
			c.close();
//			System.out.println("Motor de base de datos detenido.");
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	/**
	 * Método para lanzar excepciones no reconocidas de SQLite
	 * 
	 * @param e
	 * @throws Exception
	 */
	private void lanzarEx(SQLException e) throws Exception {
		// Lanzo una excepción de la base de datos, junto a su código de error y mensaje
		throw new Exception("Error en la base de datos." + "\nCódigo de error: " + e.getErrorCode() + "\nMensaje: "
				+ e.getMessage());
	}

	/**
	 *
	 * @param paciente
	 * @throws Exception
	 */
	public void insertarPaciente(Paciente paciente) throws Exception {
		int codigo = paciente.getCodigo();
		String nombre = paciente.getNombre();
		try {
			// agrego el paciente
			String sql = "INSERT INTO PACIENTES (CODIGO, NOMBRE) VALUES ( ?, ?);";
			PreparedStatement ps = c.prepareStatement(sql);
			// Completo los campos del statement
			ps.setInt(1, codigo);
			ps.setString(2, cripto.encrypt(nombre));
			ps.execute();
			// cierro el statement
			ps.close();
		} catch (SQLException e) {
			switch (e.getErrorCode()) {
			case 19:
				throw new Exception("Este código de paciente ya existe.");
			default:
				lanzarEx(e);
			}
		}
	}

	/**
	 *
	 * @param paciente
	 * @throws Exception
	 */
	public Paciente buscarPacientePorCodigo(int codigo) throws Exception {
		Paciente paciente;
		try {

			// Escribo la sentencia SQL para obtener todos los pacientes dado un codigo
			String sql = "SELECT * FROM PACIENTES" + " WHERE CODIGO=?;";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, codigo);

			// Almaceno en una variable el set de resultados
			ResultSet rs = ps.executeQuery();

			if (!rs.isBeforeFirst()) {
				return null;
			}

			// Creo el objeto Paciente con los datos obtenidos de la base de datos
			paciente = new Paciente(rs.getString("CODIGO"), cripto.decrypt(rs.getString("NOMBRE")));

			ps.close();
			rs.close();
		} catch (SQLException e) {
			lanzarEx(e);
			return null;
		}
		return paciente;
	}

	/**
	 *
	 * @param med
	 * @throws Exception
	 */
	public void insertarMedico(Medico med) throws Exception {
		int codigo = med.getCodigo();
		String nombre = med.getNombre();
		String especialidad = med.getEspecialidad();
		try {
			// Creo la secuencia SQL para insertar el registro en la tabla
			String sql = "INSERT INTO MEDICOS (CODIGO, NOMBRE, ESPECIALIDAD) VALUES (?,?,?);";
			// Hago un prepared statement, ya que se trata de una query con campos definidos
			// por el usuario
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, codigo);
			ps.setString(2, cripto.encrypt(nombre));
			ps.setString(3, cripto.encrypt(especialidad));
			ps.execute();
			// Cierro el statement
			ps.close();
		} catch (SQLException e) {
			switch (e.getErrorCode()) {
			case 19:
				throw new Exception("Este codigo de médico ya existe.");
			default:
				lanzarEx(e);
			}
		}
	}

	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Medico> obtenerMedicos() throws Exception {
		ArrayList<Medico> medicos = new ArrayList<Medico>();

		try {
			Statement stmt = c.createStatement();
			String sql = "SELECT CODIGO, NOMBRE, ESPECIALIDAD FROM MEDICOS;";
			ResultSet rs = stmt.executeQuery(sql);
			// voy agregando los médicos a la lista
			while (rs.next()) {
				// Desencripto los string
				medicos.add(new Medico(rs.getString("CODIGO"), cripto.decrypt(rs.getString("NOMBRE")),
						cripto.decrypt(rs.getString("ESPECIALIDAD"))));
			}
			// cierro el statement
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			lanzarEx(e);
		}

		return medicos;
	}

	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Paciente> obtenerPacientesXMedico(int codigoMedico) throws Exception {
		ArrayList<Paciente> pacientes = new ArrayList<Paciente>();

		try {
			String sql = "SELECT DISTINCT CODIGO, NOMBRE " + "FROM PACIENTES "
					+ "INNER JOIN SITUACIONES ON PACIENTES.CODIGO = SITUACIONES.CODIGOPACIENTE "
					+ "WHERE CODIGOMEDICO=?;";

			// Utilizo un prepared statement
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, String.valueOf(codigoMedico));
			ResultSet rs = ps.executeQuery();

			// Voy agregando los pacientes a la lista
			while (rs.next()) {
				pacientes.add(new Paciente(rs.getString("CODIGO"), cripto.decrypt(rs.getString("NOMBRE"))));
			}

			// Cierro el statement
			ps.close();
			rs.close();
		} catch (SQLException e) {
//			System.out.println(e.toString());
			lanzarEx(e);
		}

		return pacientes;
	}

	public ArrayList<String> obtenerEnfermedadesXMedico(int codigoMedico) throws Exception {
		ArrayList<String> diagnosticos = new ArrayList<String>();

		try {
			String sql = "SELECT DISTINCT DIAGNOSTICO " + "FROM SITUACIONES WHERE CODIGOMEDICO=?;";

			// Utilizo un prepared statement
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, String.valueOf(codigoMedico));

			ResultSet rs = ps.executeQuery();
			// Voy agregando los pacientes a la lista
			while (rs.next()) {
				diagnosticos.add(cripto.decrypt(rs.getString("DIAGNOSTICO")));
			}

			// Cierro el statement
			ps.close();
			rs.close();
		} catch (SQLException e) {
//			System.out.println(e.toString());
			lanzarEx(e);
		}

		return diagnosticos;
	}

	/**
	 *
	 * @param sit
	 * @throws Exception
	 */
	public void insertarSituacion(Situacion sit) throws Exception {
		int id = sit.getId();
		int codigoPac = sit.getCodPaciente();
		int codigoMed = sit.getCodMedico();
		String diag = sit.getDiagnostico();
		try {
			// agrego la situación
			String sql = "INSERT INTO SITUACIONES (ID, CODIGOPACIENTE, CODIGOMEDICO, DIAGNOSTICO) "
					+ "VALUES (?,?,?,?);";
			// Debido a que estamos frente a la inserción de campos ingresados
			// por el usuario, es necesario utilizar un prepared statement
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setInt(2, codigoPac);
			ps.setInt(3, codigoMed);
			ps.setString(4, cripto.encrypt(diag));

			ps.execute();
			// cierro el statement
			ps.close();
		} catch (SQLException e) {
			switch (e.getErrorCode()) {
			case 19:
				throw new Exception("Error al agregar situación.\n"
						+ "Verifique que el ID de situación no existe actualmente, y que los codigo del paciente y del médico"
						+ " están cargados en los registros de Pacientes y Médicos.");
			default:
				lanzarEx(e);
			}
		}
	}

	/**
	 *
	 * @param codigo integer
	 * @throws Exception
	 */
	public Medico buscarMedicoPorCodigo(int codigo) throws Exception {
		Medico med;
		try {

			// Obtengo los médicos dado un código
			String sql = "SELECT * FROM MEDICOS" + " WHERE CODIGO=?;";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, codigo);
			ResultSet rs = ps.executeQuery();

			if (!rs.isBeforeFirst()) {
				return null;
			}
			// Creo el objeto Medico correspondiente, con los campos obtenidos de la base de
			// datos
			med = new Medico(rs.getString("CODIGO"), cripto.decrypt(rs.getString("ESPECIALIDAD")),
					cripto.decrypt(rs.getString("NOMBRE")));
			rs.close();
			ps.close();
		} catch (SQLException e) {
			lanzarEx(e);
			return null;
		}
		return med;
	}

	public int obtenerUltimoIDSituacion() throws Exception {
		int id = 0;

		try {
			Statement stmt = c.createStatement();
			// Ordeno de mayor a menor las situaciones
			String sql = "SELECT ID FROM SITUACIONES ORDER BY ID DESC";
			ResultSet rs = stmt.executeQuery(sql);

			if (!rs.isBeforeFirst()) {
				// Si no tengo ninguna situación, devuelvo 0
				return id;
			} else {
				// En el caso contrario, devuelvo el ID mayor
				id = Integer.parseInt(rs.getString("ID"));
			}

			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	// USUARIOS

	/**
	 *
	 * @param usuario
	 * @throws Exception
	 */
	public void insertarUsuario(Usuario usuario) throws Exception {
		String nombre = usuario.getNombre();
		String password = Utils.hashPassword(usuario.getPassword());
		String email = usuario.getEmail();
		try {
			// agrego el usuario
			String sql = "INSERT INTO USUARIOS (NOMBRE, PASSWORD, EMAIL) VALUES (?,?,?);";
			PreparedStatement ps = c.prepareStatement(sql);
			// Encritp los distintos campos
			ps.setString(1, cripto.encrypt(nombre));
			ps.setString(2, cripto.encrypt(password));
			ps.setString(3, cripto.encrypt(email));
			// Ejecuto el prepared statemnt
			ps.execute();
			// cierro el statement
			ps.close();
		} catch (SQLException e) {
			switch (e.getErrorCode()) {
			case 19:
				throw new Exception("Este nombre de usuario ya existe.");
			default:
				lanzarEx(e);
			}
		}
	}

	public Usuario buscarUsuario(String email, String password) throws Exception {
		String hashedPassword = Utils.hashPassword(password);
		Usuario usuario;
		try {
			String sql = "SELECT NOMBRE, EMAIL, PASSWORD FROM USUARIOS WHERE EMAIL=? AND PASSWORD=?;";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, cripto.encrypt(email));
			ps.setString(2, cripto.encrypt(hashedPassword));
			ResultSet rs = ps.executeQuery();

			if (!rs.isBeforeFirst()) {
				return null;
			}
			// Creo el objeto Usuario correspondiente, con los campos obtenidos de la base
			// de
			// datos
			usuario = new Usuario(cripto.decrypt(rs.getString("NOMBRE")), cripto.decrypt(rs.getString("EMAIL")),
					cripto.decrypt(rs.getString("PASSWORD")));
			rs.close();
			ps.close();
		} catch (SQLException e) {
			lanzarEx(e);
			return null;
		}
		return usuario;
	}

	public Usuario buscarUsuarioPorEmail(String email) throws Exception {
		Usuario usuario;
		try {
			// Obtengo los usuarios dado un mail
			String sql = "SELECT NOMBRE, EMAIL, PASSWORD FROM USUARIOS WHERE EMAIL=?;";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, cripto.encrypt(email));

			ResultSet rs = ps.executeQuery();

			if (!rs.isBeforeFirst()) {
				return null;
			}
			// Creo el objeto Usuario correspondiente, con los campos obtenidos de la base
			// de
			// datos
			usuario = new Usuario(cripto.decrypt(rs.getString("NOMBRE")), cripto.decrypt(rs.getString("EMAIL")),
					cripto.decrypt(rs.getString("PASSWORD")));
			rs.close();
			ps.close();
		} catch (SQLException e) {
			lanzarEx(e);
			return null;
		}
		return usuario;
	}
}