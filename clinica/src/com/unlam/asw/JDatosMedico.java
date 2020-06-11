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

public class JDatosMedico extends JFrame {

	private JPanel contentPane;
	private DAO dao;
	private JFrame frame;
	private JTextField txtCodMedico;
	private JTextField txtNombre;
	private JTextField txtEspecializacion;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JDatosMedico frame = new JDatosMedico();
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
	public JDatosMedico() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblCodMedico = new JLabel("C\u00F3digo");
		lblCodMedico.setBounds(95, 50, 65, 14);
		panel.add(lblCodMedico);
		
		txtCodMedico = new JTextField();
		txtCodMedico.setBounds(166, 47, 162, 20);
		panel.add(txtCodMedico);
		txtCodMedico.setColumns(10);
		
		JLabel lblRegistro = new JLabel("Registro de m\u00E9dico");
		lblRegistro.setBounds(173, 14, 151, 14);
		panel.add(lblRegistro);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(166, 75, 162, 20);
		panel.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(95, 77, 64, 14);
		panel.add(lblNombre);
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				registrarMedico();
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
		
		JLabel lblEspecializacion = new JLabel("Especializaci\u00F3n");
		lblEspecializacion.setBounds(69, 110, 90, 14);
		panel.add(lblEspecializacion);
		
		txtEspecializacion = new JTextField();
		txtEspecializacion.setColumns(10);
		txtEspecializacion.setBounds(166, 108, 162, 20);
		panel.add(txtEspecializacion);

		dao = new DAO();
		setLocationRelativeTo(null);
	}

}
