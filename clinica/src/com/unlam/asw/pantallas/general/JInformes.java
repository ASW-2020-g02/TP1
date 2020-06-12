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
		setBounds(100, 100, 450, 235);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JButton btnListadoPac = new JButton("Listado pacientes por m\u00E9dico");
		btnListadoPac.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPacienteXMedico pacientesXMedico = new JPacienteXMedico();
				pacientesXMedico.setVisible(true);
				dispose();
			}
		});
		btnListadoPac.setBounds(98, 31, 240, 40);
		panel.add(btnListadoPac);

		JButton btnEnfMedico = new JButton("Enfermedades que antiende c/m\u00E9dico");
		btnEnfMedico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JEnfermedadesXMedico enfermedadesXMedico = new JEnfermedadesXMedico();
				enfermedadesXMedico.setVisible(true);
				dispose();
			}
		});
		btnEnfMedico.setBounds(98, 82, 240, 40);
		panel.add(btnEnfMedico);

		JButton btnAnterior = new JButton("Anterior");
		btnAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JInicial inicial = new JInicial();
				inicial.setVisible(true);
				dispose();
			}
		});
		btnAnterior.setBounds(98, 133, 240, 40);
		panel.add(btnAnterior);
		setLocationRelativeTo(null);
	}

}
