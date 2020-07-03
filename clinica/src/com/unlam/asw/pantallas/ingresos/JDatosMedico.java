package com.unlam.asw.pantallas.ingresos;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.unlam.asw.DB.DAO;
import com.unlam.asw.entities.Medico;
import com.unlam.asw.pantallas.access.JLogin;
import com.unlam.asw.pantallas.general.JIngresos;
import java.awt.Rectangle;

public class JDatosMedico extends JFrame {

	private JPanel contentPane;
	private DAO dao;
	private JFrame frame;
	private JTextField txtCodMedico;
	private JTextField txtNombre;
	private JTextField txtEspecializacion;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// Creo un nuevo thread para la ventanas
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Creo una instancia de JDatosMedico y la hago visible
					JDatosMedico frame = new JDatosMedico();
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
	public JDatosMedico() {
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
		setTitle("Registro de médico");

		// Panel que contendrï¿½ los distintos elementos
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		// Label de cï¿½digo de mï¿½dico
		JLabel lblCodMedico = new JLabel("C\u00F3digo");
		lblCodMedico.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblCodMedico.setBounds(36, 77, 124, 30);
		panel.add(lblCodMedico);

		// Text field de cï¿½digo de mï¿½dico
		txtCodMedico = new JTextField();
		txtCodMedico.setBounds(183, 85, 189, 20);
		panel.add(txtCodMedico);
		txtCodMedico.setColumns(10);

		// Label de registro de mï¿½dico, funciona como titular de la ventana
		JLabel lblRegistro = new JLabel("Registro de m\u00E9dico");
		lblRegistro.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistro.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblRegistro.setBounds(41, 14, 331, 25);
		panel.add(lblRegistro);

		// Text field de nombre del mï¿½dico
		txtNombre = new JTextField();
		txtNombre.setBounds(183, 136, 189, 20);
		panel.add(txtNombre);
		txtNombre.setColumns(10);

		// Label del nombre del mï¿½dico
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNombre.setBounds(36, 128, 124, 30);
		panel.add(lblNombre);

		// Boton para confirmar la creaciï¿½n de un registro de mï¿½dico
		JButton btnConfirmar = new JButton("<html><center>Confirmar</center></html>");
		btnConfirmar.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnConfirmar.setFocusPainted(false);
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				registrarMedico();
			}
		});
		btnConfirmar.setBounds(36, 276, 156, 48);
		panel.add(btnConfirmar);

		// Boton para vovler a la pantalla de ingresos
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

		// Label de especializaciï¿½n del mï¿½dico
		JLabel lblEspecializacion = new JLabel("Especializaci\u00F3n");
		lblEspecializacion.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblEspecializacion.setBounds(36, 182, 137, 30);
		panel.add(lblEspecializacion);

		// Text field de especializaciï¿½n del mï¿½dico
		txtEspecializacion = new JTextField();
		txtEspecializacion.setColumns(10);
		txtEspecializacion.setBounds(183, 190, 189, 20);
		panel.add(txtEspecializacion);

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
							"<html>En primer lugar, se debe ingresar el código del médico.<br>Luego, el nombre y por último su especialización.</html>",
							100, 100), "Información", JOptionPane.INFORMATION_MESSAGE);

				}
			});
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public void registrarMedico() {
		String strCodMed = txtCodMedico.getText().trim();
		String strNombre = txtNombre.getText().trim();
		String strEspe = txtEspecializacion.getText().trim();

		// Validacion de parse-int
		if (esCodigoValido(strCodMed)) {
			int codMed = Integer.parseInt(strCodMed);

			// Busca al medico en una query, si existe devuelve true
			if (!existeMedico(codMed)) {

				// Si el medico existe, chequeamos que se haya ingresado bien el nombre
				if (strNombre.length() <= 50 && strNombre.length() > 0) {
					// Chequeamos la longitud de la especialidad
					if (strEspe.length() <= 50 && strEspe.length() > 0) {
						try {
							// Creamos un objeto del tipo mï¿½dico con los datos de los textfields
							Medico med = new Medico(strCodMed, strNombre, strEspe);
							// Hacemos una llamada para insertar al mï¿½dico en la DB
							dao.insertarMedico(med);
						} catch (Exception e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(null, "Ocurrió un error con la BD.", "Error",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}

						JOptionPane.showMessageDialog(null, "Medico registrado con éxito en la base de datos.",
								"Mï¿½dico registrado", JOptionPane.INFORMATION_MESSAGE);
						txtCodMedico.setText("");
						txtNombre.setText("");
						txtEspecializacion.setText("");
					} else {
						JOptionPane.showMessageDialog(null, "La especialización se encuentra vacía o es muy grande.",
								"Error", JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "El nombre se encuentra vacía o es muy grande.", "Error",
							JOptionPane.INFORMATION_MESSAGE);
				}

			} else {
				JOptionPane.showMessageDialog(null, "El medico ya existe.", "Error", JOptionPane.INFORMATION_MESSAGE);
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

	public boolean existeMedico(int cod) {
		Medico medico = null;
		try {
			// Busco en la base de datos el mï¿½dico por su codigo
			medico = dao.buscarMedicoPorCodigo(cod);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// En caso de que no devuelva nada, la variable inicial seguira en null, por lo
		// que no existe el mï¿½dico para dicho codigo
		if (medico == null) {
			return false;
		} else {
			return true;
		}
	}

}
