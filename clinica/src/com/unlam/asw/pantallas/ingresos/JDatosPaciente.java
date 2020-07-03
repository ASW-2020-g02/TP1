package com.unlam.asw.pantallas.ingresos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.unlam.asw.DB.DAO;
import com.unlam.asw.entities.Paciente;
import com.unlam.asw.pantallas.access.JLogin;
import com.unlam.asw.pantallas.general.JIngresos;

public class JDatosPaciente extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DAO dao;
	private JFrame frame;
	private JTextField txtCodPaciente;
	private JTextField txtNombrePaciente;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// Creo un nuevo thread para la ventanas
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Creo una instancia de JDatosPaciente y la hago visible
					JDatosPaciente frame = new JDatosPaciente();
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
	public JDatosPaciente() {
		// Configuraciï¿½n inicial de la ventana
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
		setTitle("Alta de paciente");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		// Panel que contendrï¿½ todos los elementos
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		// Label de cï¿½digo del paciente
		JLabel lblCodPaciente = new JLabel("C\u00F3digo");
		lblCodPaciente.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblCodPaciente.setBounds(72, 95, 80, 30);
		panel.add(lblCodPaciente);

		// Text field de cï¿½digo del paciente
		txtCodPaciente = new JTextField();
		txtCodPaciente.setBounds(162, 103, 189, 20);
		panel.add(txtCodPaciente);
		txtCodPaciente.setColumns(10);

		// Label de alta de paciente, titulo de la ventana
		JLabel lblAltaDePaciente = new JLabel("Alta de Paciente");
		lblAltaDePaciente.setHorizontalAlignment(SwingConstants.CENTER);
		lblAltaDePaciente.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblAltaDePaciente.setBounds(41, 14, 331, 25);
		panel.add(lblAltaDePaciente);

		// Text field de nombre del paciente
		txtNombrePaciente = new JTextField();
		txtNombrePaciente.setBounds(162, 176, 189, 20);
		panel.add(txtNombrePaciente);
		txtNombrePaciente.setColumns(10);

		// Label de nombre del paciente
		JLabel lblNombreDelPaciente = new JLabel("Nombre");
		lblNombreDelPaciente.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNombreDelPaciente.setBounds(72, 168, 80, 30);
		panel.add(lblNombreDelPaciente);

		// Boton para guardar en la base de datos
		JButton btnConfirmar = new JButton("<html><center>Confirmar</center></html>");
		btnConfirmar.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnConfirmar.setFocusPainted(false);
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				generarAltaPaciente();
			}
		});
		btnConfirmar.setBounds(36, 276, 156, 48);
		panel.add(btnConfirmar);

		// Boton para volver a la pantalla de ingresos
		JButton btnSalir = new JButton("<html><center>Cancelar</center></html>");
		btnSalir.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnSalir.setFocusPainted(false);
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JIngresos jp = new JIngresos();
				jp.setVisible(true);
				dispose();
			}
		});
		btnSalir.setBounds(223, 276, 156, 48);
		panel.add(btnSalir);

		// Obtengo la instancia del DAO
		dao = DAO.obtenerInstancia();
		// Centro la ventana en el monitor
		setLocationRelativeTo(null);

		try {
			// Creo el boton
			JButton botonAyuda = new JButton();

			// Seteo los bounds
			botonAyuda.setBounds(new Rectangle(371, 14, 32, 32));

			// Obtengo el url de la imagen
			URL url = JLogin.class.getResource("/informacion.png");

			// Creo el buffer para la imagen
			BufferedImage img;
			img = ImageIO.read(url);

			// Creo una variable del tipo ImageIcon
			ImageIcon image = new ImageIcon(img);
			// Seteo la imagen como icono
			botonAyuda.setIcon(image);

			// Pongo el cuadrado del mismo color de fondo
			botonAyuda.setBackground(new Color(245, 245, 220));
			botonAyuda.setBorderPainted(false);

			// Lo agrego al panel
			panel.add(botonAyuda);

			// Agrego el onClick para mostrar el diálogo
			botonAyuda.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, String.format(
							"<html>En primer lugar, se debe ingresar el código del paciente.<br>Luego, el nombre del mismo.</html>",
							100, 100), "Información", JOptionPane.INFORMATION_MESSAGE);

				}
			});
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	private void generarAltaPaciente() {
		String strCod = txtCodPaciente.getText().trim();
		String strNombre = txtNombrePaciente.getText().trim();
		int nombreLength = strNombre.length();

		// Validacion de parse-int
		if (esCodigoValido(strCod)) {
			int cod = Integer.parseInt(strCod);

			// Busca el paciente en una query, si existe devuelve true
			if (!existePaciente(cod)) {
				// Si el paciente no existe, chequeamos que se haya ingresado bien el nombre
				if (nombreLength <= 50 && nombreLength > 0) {
					registrarPaciente(new Paciente(strCod, strNombre));

					JOptionPane.showMessageDialog(null, "Paciente registrado con éxito en la base de datos.",
							"Paciente registrado", JOptionPane.INFORMATION_MESSAGE);

					// Reseteo los input
					txtCodPaciente.setText("");
					txtNombrePaciente.setText("");
				} else {
					JOptionPane.showMessageDialog(null,
							"El nombre ingresado excede el límite de 50 caracteres, o está vacío.",
							"Paciente registrado", JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "¡El paciente ya existe!", "Error",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "¡El código ingresado no es válido!", "Error",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public boolean esCodigoValido(String codigo) {
		try {
			// Si es posible realizar el parse Int, significa que es un String nï¿½merico
			// vï¿½lido
			Integer.parseInt(codigo);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean existePaciente(int cod) {
		Paciente paciente = null;
		try {
			// Busco en la base de datos el paciente por su codigo
			paciente = dao.buscarPacientePorCodigo(cod);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// En caso de que no devuelva nada, la variable inicial seguira en null, por lo
		// que no existe el paciente para dicho codigo
		if (paciente == null) {
			return false;
		} else {
			return true;
		}
	}

	public void registrarPaciente(Paciente pac) {
		try {
			dao.insertarPaciente(pac);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
