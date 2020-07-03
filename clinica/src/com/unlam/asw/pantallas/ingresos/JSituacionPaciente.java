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
import com.unlam.asw.entities.Medico;
import com.unlam.asw.entities.Paciente;
import com.unlam.asw.entities.Situacion;
import com.unlam.asw.pantallas.access.JLogin;
import com.unlam.asw.pantallas.general.JIngresos;

public class JSituacionPaciente extends JFrame {

	private JPanel contentPane;
	private DAO dao;
	private JFrame frame;
	private JTextField txtCodPaciente;
	private JTextField txtCodMed;
	private JTextField txtDiagnostico;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// Creo un nuevo thread para la ventanas
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Creo una instancia de JSituacionPaciente y la hago visible
					JSituacionPaciente frame = new JSituacionPaciente();
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
	public JSituacionPaciente() {
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
		setTitle("Alta de situaciï¿½n de paciente");

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		// Label del cï¿½digo de paciente
		JLabel lblCodPaciente = new JLabel("C\u00F3digo paciente");
		lblCodPaciente.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblCodPaciente.setBounds(36, 77, 143, 30);
		panel.add(lblCodPaciente);

		// Text field del cï¿½digo de paciente
		txtCodPaciente = new JTextField();
		txtCodPaciente.setBounds(183, 85, 189, 20);
		panel.add(txtCodPaciente);
		txtCodPaciente.setColumns(10);

		// Label de situaciï¿½n, titular de la ventana
		JLabel lblSituacion = new JLabel("Situaci\u00F3n del Paciente");
		lblSituacion.setHorizontalAlignment(SwingConstants.CENTER);
		lblSituacion.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblSituacion.setBounds(41, 14, 331, 25);
		panel.add(lblSituacion);

		// Text field de cï¿½digo de mï¿½dico
		txtCodMed = new JTextField();
		txtCodMed.setBounds(183, 134, 189, 20);
		panel.add(txtCodMed);
		txtCodMed.setColumns(10);

		// Label de cï¿½digo de mï¿½dico
		JLabel lblCodigoMedico = new JLabel("C\u00F3digo m\u00E9dico");
		lblCodigoMedico.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblCodigoMedico.setBounds(36, 124, 137, 34);
		panel.add(lblCodigoMedico);

		// Boton para guardar en la base de datos la situaciï¿½n del paciente
		JButton btnConfirmar = new JButton("<html><center>Confirmar</center></html>");
		btnConfirmar.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnConfirmar.setFocusPainted(false);
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				registrarSituacion();
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

		// Text field de diagnï¿½stico
		txtDiagnostico = new JTextField();
		txtDiagnostico.setColumns(10);
		txtDiagnostico.setBounds(183, 190, 189, 20);
		panel.add(txtDiagnostico);

		// Label de diagnï¿½stico
		JLabel lblDiag = new JLabel("Diagn\u00F3stico");
		lblDiag.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblDiag.setBounds(36, 182, 137, 30);
		panel.add(lblDiag);

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
							"<html>En primer lugar, se debe ingresar el código del paciente.<br>Luego, el código del paciente siendo atendido.<br>Por último, el diagónstico obtenido.</html>",
							100, 100), "Información", JOptionPane.INFORMATION_MESSAGE);

				}
			});
		} catch (IOException e1) {
			e1.printStackTrace();
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

	public void registrarSituacion() {
		// Strings de los textfields
		String strCodPac = txtCodPaciente.getText().trim();
		String strCodMed = txtCodMed.getText().trim();
		String strSituacion = txtDiagnostico.getText().trim();

		// Validacion de parse-int
		if (esCodigoValido(strCodPac) && esCodigoValido(strCodMed)) {
			// Parseamos el codigo del paciente
			int codPac = Integer.parseInt(strCodPac);
			// Parseamos el cï¿½digo del medico
			int codMed = Integer.parseInt(strCodMed);

			// Busca el paciente y al medico, si existen devuelve true
			if (existePaciente(codPac) && existeMedico(codMed)) {
				// Chequeamos que la situacion no estï¿½ vacia
				if (strSituacion.length() > 0) {
					try {
						// Llamamos a la BD para obtener el ID de la ultima situaciï¿½n registrada
						// (por favor esto es algo teorico en la vida real esto seria un desastre
						// debido a la concurrencia, habria conflictos cada dos segundos)
						int id = dao.obtenerUltimoIDSituacion() + 1;
						Situacion situ = new Situacion(String.valueOf(id), String.valueOf(codPac),
								String.valueOf(codMed), strSituacion);
						// Agregamos la situacion a la base de datos
						dao.insertarSituacion(situ);

					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Ocurrió un error con la BD.", "Error",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					JOptionPane.showMessageDialog(null, "Situación registrada con éxito en la base de datos.",
							"Situación registrada", JOptionPane.INFORMATION_MESSAGE);
					txtCodPaciente.setText("");
					txtCodMed.setText("");
					txtDiagnostico.setText("");
				} else {
					JOptionPane.showMessageDialog(null, "La situación se encuentra vacía.", "Error",
							JOptionPane.INFORMATION_MESSAGE);
				}

			} else {
				JOptionPane.showMessageDialog(null, "El paciente o el médico no existen.", "Error",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "¡El código ingresado no es válido!", "Error",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

}
