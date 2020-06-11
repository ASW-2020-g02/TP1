package pantallas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;

public class Principal {

	private JFrame frmRegistroPacientes;
	private JFrame frmListadoPacientesPorMedicos;
	private JFrame frmListadoMedicosEnfermedades;
	private JFrame frmListadoPacientes;
	private JFrame frmListadoMedicos;
	private JDialog jdlAltaPaciente;
	private JDialog jdlModificacionPaciente;
	private JDialog jdlAltaMedico;
	private JDialog jdlModificacionMedico;
	private JPanel panelPrincipal;
	private JPanel panelListadoPacientesYMedicos;
	private JPanel panelInformes;
	private JPanel panelIngresoDatos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal window = new Principal();
					window.frmRegistroPacientes.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Principal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmRegistroPacientes = new JFrame();
		frmRegistroPacientes.setTitle("Registro de Pacientes");
		frmRegistroPacientes.setResizable(false);
		frmRegistroPacientes.getContentPane().setBackground(new Color(204, 204, 153));
		frmRegistroPacientes.setBackground(new Color(153, 204, 153));
		frmRegistroPacientes.setBounds(100, 100, 426, 353);
		frmRegistroPacientes.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRegistroPacientes.getContentPane().setLayout(null);
		
		panelListadoPacientesYMedicos = new JPanel();
		panelListadoPacientesYMedicos.setBackground(new Color(153, 204, 102));
		panelListadoPacientesYMedicos.setBounds(0, 0, 420, 325);
		frmRegistroPacientes.getContentPane().add(panelListadoPacientesYMedicos);
		panelListadoPacientesYMedicos.setLayout(null);
		
		JLabel label_2 = new JLabel("Listados de Pacientes y M\u00E9dicos");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setFont(new Font("Arial", Font.BOLD, 18));
		label_2.setBounds(35, 35, 347, 22);
		panelListadoPacientesYMedicos.add(label_2);
		
