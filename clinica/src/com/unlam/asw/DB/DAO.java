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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*
 * La base de datos se puede abrir utilizando DB Browser (https://sqlitebrowser.org/)
 */
public class DAO {
	final static String DB = "clinica-los-pinares.db";
	Connection c = null;

	public DAO() {
		File archivo = new File(DB);
		try {
			if (archivo.exists()) {
				SQLiteConfig config = new SQLiteConfig();
				config.enforceForeignKeys(true);
				c = DriverManager.getConnection("jdbc:sqlite:" + DB, config.toProperties());
			} else {
				Class.forName("org.sqlite.JDBC");
				SQLiteConfig config = new SQLiteConfig();
				config.enforceForeignKeys(true);
				c = DriverManager.getConnection("jdbc:sqlite:" + DB, config.toProperties());
				inicializar();
				System.out.println("Instanciado archivo de base de datos.");
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Conexión con base de datos establecida.");
	}

	private void inicializar() {
		String sql = null;
		try {
			// creación de tablas por defecto
			Statement stmt = c.createStatement();

			// usuarios
			sql = "CREATE TABLE USUARIOS " + " (NOMBRE    TEXT           PRIMARY KEY     NOT NULL, "
					+ " PASSWORD   CHAR(50)       NOT NULL, " + " EMAIL      TEXT)";
			stmt.executeUpdate(sql);

			// pacientes
			sql = "CREATE TABLE PACIENTES " + "(CODIGO INTEGER PRIMARY KEY NOT NULL,"
					+ " NOMBRE     TEXT        NOT NULL)";
			stmt.executeUpdate(sql);

			// médicos
			sql = "CREATE TABLE MEDICOS " + "(CODIGO INTEGER PRIMARY KEY NOT NULL,"
					+ " ESPECIALIDAD   TEXT    NOT NULL," + " NOMBRE            TEXT    NOT NULL)";
			stmt.executeUpdate(sql);

			// situaciones
			sql = "CREATE TABLE SITUACIONES " + "(ID INTEGER PRIMARY KEY NOT NULL,"
					+ " CODIGOPACIENTE    INT    NOT NULL," + " CODIGOMEDICO      INT    NOT NULL,"
					+ " DIAGNOSTICO   TEXT," + " FOREIGN KEY(CODIGOPACIENTE) REFERENCES PACIENTES (CODIGO)"
					+ " FOREIGN KEY(CODIGOMEDICO) REFERENCES MEDICOS (CODIGO)" + ")";
			stmt.executeUpdate(sql);

			// creación del usuario admin
			sql = "INSERT INTO USUARIOS (NOMBRE, PASSWORD, EMAIL) " + "VALUES ('admin', 'admin', 'admin@admin.com');";
			stmt.executeUpdate(sql);

			// cierro el statement
			stmt.close();
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	public void cerrar() {
		try {
			c.close();
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
		throw new Exception("Error en la base de datos." + "\nCódigo de error: " + e.getErrorCode() + "\nMensaje: "
				+ e.getMessage());
	}

	/**
	 * Método para validar el usuario en el login
	 * 
	 * @param nombre
	 * @param password
	 * @return
	 */
	public boolean validarUsuario(String nombre, String password) {
		boolean res = false;
		try {
			Statement stmt = c.createStatement();
			String sql = "SELECT PASSWORD FROM USUARIOS WHERE NOMBRE='" + nombre + "';";
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				String bdPassword = rs.getString("PASSWORD");
				if (bdPassword.equals(password))
					res = true;
			}
			// cierro el statement
			stmt.close();
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	/**
	 *
	 * @param usuario
	 * @throws Exception
	 */
	public void insertarUsuario(Usuario usuario) throws Exception {
		String nombre = usuario.getNombre();
		String password = usuario.getPassword();
		String email = usuario.getEmail();
		try {
			// agrego el usuario
			String sql = "INSERT INTO USUARIOS (NOMBRE, PASSWORD, EMAIL) " + "VALUES ('" + nombre + "', '" + password
					+ "', '" + email + "');";
			PreparedStatement ps = c.prepareStatement(sql);
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

	/**
	 *
	 * @param nombre
	 * @throws Exception
	 */
	public void eliminarUsuario(String nombre) throws Exception {
		try {
			// borro el usuario
			String sql = "DELETE FROM USUARIOS WHERE NOMBRE='" + nombre + "';";
			PreparedStatement ps = c.prepareStatement(sql);
			if (ps.executeUpdate() == 0) {
				ps.close();
				throw new Exception("No se encontró ningún registro con ese nombre de usuario.");
			}
			// cierro el statement

		} catch (SQLException e) {
			lanzarEx(e);
		}
	}

	/**
	 *
	 * @param usuario
	 * @throws Exception
	 */
	public void actualizarUsuario(Usuario usuario) throws Exception {
		String nombre = usuario.getNombre();
		String password = usuario.getPassword();
		String email = usuario.getEmail();
		try {
			// actualizo el usuario
			String sql = "UPDATE USUARIOS " + "SET PASSWORD='" + password + "', EMAIL='" + email + "' "
					+ "WHERE NOMBRE='" + nombre + "';";
			PreparedStatement ps = c.prepareStatement(sql);
			if (ps.executeUpdate() == 0) {
				ps.close();
				throw new Exception("No se encontró ningún registro con ese nombre de usuario.");
			}
			// cierro el statement
			ps.close();
		} catch (SQLException e) {
			lanzarEx(e);
		}
	}

	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public ObservableList<Usuario> obtenerUsuarios() throws Exception {
		ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
		try {
			Statement stmt = c.createStatement();
			String sql = "SELECT NOMBRE, PASSWORD, EMAIL FROM USUARIOS;";
			ResultSet rs = stmt.executeQuery(sql);
			// voy agregando los usuarios a la lista
			while (rs.next()) {
				usuarios.add(new Usuario(rs.getString("NOMBRE"), rs.getString("PASSWORD"), rs.getString("EMAIL")));
			}
			// cierro el statement
			stmt.close();
		} catch (SQLException e) {
			lanzarEx(e);
		}
		return usuarios;
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
			String sql = "INSERT INTO PACIENTES (CODIGO, NOMBRE) " + "VALUES ( " + codigo + ", '" + nombre + "');";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.execute();
			// cierro el statement
			ps.close();
		} catch (SQLException e) {
			switch (e.getErrorCode()) {
			case 19:
				throw new Exception("Este codigo de paciente ya existe.");
			default:
				lanzarEx(e);
			}
		}
	}

	/**
	 *
	 * @param codigo
	 * @throws Exception
	 */
	public void eliminarPaciente(int codigo) throws Exception {
		try {
			// borro el paciente
			String sql = "DELETE FROM PACIENTES WHERE CODIGO=" + codigo + ";";
			PreparedStatement ps = c.prepareStatement(sql);
			if (ps.executeUpdate() == 0) {
				ps.close();
				throw new Exception("No se encontró ningún registro con ese codigo de paciente.");
			}
			// cierro el statement
			ps.close();
		} catch (SQLException e) {
			lanzarEx(e);
		}
	}

	/**
	 *
	 * @param paciente
	 * @throws Exception
	 */
	public void actualizarPaciente(Paciente paciente) throws Exception {
		int codigo = paciente.getCodigo();
		String nombre = paciente.getNombre();
		try {
			// actualizo el paciente
			String sql = "UPDATE PACIENTES " + "SET NOMBRE='" + nombre + "' " + "WHERE CODIGO=" + codigo + ";";
			PreparedStatement ps = c.prepareStatement(sql);
			if (ps.executeUpdate() == 0) {
				ps.close();
				throw new Exception("No se encontró ningún registro con ese codigo de paciente.");
			}
			// cierro el statement
			ps.close();
		} catch (SQLException e) {
			lanzarEx(e);
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

			Statement stmt = c.createStatement();
			String sql = "SELECT * FROM PACIENTES" + " WHERE CODIGO=" + codigo + ";";
			ResultSet rs = stmt.executeQuery(sql);

			if (!rs.isBeforeFirst()) {
				return null;
			}
			paciente = new Paciente(rs.getString("CODIGO"), rs.getString("NOMBRE"));

			stmt.close();
		} catch (SQLException e) {
			lanzarEx(e);
			return null;
		}
		return paciente;
	}

	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public ObservableList<Paciente> obtenerPacientes() throws Exception {
		ObservableList<Paciente> pacientes = FXCollections.observableArrayList();
		try {
			Statement stmt = c.createStatement();
			String sql = "SELECT CODIGO, NOMBRE FROM PACIENTES;";
			ResultSet rs = stmt.executeQuery(sql);
			// voy agregando los pacientes a la lista
			while (rs.next()) {
				pacientes.add(new Paciente(rs.getString("CODIGO"), rs.getString("NOMBRE")));
			}
			// cierro el statement
			stmt.close();
		} catch (SQLException e) {
			lanzarEx(e);
		}
		return pacientes;
	}

	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public ObservableList<Paciente> consultarEnfermedadesPaciente() throws Exception {
		ObservableList<Paciente> pacientes = FXCollections.observableArrayList();
		try {
			Statement stmt = c.createStatement();
			String sql = "SELECT CODIGO, NOMBRE FROM PACIENTES;";
			ResultSet rs = stmt.executeQuery(sql);
			// voy agregando los pacientes a la lista
			while (rs.next()) {
				pacientes.add(new Paciente(rs.getString("CODIGO"), rs.getString("NOMBRE")));
			}
			// cierro el statement
			stmt.close();
		} catch (SQLException e) {
			lanzarEx(e);
		}
		return pacientes;
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
			// agrego el médico
			String sql = "INSERT INTO MEDICOS (CODIGO, NOMBRE, ESPECIALIDAD) " + "VALUES (" + codigo + ", '" + nombre
					+ "', '" + especialidad + "');";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.execute();
			// cierro el statement
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
	 * @param codigo
	 * @throws Exception
	 */
	public void eliminarMedico(int codigo) throws Exception {
		try {
			// borro el médico
			String sql = "DELETE FROM MEDICOS WHERE CODIGO=" + codigo + ";";
			PreparedStatement ps = c.prepareStatement(sql);
			if (ps.executeUpdate() == 0) {
				ps.close();
				throw new Exception("No se encontró ningún registro con ese codigo de médico.");
			}
			// cierro el statement
			ps.close();
		} catch (SQLException e) {
			lanzarEx(e);
		}
	}

	/**
	 *
	 * @param med
	 * @throws Exception
	 */
	public void actualizarMedico(Medico med) throws Exception {
		int codigo = med.getCodigo();
		String nombre = med.getNombre();
		String especialidad = med.getEspecialidad();
		try {
			// actualizo el médico
			String sql = "UPDATE MEDICOS " + "SET NOMBRE='" + nombre + "', ESPECIALIDAD='" + especialidad + "' "
					+ "WHERE CODIGO=" + codigo + ";";
			PreparedStatement ps = c.prepareStatement(sql);
			if (ps.executeUpdate() == 0) {
				ps.close();
				throw new Exception("No se encontró ningún registro con ese codigo de médico.");
			}
			// cierro el statement
			ps.close();
		} catch (SQLException e) {
			lanzarEx(e);
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
				medicos.add(new Medico(rs.getString("CODIGO"), rs.getString("NOMBRE"), rs.getString("ESPECIALIDAD")));
			}
			// cierro el statement
			stmt.close();
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
					+ "INNER JOIN SITUACIONES ON PACIENTES.CODIGO = SITUACIONES.CODIGOPACIENTE " + "WHERE CODIGOMEDICO="
					+ String.valueOf(codigoMedico) + ";";
			System.out.println(sql);
			// Utilizo un prepared statement
			PreparedStatement ps = c.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			// Voy agregando los pacientes a la lista
			while (rs.next()) {
				pacientes.add(new Paciente(rs.getString("CODIGO"), rs.getString("NOMBRE")));
			}

			// Cierro el statement
			ps.close();
		} catch (SQLException e) {
			System.out.println(e.toString());
			lanzarEx(e);
		}

		return pacientes;
	}

	public ArrayList<String> obtenerEnfermedadesXMedico(int codigoMedico) throws Exception {
		ArrayList<String> diagnosticos = new ArrayList<String>();

		try {
			String sql = "SELECT DISTINCT DIAGNOSTICO " + "FROM SITUACIONES WHERE CODIGOMEDICO="
					+ String.valueOf(codigoMedico) + ";";
			System.out.println(sql);
			// Utilizo un prepared statement
			PreparedStatement ps = c.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			// Voy agregando los pacientes a la lista
			while (rs.next()) {
				diagnosticos.add(rs.getString("DIAGNOSTICO"));
			}

			// Cierro el statement
			ps.close();
		} catch (SQLException e) {
			System.out.println(e.toString());
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
			String sql = "INSERT INTO SITUACIONES (ID, CODIGOPACIENTE, CODIGOMEDICO, DIAGNOSTICO) " + "VALUES (" + id
					+ ", " + codigoPac + ", " + codigoMed + ", '" + diag + "');";

			PreparedStatement ps = c.prepareStatement(sql);
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
	 * @param id
	 * @throws Exception
	 */
	public void eliminarSituacion(int id) throws Exception {
		try {
			// borro la situación
			String sql = "DELETE FROM SITUACIONES WHERE ID=" + id + ";";
			PreparedStatement ps = c.prepareStatement(sql);
			if (ps.executeUpdate() == 0) {
				ps.close();
				throw new Exception("No se encontró ningún registro con ese ID de situación.");
			}
			// cierro el statement
			ps.close();
		} catch (SQLException e) {
			lanzarEx(e);
		}
	}

	/**
	 *
	 * @param sit
	 * @throws Exception
	 */
	public void actualizarSituacion(Situacion sit) throws Exception {
		int id = sit.getId();
		int codigoPac = sit.getCodPaciente();
		int codigoMed = sit.getCodMedico();
		String diag = sit.getDiagnostico();
		try {
			// actualizo la situación
			String sql = "UPDATE SITUACIONES " + "SET CODIGOPACIENTE=" + codigoPac + ", CODIGOMEDICO=" + codigoMed
					+ ", '" + diag + "'" + "WHERE ID=" + id + ";";
			PreparedStatement ps = c.prepareStatement(sql);
			if (ps.executeUpdate("PRAGMA foreign_keys = ON") == 0) {
				ps.close();
				throw new Exception("No se encontró ningún registro con ese ID de situación.");
			}
			// cierro el statement
			ps.close();
		} catch (SQLException e) {
			lanzarEx(e);
		}
	}

	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public ObservableList<Situacion> obtenerSituaciones() throws Exception {
		ObservableList<Situacion> sits = FXCollections.observableArrayList();
		try {
			Statement stmt = c.createStatement();
			String sql = "SELECT ID, CODIGOPACIENTE, CODIGOMEDICO, DIAGNOSTICO FROM SITUACIONES;";
			ResultSet rs = stmt.executeQuery(sql);
			// voy agregando las situaciones a la lista
			while (rs.next()) {
				sits.add(new Situacion(rs.getString("ID"), rs.getString("CODIGOPACIENTE"), rs.getString("CODIGOMEDICO"),
						rs.getString("DIAGNOSTICO")));
			}
			// cierro el statement
			stmt.close();
		} catch (SQLException e) {
			lanzarEx(e);
		}
		return sits;
	}

	/**
	 *
	 * @param codigo integer
	 * @throws Exception
	 */
	public Medico buscarMedicoPorCodigo(int codigo) throws Exception {
		Medico med;
		try {

			Statement stmt = c.createStatement();
			String sql = "SELECT * FROM MEDICOS" + " WHERE CODIGO=" + codigo + ";";
			ResultSet rs = stmt.executeQuery(sql);

			if (!rs.isBeforeFirst()) {
				return null;
			}
			med = new Medico(rs.getString("CODIGO"), rs.getString("ESPECIALIDAD"), rs.getString("NOMBRE"));

			stmt.close();
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
			String sql = "SELECT ID FROM SITUACIONES ORDER BY ID DESC";
			ResultSet rs = stmt.executeQuery(sql);

			if (!rs.isBeforeFirst()) {
				return id;
			} else {
				id = Integer.parseInt(rs.getString("ID"));
			}

			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
}