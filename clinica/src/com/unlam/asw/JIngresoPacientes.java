package com.unlam.asw;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class JIngresoPacientes extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JIngresoPacientes frame = new JIngresoPacientes();
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
	public JIngresoPacientes() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 268);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JButton btnDatosPaciente = new JButton("Datos del paciente");
		btnDatosPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDatosPaciente dp = new JDatosPaciente();
				dp.setVisible(true);
				dispose();
			}
		});
		btnDatosPaciente.setBounds(148, 23, 140, 40);
		panel.add(btnDatosPaciente);
		
		JButton btnSituPaciente = new JButton("Situaci\u00F3n del paciente");
		btnSituPaciente.setBounds(148, 74, 140, 40);
		panel.add(btnSituPaciente);
		
		JButton btnDatosDelMdico = new JButton("Datos del m\u00E9dico");
		btnDatosDelMdico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDatosDelMdico.setBounds(148, 125, 140, 40);
		panel.add(btnDatosDelMdico);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JInicial inicial = new JInicial();
				inicial.setVisible(true);
				dispose();
			}
		});
		btnSalir.setBounds(148, 176, 140, 40);
		panel.add(btnSalir);
		setLocationRelativeTo(null);
	}

}
