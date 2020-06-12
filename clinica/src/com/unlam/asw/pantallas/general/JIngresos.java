package com.unlam.asw.pantallas.general;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.unlam.asw.DB.DAO;
import com.unlam.asw.pantallas.JInicial;
import com.unlam.asw.pantallas.ingresos.JDatosMedico;
import com.unlam.asw.pantallas.ingresos.JDatosPaciente;
import com.unlam.asw.pantallas.ingresos.JSituacionPaciente;

public class JIngresos extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// Creo un nuevo thread para la ventanas
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Creo una instancia de JIngresos y la hago visible
					JIngresos frame = new JIngresos();
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
	public JIngresos() {
		// Configuración inicial de la ventana
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 429, 404);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setTitle("Ingreso de datos");

		// Modifico el label del yes button
		UIManager.put("OptionPane.yesButtonText", "Si");

		// Agrego una ventana de dialogo al intentar cerrar el programa
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int confirmed = JOptionPane.showConfirmDialog(null, "Está seguro que desea salir?", "Atención",
						JOptionPane.YES_NO_OPTION);
				if (confirmed == JOptionPane.YES_OPTION) {
					// Para evitar problemas, se debe detener de forma correcta la base de datos
					DAO.obtenerInstancia().cerrar();
					setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				} else {
					setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				}
			}
		});

		// Creo el panel que contendrá todos los elementos
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		// Boton para acceder a la pantalla de ingreso de datos del paciente
		JButton btnDatosPaciente = new JButton("<html><center>Datos del paciente</center></html>");
		btnDatosPaciente.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnDatosPaciente.setFocusPainted(false);
		btnDatosPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDatosPaciente dp = new JDatosPaciente();
				dp.setVisible(true);
				dispose();
			}
		});
		btnDatosPaciente.setBounds(97, 56, 240, 68);
		panel.add(btnDatosPaciente);

		// Boton para acceder a la pantalla de ingreso de situación del paciente
		JButton btnSituPaciente = new JButton("<html><center>Situaci\u00F3n del paciente</center></html>");
		btnSituPaciente.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnSituPaciente.setFocusPainted(false);
		btnSituPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JSituacionPaciente jp = new JSituacionPaciente();
				jp.setVisible(true);
				dispose();
			}
		});
		btnSituPaciente.setBounds(97, 135, 240, 68);
		panel.add(btnSituPaciente);
		
		// Boton para acceder a la pantalla de ingreso de datos del médico
		JButton btnDatosDelMdico = new JButton("<html><center>Datos del m\u00E9dico</center></html>");
		btnDatosDelMdico.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnDatosDelMdico.setFocusPainted(false);
		btnDatosDelMdico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDatosMedico jd = new JDatosMedico();
				jd.setVisible(true);
				dispose();
			}
		});
		btnDatosDelMdico.setBounds(97, 214, 240, 68);
		panel.add(btnDatosDelMdico);
		
		// Boton para volver a la pantalla anterior
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
		
		// Centro la ventana en el monitor
		setLocationRelativeTo(null);
	}

}
