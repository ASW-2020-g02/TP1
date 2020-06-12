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

/*
 * La base de datos se puede abrir utilizando DB Browser (https://sqlitebrowser.org/)
 */
public class DAO {
	private static DAO singleton = null;
	final static String DB = "centro-asistencial-los-pinares.db";
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

	public static DAO obtenerInstancia() {
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

			// Creación del usuario admin
			sql = "INSERT INTO USUARIOS (NOMBRE, PASSWORD, EMAIL) " + "VALUES ('admin', 'admin', 'admin@admin.com');";
			stmt.executeUpdate(sql);

			// Cierro el statemnt
			stmt.close();
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	public void cerrar() {
		try {
			c.close();
			System.out.println("Motor de base de datos detenido.");
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
	 * @param med
	 * @throws Exception
	 */
	public void insertarMedico(Medico med) throws Exception {
		int codigo = med.getCodigo();
		String nombre = med.getNombre();
		String especialidad = med.getEspecialidad();
		try {
			// Agrego el médico
			String sql = "INSERT INTO MEDICOS (CODIGO, NOMBRE, ESPECIALIDAD) " + "VALUES (" + codigo + ", '" + nombre
					+ "', '" + especialidad + "');";
			PreparedStatement ps = c.prepareStatement(sql);
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