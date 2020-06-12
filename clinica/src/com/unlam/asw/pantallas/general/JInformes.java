package com.unlam.asw.pantallas.general;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.unlam.asw.pantallas.JInicial;
import com.unlam.asw.pantallas.informes.JEnfermedadesXMedico;
import com.unlam.asw.pantallas.informes.JPacienteXMedico;
import java.awt.Font;

public class JInformes extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JInformes frame = new JInformes();
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
	public JInformes() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 429, 404);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setTitle("Control de pacientes - Informes");

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		JButton btnListadoPac = new JButton("<html><center>Listado de pacientes<br /> por m\u00E9dico</center></html>");
		btnListadoPac.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnListadoPac.setFocusPainted(false);
		btnListadoPac.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPacienteXMedico pacientesXMedico = new JPacienteXMedico();
				pacientesXMedico.setVisible(true);
				dispose();
			}
		});
		btnListadoPac.setBounds(84, 52, 240, 95);
		panel.add(btnListadoPac);

		JButton btnEnfMedico = new JButton(
				"<html><center>Enfermedades que<br /> atiende cada m\u00E9dico</center></html>");
		btnEnfMedico.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnEnfMedico.setFocusPainted(false);
		btnEnfMedico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JEnfermedadesXMedico enfermedadesXMedico = new JEnfermedadesXMedico();
				enfermedadesXMedico.setVisible(true);
				dispose();
			}
		});
		btnEnfMedico.setBounds(84, 181, 240, 95);
		panel.add(btnEnfMedico);

		JButton btnAnterior = new JButton("Volver");
		btnAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JInicial inicial = new JInicial();
				inicial.setVisible(true);
				dispose();
			}
		});
		btnAnterior.setBounds(329, 332, 74, 22);
		btnAnterior.setFocusPainted(false);
		panel.add(btnAnterior);
		setLocationRelativeTo(null);
	}

}
