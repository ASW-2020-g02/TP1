package pantallas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import entidades.Usuario;
import otros.Constantes;
import otros.Encriptacion;
import otros.FuncionesComunes;

public class RegistrarUsuarios extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField txtpContra;
	private JPasswordField txtpContraConf;
	private JLabel lblDarDeAlta;
	private JButton btnNewButton;
	private JButton btnNewButton_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistrarUsuarios frame = new RegistrarUsuarios();
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
	public RegistrarUsuarios() {
		setTitle("Registrar Usuarios");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 380, 250);
		contentPane = new JPanel();
		contentPane.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
			}
		});
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Nombre Usuario");
		lblNewLabel.setBounds(39, 42, 133, 26);
		contentPane.add(lblNewLabel);

		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setBounds(39, 81, 133, 26);
		contentPane.add(lblContrasea);

		JLabel lblConfirmarContrasea = new JLabel("Confirmar contrase\u00F1a");
		lblConfirmarContrasea.setBounds(39, 120, 133, 26);
		contentPane.add(lblConfirmarContrasea);

		txtUsuario = new JTextField();
		txtUsuario.setToolTipText("El nombre de usuario permite letras y n\u00FAmeros.");
		txtUsuario.setBounds(184, 44, 116, 22);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);

		txtpContra = new JPasswordField();
		txtpContra.setToolTipText("La contrase\u00F1a puede ser alfanum\u00E9rica");
		txtpContra.setBounds(184, 83, 116, 24);
		contentPane.add(txtpContra);

		txtpContraConf = new JPasswordField();
		txtpContraConf.setToolTipText("La contrase\u00F1a debe igual a la de arriba.");
		txtpContraConf.setBounds(184, 120, 116, 24);
		contentPane.add(txtpContraConf);

		lblDarDeAlta = new JLabel("Dar de alta nuevo usuario para el sistema");
		lblDarDeAlta.setForeground(Color.BLUE);
		lblDarDeAlta.setFont(new Font("Calibri", Font.ITALIC, 15));
		lblDarDeAlta.setBounds(12, 13, 288, 16);
		contentPane.add(lblDarDeAlta);

		ArrayList<Usuario> usuarios = obtenerUsuarios();

		btnNewButton = new JButton("Aceptar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					if (txtUsuario.getText().length() < 4 || txtUsuario.getText().length() > 10)
						throw new Exception("El usuario debe contener como mínimo 4 caractéres y como máximo 10.");

					if (!FuncionesComunes.isValidUserOrPass(txtUsuario.getText()))
						throw new Exception(
								"El usuario no tiene un formato válido, solo se permiten letras y/o números.");

					Usuario usu = devuelveUsuario(usuarios, txtUsuario.getText());
					if (usu != null)
						throw new Exception("El usuario ya existe, elija otro nombre de usuario");

					if (txtpContra.getText().length() < 4 || txtpContra.getText().length() > 10)
						throw new Exception("La contraseña debe contener como mínimo 4 caractéres y como máximo 10.");

					if (!FuncionesComunes.isValidUserOrPass(txtpContra.getText()))
						throw new Exception(
								"La contraseña no tiene un formato válido, solo se permiten letras y/o números.");

					if (!txtpContra.getText().equals(txtpContraConf.getText()))
						throw new Exception("La contraseña no coincide, escriba las mismas contraseñas.");

					registrarUsuario(txtUsuario.getText(), txtpContra.getText());
					JOptionPane.showMessageDialog(null, "¡Alta realizada satisfactoriamente!", "Confirmación",
							JOptionPane.INFORMATION_MESSAGE);
					
					Login login = new Login();
					login.setVisible(true);
					dispose();

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnNewButton.setBounds(49, 159, 97, 25);
		contentPane.add(btnNewButton);

		btnNewButton_1 = new JButton("Cancelar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Login login = new Login();
				login.setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setBounds(194, 159, 97, 25);
		contentPane.add(btnNewButton_1);
		setLocationRelativeTo(null);
		getRootPane().setDefaultButton(btnNewButton);
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

	private void registrarUsuario(String u, String p) {
		try {
			FileWriter datopac = new FileWriter(Constantes.archivoUsuarios, true);
			String registro = Encriptacion.Encriptar(u + "," + p);
			datopac.write(registro + "\n");
			datopac.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
