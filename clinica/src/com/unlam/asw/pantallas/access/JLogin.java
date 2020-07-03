package com.unlam.asw.pantallas.access;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.unlam.asw.DB.DAO;
import com.unlam.asw.entities.Usuario;
import com.unlam.asw.pantallas.JInicial;
import com.unlam.asw.utils.InvalidPasswordException;
import com.unlam.asw.utils.Utils;

public class JLogin extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DAO dao;
	private JFrame frame;
	private JTextField txtEmail;
	private JTextField txtPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// Creo un nuevo thread para la ventanas
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Creo una instancia de JDatosPaciente y la hago visible
					JLogin frame = new JLogin();
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
	public JLogin() {
		// Configuración inicial de la ventana
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Modifico el label del yes button
		UIManager.put("OptionPane.yesButtonText", "Si");
		// Agrego una ventana de dialogo al intentar cerrar el programa
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int confirmed = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea salir?", "Atención",
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
		setBounds(100, 100, 429, 404);
		setTitle("Ingreso al sistema");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		// Panel que contendrá todos los elementos
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		// Label de código del paciente
		JLabel lblEmail = new JLabel("E-mail");
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblEmail.setBounds(10, 127, 393, 30);
		panel.add(lblEmail);

		// Text field de código del paciente
		txtEmail = new JTextField();
		txtEmail.setBounds(81, 156, 257, 20);
		panel.add(txtEmail);
		txtEmail.setColumns(10);

		// Label de alta de paciente, titulo de la ventana
		URL url = JLogin.class.getResource("/hospital.png");
		try {
			BufferedImage img = ImageIO.read(url);
			ImageIcon image = new ImageIcon(img);
			JLabel lblLogo = new JLabel("");
			lblLogo.setIcon(image);
			lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
			lblLogo.setBounds(10, 11, 393, 80);
			panel.add(lblLogo);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		// Text field de nombre del paciente
		txtPassword = new JPasswordField();
		txtPassword.setBounds(81, 217, 257, 20);
		panel.add(txtPassword);
		txtPassword.setColumns(10);

		// Label de nombre del paciente
		JLabel lblPassword = new JLabel("Contrase\u00F1a");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblPassword.setBounds(10, 187, 393, 30);
		panel.add(lblPassword);

		// Boton para guardar en la base de datos
		JButton btnIngresar = new JButton("<html><center>Ingresar</center></html>");
		btnIngresar.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnIngresar.setFocusPainted(false);
		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ingresarAlSistema();
			}
		});
		btnIngresar.setBounds(81, 259, 257, 48);
		panel.add(btnIngresar);

		JLabel lblLosPinares = new JLabel("Centro Asistencial Los Pinares");
		lblLosPinares.setHorizontalAlignment(SwingConstants.CENTER);
		lblLosPinares.setFont(new Font("Tahoma", Font.BOLD, 17));

		lblLosPinares.setBounds(10, 102, 393, 14);
		panel.add(lblLosPinares);

		JButton btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnRegistrarse.setBounds(147, 318, 115, 23);
		btnRegistrarse.setFocusPainted(false);
		btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JRegister register = new JRegister();
				register.setVisible(true);
				dispose();
			}
		});
		panel.add(btnRegistrarse);

		// Obtengo la instancia del DAO
		dao = DAO.obtenerInstancia();
		// Centro la ventana en el monitor
		setLocationRelativeTo(null);
	}

	private void ingresarAlSistema() {
		String strEmail = txtEmail.getText().trim();
		String strPassword = txtPassword.getText().trim();

		try {
			if (Utils.esEmailValido(strEmail)) {
				// Validacón de contraseña y de email
				if (Utils.esPasswordValida(strPassword)) {
					// Busca el usuario en una query, si existe devuelve true
					if (existeUsuario(strEmail, strPassword)) {
						JInicial pantallaInicial = new JInicial();
						pantallaInicial.setVisible(true);
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "El email o la contraseña ingresados son incorrectos",
								"Error", JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "El email o la contraseña ingresados son incorrectos", "Error",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}

			else {
				JOptionPane.showMessageDialog(null, "El email ingresado es inválido", "Error",
						JOptionPane.INFORMATION_MESSAGE);
			}

		} catch (InvalidPasswordException ex) {
			JOptionPane.showMessageDialog(null, "La contraseña ingresada es inválida", "Error",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public boolean existeUsuario(String email, String password) {
		Usuario usuario = null;
		try {
			// Busco en la base de datos el usuario, con su email y password
			// Primero, debo encriptar la password
			usuario = dao.buscarUsuario(email, password);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return true;
		}

		// En caso de que no devuelva nada, la variable inicial seguira en null, por lo
		// que no existe el usuario para dicha combinacion de email y contraseña
		if (usuario == null) {
			return false;
		} else {
			return true;
		}
	}
}
