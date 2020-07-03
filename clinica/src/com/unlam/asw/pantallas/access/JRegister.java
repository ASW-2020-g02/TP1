package com.unlam.asw.pantallas.access;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
import com.unlam.asw.utils.InvalidPasswordException;
import com.unlam.asw.utils.Utils;

public class JRegister extends JFrame {

	private JPanel contentPane;
	private DAO dao;
	private JFrame frame;
	private JTextField txtNombre;
	private JTextField txtEmail;
	private JTextField txtPassword;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// Creo un nuevo thread para la ventanas
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Creo una instancia de JDatosMedico y la hago visible
					JRegister frame = new JRegister();
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
	public JRegister() {
		// Configuraciï¿½n inicial de la ventana
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		setResizable(false);
		setBounds(100, 100, 429, 404);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setTitle("Registro de usuario");

		// Panel que contendrï¿½ los distintos elementos
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		// Label de cï¿½digo de mï¿½dico
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNombre.setBounds(36, 77, 124, 30);
		panel.add(lblNombre);

		// Text field de cï¿½digo de mï¿½dico
		txtNombre = new JTextField();
		txtNombre.setBounds(183, 85, 189, 20);
		panel.add(txtNombre);
		txtNombre.setColumns(10);

		// Label de registro de mï¿½dico, funciona como titular de la ventana
		JLabel lblRegistro = new JLabel("Registro de usuario");
		lblRegistro.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistro.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblRegistro.setBounds(41, 14, 331, 25);
		panel.add(lblRegistro);

		// Text field de nombre del mï¿½dico
		txtEmail = new JTextField();
		txtEmail.setBounds(183, 136, 189, 20);
		panel.add(txtEmail);
		txtEmail.setColumns(10);

		// Label del nombre del mï¿½dico
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblEmail.setBounds(36, 128, 124, 30);
		panel.add(lblEmail);

		// Boton para confirmar la creaciï¿½n de un registro de mï¿½dico
		JButton btnConfirmar = new JButton("<html><center>Confirmar</center></html>");
		btnConfirmar.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnConfirmar.setFocusPainted(false);
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				registrarUsuario();
			}
		});
		btnConfirmar.setBounds(36, 276, 336, 48);
		panel.add(btnConfirmar);

		// Boton para vovler a la pantalla de ingresos
//		JButton btnSalir = new JButton("<html><center>Cancelar</center></html>");
//		btnSalir.setFont(new Font("Tahoma", Font.BOLD, 17));
//		btnSalir.setFocusPainted(false);
//		btnSalir.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				JLogin login = new JLogin();
//				login.setVisible(true);
//				dispose();
//			}
//		});
//		btnSalir.setBounds(223, 276, 156, 48);
//		panel.add(btnSalir);

		// Label de especializaciï¿½n del mï¿½dico
		JLabel lblPassword = new JLabel("Contrase\u00F1a");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblPassword.setBounds(36, 182, 137, 30);
		panel.add(lblPassword);

		// Text field de especializaciï¿½n del mï¿½dico
		txtPassword = new JPasswordField();
		txtPassword.setColumns(10);
		txtPassword.setBounds(183, 190, 189, 20);
		panel.add(txtPassword);

		// Obtengo la instancia del DAO
		dao = DAO.obtenerInstancia();
		// Centro la ventana en el monitor
		setLocationRelativeTo(null);
	}

	public void registrarUsuario() {
		String strNombre = txtNombre.getText().trim();
		String strEmail = txtEmail.getText().trim();
		String strPassword = txtPassword.getText().trim();
		// Verifico si el mail y el nombre de usuario estan completos
		if (strNombre.length() == 0 || strEmail.length() == 0) {
			JOptionPane.showMessageDialog(null, "Complete los campos", "Error", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		try {
			// Validaciï¿½n de contraseï¿½a y de email
			if (!Utils.esPasswordValida(strPassword) || !Utils.esEmailValido(strEmail)) {
				// Informo que el mail ingresado no es valido
				JOptionPane.showMessageDialog(null, "El email ingresado no es válido", "Error",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		} catch (InvalidPasswordException ex) {
			// Muestro un mensaje de error
			JOptionPane.showMessageDialog(null, ex.printMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		// Busca el usuario en una query, si existe devuelve true
		if (!existeUsuario(strEmail)) {
			// Si el medico existe, chequeamos que se haya ingresado bien el nombre
			if (strNombre.length() <= 50) {
				try {
					// Creamos un objeto del tipo usuario con los datos de los textfields
					Usuario usuario = new Usuario(strNombre, strPassword, strEmail);
					// Hacemos una llamada para insertar al usuario en la DB
					dao.insertarUsuario(usuario);
					JOptionPane.showMessageDialog(null, "Usuario registrado con éxito en la base de datos.",
							"Usuario registrado", JOptionPane.INFORMATION_MESSAGE);

					txtNombre.setText("");
					txtEmail.setText("");
					txtPassword.setText("");
				} catch (Exception e) {
					e.printStackTrace();
					// Error en la DB
					JOptionPane.showMessageDialog(null, "Ocurrió un error con la BD.", "Error",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}

			} else {
				// Informe que el nombre no se adecua
				JOptionPane.showMessageDialog(null, "El nombre se encuentra vacío o es muy grande.", "Error",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			// Informo que ya exist eun usuario con dicho mail
			JOptionPane.showMessageDialog(null, "Ya existe un usuario con el email ingresado", "Error",
					JOptionPane.INFORMATION_MESSAGE);
		}

	}

	public boolean existeUsuario(String email) {
		Usuario usuario = null;
		try {
			// Busco en la base de datos el usuario por su email
			usuario = dao.buscarUsuarioPorEmail(email);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// En caso de que no devuelva nada, la variable inicial seguira en null, por lo
		// que no existe el usuario para dicho email
		if (usuario == null) {
			return false;
		} else {
			return true;
		}
	}
}