		JButton btnPacientes = new JButton("Listado de pacientes");
		btnPacientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (frmListadoPacientes != null){
					frmListadoPacientes.toFront();
				}else{
					frmListadoPacientes = new ListadoPacientes();
				}
				frmListadoPacientes.setVisible(true);
			}
		});
		btnPacientes.setBounds(118, 113, 167, 34);
		panelListadoPacientesYMedicos.add(btnPacientes);
		
		JButton btnMédicos = new JButton("Listado de m\u00E9dicos");
		btnMédicos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (frmListadoMedicos != null){
					frmListadoMedicos.toFront();
				}else{
					frmListadoMedicos = new ListadoMedicos();
				}
				frmListadoMedicos.setVisible(true);
			}
		});
		btnMédicos.setBounds(118, 168, 167, 34);
		panelListadoPacientesYMedicos.add(btnMédicos);
		
		JButton btnVolverListados = new JButton("Volver");
		btnVolverListados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (frmListadoPacientes != null){
					frmListadoPacientes = null;
				}
				if (frmListadoMedicos != null){
					frmListadoMedicos = null;
				}
				cambiaPantalla(panelListadoPacientesYMedicos, panelPrincipal);
			}
		});
		btnVolverListados.setBounds(118, 249, 167, 34);
		panelListadoPacientesYMedicos.add(btnVolverListados);
		panelListadoPacientesYMedicos.setEnabled(false);
		panelListadoPacientesYMedicos.setVisible(false);
		
		panelInformes = new JPanel();
		panelInformes.setBackground(new Color(153, 204, 204));
		panelInformes.setBounds(0, 0, 420, 325);
		frmRegistroPacientes.getContentPane().add(panelInformes);
		panelInformes.setLayout(null);
		
		JLabel label_1 = new JLabel("Informes");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("Arial", Font.BOLD, 18));
		label_1.setBounds(119, 47, 170, 25);
		panelInformes.add(label_1);
		
		JButton btnListadoPacientesPorMedico = new JButton("Listado de Pacientes por M\u00E9dico");
		btnListadoPacientesPorMedico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (frmListadoPacientesPorMedicos != null){
					frmListadoPacientesPorMedicos.toFront();
				}else{
					frmListadoPacientesPorMedicos = new ListadoPacientesPorMedicos();
				}
				frmListadoPacientesPorMedicos.setVisible(true);
			}
		});
		btnListadoPacientesPorMedico.setBounds(106, 117, 205, 37);
		panelInformes.add(btnListadoPacientesPorMedico);
		
		JButton btnEnfermedadesPorMedico = new JButton("Enfermedades tratadas por M\u00E9dico");
		btnEnfermedadesPorMedico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (frmListadoMedicosEnfermedades != null){
					frmListadoMedicosEnfermedades.toFront();
				}else{
					frmListadoMedicosEnfermedades = new ListadoEnfermedadesPorMedico();
				}	
				frmListadoMedicosEnfermedades.setVisible(true);
			}
		});
		btnEnfermedadesPorMedico.setBounds(106, 174, 205, 37);
		panelInformes.add(btnEnfermedadesPorMedico);
		
		JButton btnVolverInformes = new JButton("Volver");
		btnVolverInformes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (frmListadoPacientesPorMedicos != null){
					frmListadoPacientesPorMedicos = null;
				}
				if (frmListadoMedicosEnfermedades != null){
					frmListadoMedicosEnfermedades = null;
				}
				cambiaPantalla(panelInformes, panelPrincipal);
			}
		});
		btnVolverInformes.setBounds(125, 246, 164, 37);
		panelInformes.add(btnVolverInformes);
		panelInformes.setEnabled(false);
		panelInformes.setVisible(false);
		
		panelIngresoDatos = new JPanel();
		panelIngresoDatos.setBackground(new Color(153, 204, 153));
		panelIngresoDatos.setBounds(0, 0, 420, 325);
		frmRegistroPacientes.getContentPane().add(panelIngresoDatos);
		panelIngresoDatos.setLayout(null);
		
		JLabel label = new JLabel("Ingreso / Modificaci\u00F3n de datos");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Arial", Font.BOLD, 18));
		label.setBounds(62, 23, 300, 39);
		panelIngresoDatos.add(label);
		
		JButton btnAltaPaciente = new JButton("Alta Paciente");
		btnAltaPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (jdlAltaPaciente != null){
					jdlAltaPaciente.toFront();
				}else{
					jdlAltaPaciente = new AltaPaciente();
					
				}
				jdlAltaPaciente.setVisible(true);
			}
		});
		btnAltaPaciente.setBounds(116, 80, 181, 35);
		panelIngresoDatos.add(btnAltaPaciente);
		
		JButton btnModificacionPaciente = new JButton("Modificacion Paciente");
		btnModificacionPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (jdlModificacionPaciente != null){
					jdlModificacionPaciente.setVisible(true);
					jdlModificacionPaciente.toFront();
				}else{
					jdlModificacionPaciente = new ModificacionPaciente();
					jdlModificacionPaciente.setVisible(true);
				}
			}
		});
		btnModificacionPaciente.setBounds(116, 126, 181, 39);
		panelIngresoDatos.add(btnModificacionPaciente);
		
		JButton btnAltaMedico = new JButton("Alta M\u00E9dico");
		btnAltaMedico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (jdlAltaMedico != null){
					jdlAltaMedico.setVisible(true);
					jdlAltaMedico.toFront();
				}else{
					jdlAltaMedico = new AltaMedico();
					jdlAltaMedico.setVisible(true);
				}
			}
		});
		btnAltaMedico.setBounds(116, 176, 181, 35);
		panelIngresoDatos.add(btnAltaMedico);
		
		JButton btnModificacionMedico = new JButton("Modificaci\u00F3n M\u00E9dico");
		btnModificacionMedico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (jdlModificacionMedico != null){
					jdlModificacionMedico.setVisible(true);
					jdlModificacionMedico.toFront();
				}else{
					jdlModificacionMedico = new ModificacionMedico();
					jdlModificacionMedico.setVisible(true);
				}
			}
		});
		btnModificacionMedico.setBounds(116, 222, 181, 34);
		panelIngresoDatos.add(btnModificacionMedico);
		
		JButton btnVolverIngresoDatos = new JButton("Volver");
		btnVolverIngresoDatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (jdlAltaPaciente != null)
					jdlAltaPaciente.dispose();
					jdlAltaPaciente = null;
				if (jdlAltaMedico != null)
					jdlAltaMedico.dispose();
					jdlAltaMedico = null;
				if (jdlModificacionPaciente != null)
					jdlModificacionPaciente.dispose();
					jdlModificacionPaciente = null;
				if (jdlModificacionMedico != null)
					jdlModificacionMedico.dispose();
					jdlModificacionMedico = null;
				cambiaPantalla(panelIngresoDatos, panelPrincipal);
			}
		});
		btnVolverIngresoDatos.setBounds(116, 267, 181, 35);
		panelIngresoDatos.add(btnVolverIngresoDatos);
		panelIngresoDatos.setEnabled(false);
		panelIngresoDatos.setVisible(false);
		
		panelPrincipal = new JPanel();
		panelPrincipal.setBackground(new Color(204, 153, 204));
		panelPrincipal.setBounds(0, 0, 420, 325);
		frmRegistroPacientes.getContentPane().add(panelPrincipal);
		panelPrincipal.setLayout(null);
		
		JButton btnIngresoModificacion = new JButton("Ingreso / Modificaci\u00F3n de datos");
		btnIngresoModificacion.setBounds(106, 79, 204, 30);
		panelPrincipal.add(btnIngresoModificacion);
		
		JButton btnDiagnosticos = new JButton("Diagn\u00F3sticos");
		btnDiagnosticos.setBounds(106, 125, 204, 30);
		panelPrincipal.add(btnDiagnosticos);
		
		JButton btnInformes = new JButton("Informes");
		btnInformes.setBounds(106, 170, 204, 32);
		panelPrincipal.add(btnInformes);
		
		JButton btnListadoPacientesMedicos = new JButton("Listado de Pacientes y M\u00E9dicos");
		btnListadoPacientesMedicos.setBounds(105, 217, 204, 30);
		panelPrincipal.add(btnListadoPacientesMedicos);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setBounds(105, 271, 204, 32);
		panelPrincipal.add(btnSalir);
		
		JLabel lblNewLabel = new JLabel("Registro de Pacientes");
		lblNewLabel.setBounds(84, 23, 238, 30);
		panelPrincipal.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 19));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmRegistroPacientes.setVisible(false);
				System.exit(0);
			}
		});
		btnListadoPacientesMedicos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cambiaPantalla(panelPrincipal, panelListadoPacientesYMedicos);
			}
		});
		btnInformes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cambiaPantalla(panelPrincipal, panelInformes);
				
			}
		});
		btnDiagnosticos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnIngresoModificacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cambiaPantalla(panelPrincipal, panelIngresoDatos);
			}
		});
	}
	
	public void cambiaPantalla (JPanel panelViejo, JPanel panelNuevo){
		panelNuevo.setVisible(true);
		panelNuevo.setEnabled(true);
		panelViejo.setVisible(false);
		panelViejo.setEnabled(false);	
	}
}
