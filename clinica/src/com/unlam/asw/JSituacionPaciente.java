package com.unlam.asw;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.unlam.asw.DB.DAO;

public class JSituacionPaciente extends JFrame {

	private JPanel contentPane;
	private DAO dao;
	private JFrame frame;
	private JTextField txtCodPaciente;
	private JTextField txtNombrePaciente;
	private JTextField txtCodDiagnostico;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JSituacionPaciente frame = new JSituacionPaciente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JSituacionPaciente() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblCodPaciente = new JLabel("C\u00F3digo paciente");
		lblCodPaciente.setBounds(42, 50, 118, 14);
		panel.add(lblCodPaciente);
		
		txtCodPaciente = new JTextField();
		txtCodPaciente.setBounds(166, 47, 162, 20);
		panel.add(txtCodPaciente);
		txtCodPaciente.setColumns(10);
		
		JLabel lblSituacion = new JLabel("Situaci\u00F3n del Paciente");
		lblSituacion.setBounds(173, 14, 151, 14);
		panel.add(lblSituacion);
		
		txtNombrePaciente = new JTextField();
		txtNombrePaciente.setBounds(166, 78, 162, 20);
		panel.add(txtNombrePaciente);
		txtNombrePaciente.setColumns(10);
		
		JLabel lblCodigoMedico = new JLabel("C\u00F3digo m\u00E9dico");
		lblCodigoMedico.setBounds(42, 81, 117, 14);
		panel.add(lblCodigoMedico);
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				registrarSituacion();
			}
		});
		btnConfirmar.setBounds(82, 200, 118, 23);
		panel.add(btnConfirmar);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JIngresoPacientes jp = new JIngresoPacientes();
				jp.setVisible(true);
				dispose();
			}
		});
		btnSalir.setBounds(248, 200, 118, 23);
		panel.add(btnSalir);
		
		txtCodDiagnostico = new JTextField();
		txtCodDiagnostico.setColumns(10);
		txtCodDiagnostico.setBounds(166, 109, 162, 20);
		panel.add(txtCodDiagnostico);
		
		JLabel lblCodDiag = new JLabel("N\u00FAm. Diagn\u00F3stico");
		lblCodDiag.setBounds(42, 112, 117, 14);
		panel.add(lblCodDiag);

		dao = new DAO();
		setLocationRelativeTo(null);
	}
}
