package com.unlam.asw.pantallas;

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
import com.unlam.asw.pantallas.access.JLogin;
import com.unlam.asw.pantallas.general.JInformes;
import com.unlam.asw.pantallas.general.JIngresos;

public class JInicial extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// Creo un nuevo thread para la ventanas
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Creo una instancia de JInicial y la hago visible
					JInicial frame = new JInicial();
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
	public JInicial() {
		// Configuración inicial de la ventana
		setResizable(false);
		// Modifico el label del yes button
		UIManager.put("OptionPane.yesButtonText", "Si");
		// Agrego una vetana de dialogo al intentar cerrar el programa
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Centro Asistencial Los Pinares");
		setBounds(100, 100, 429, 404);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		// Creo el panel que contendrá todos los elementos
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		// Boton para acceder a la pantalla de ingresos
		JButton btnIngresoDeDatos = new JButton("<html><center>Ingreso de datos</center></html>");
		btnIngresoDeDatos.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnIngresoDeDatos.setFocusPainted(false);
		btnIngresoDeDatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JIngresos ingreso = new JIngresos();
				ingreso.setVisible(true);
				dispose();

			}
		});
		btnIngresoDeDatos.setBounds(99, 45, 240, 95);
		panel.add(btnIngresoDeDatos);

		// Boton para acceder a la pantalla de informes
		JButton btnInformes = new JButton("<html><center>Informes</center></html>");
		btnInformes.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnInformes.setFocusPainted(false);
		btnInformes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JInformes inf = new JInformes();
				inf.setVisible(true);
				dispose();

			}
		});
		btnInformes.setBounds(99, 187, 240, 95);
		panel.add(btnInformes);

		// Boton para cerrar el programa
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Para evitar problemas, se debe detener de forma correcta la base de datos
				JLogin login = new JLogin();
				login.setVisible(true);
				dispose();
			}
		});
		btnSalir.setBounds(329, 332, 74, 22);
		panel.add(btnSalir);

		// Conecto el DAO
		DAO dao = DAO.obtenerInstancia();
		// Centro la ventana en el monitor
		setLocationRelativeTo(null);
	}

}
