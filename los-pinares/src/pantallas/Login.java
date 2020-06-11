package pantallas;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import entidades.Usuario;
import otros.Constantes;
import otros.Encriptacion;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField txtPass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		setTitle("Acceso al sistema");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 284, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsuario.setBounds(22, 26, 95, 27);
		contentPane.add(lblUsuario);

		JLabel lblContrasea = new JLabel("Contrase\u00F1a:");
		lblContrasea.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblContrasea.setHorizontalAlignment(SwingConstants.CENTER);
		lblContrasea.setBounds(22, 67, 95, 27);
		contentPane.add(lblContrasea);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(119, 29, 149, 22);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);

		ArrayList<Usuario> usuarios = obtenerUsuarios();

		JButton btnIngresar = new JButton("Ingresar");
		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (txtUsuario.getText().length() <= 0 || txtPass.getText().length() <= 0)
						throw new Exception("No pueden haber campos vacíos");

					Usuario usu = devuelveUsuario(usuarios, txtUsuario.getText());
					if (usu == null)
						throw new Exception("El usuario NO existe. Registrese o ingrese nuevamente los datos.");

					if (!usu.getContrasenia().equals(txtPass.getText()))
						throw new Exception("La contraseña no corresponde al usuario: " + usu.getUsuario());

					// Principal window = new Principal();
					// window.frmRegistroPacientes.setVisible(true);

					Main main = new Main();
					main.setVisible(true);

					dispose();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnIngresar.setBounds(22, 118, 246, 39);
		contentPane.add(btnIngresar);

		JButton btnCancelar = new JButton("Registrarse");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				RegistrarUsuarios ventanaRegistro = new RegistrarUsuarios();
				ventanaRegistro.setVisible(true);

				dispose();
			}
		});
		btnCancelar.setBounds(22, 168, 246, 39);
		contentPane.add(btnCancelar);

		txtPass = new JPasswordField();
		txtPass.setBounds(119, 69, 149, 25);
		contentPane.add(txtPass);

		setLocationRelativeTo(null);
		getRootPane().setDefaultButton(btnIngresar);
	}

	private ArrayList<Usuario> obtenerUsuarios() {
		try {
			BufferedReader entrada = new BufferedReader(new FileReader(Constantes.archivoUsuarios));
			String s = "";
			ArrayList<Usuario> ret = new ArrayList<Usuario>();
			while ((s = entrada.readLine()) != null) {
				String cd = "";
				try {
					cd = Encriptacion.Desencriptar(s);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String[] cadena = cd.split(",");
				String usu = cadena[0];
				String con = cadena[1];
				ret.add(new Usuario(usu, con));
			}
			entrada.close();
			return ret;
		} catch (java.io.IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private Usuario devuelveUsuario(ArrayList<Usuario> usuarios, String u) {
		for (Usuario usuario : usuarios) {
			if (usuario.getUsuario().equals(u))
				return usuario;
		}
		return null;
	}
}
