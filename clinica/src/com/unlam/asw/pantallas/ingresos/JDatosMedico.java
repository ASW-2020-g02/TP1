package com.unlam.asw.pantallas.ingresos;

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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.unlam.asw.DB.DAO;
import com.unlam.asw.entities.Medico;
import com.unlam.asw.pantallas.general.JIngresos;

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
		// Configuraci�n inicial de la ventana
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Modifico el label del yes button
		UIManager.put("OptionPane.yesButtonText", "Si");
		// Agrego una ventana de dialogo al intentar cerrar el programa
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int confirmed = JOptionPane.showConfirmDialog(null, "Est� seguro que desea salir?", "Atenci�n",
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
		setTitle("Registro de m�dico");

		// Panel que contendr� los distintos elementos
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		// Label de c�digo de m�dico
		JLabel lblCodMedico = new JLabel("C\u00F3digo");
		lblCodMedico.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblCodMedico.setBounds(36, 77, 124, 30);
		panel.add(lblCodMedico);

		// Text field de c�digo de m�dico
		txtCodMedico = new JTextField();
		txtCodMedico.setBounds(183, 85, 189, 20);
		panel.add(txtCodMedico);
		txtCodMedico.setColumns(10);

		// Label de registro de m�dico, funciona como titular de la ventana
		JLabel lblRegistro = new JLabel("Registro de m\u00E9dico");
		lblRegistro.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistro.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblRegistro.setBounds(41, 14, 331, 25);
		panel.add(lblRegistro);

		// Text field de nombre del m�dico
		txtNombre = new JTextField();
		txtNombre.setBounds(183, 136, 189, 20);
		panel.add(txtNombre);
		txtNombre.setColumns(10);

		// Label del nombre del m�dico
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNombre.setBounds(36, 128, 124, 30);
		panel.add(lblNombre);

		// Boton para confirmar la creaci�n de un registro de m�dico
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

		// Label de especializaci�n del m�dico
		JLabel lblEspecializacion = new JLabel("Especializaci\u00F3n");
		lblEspecializacion.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblEspecializacion.setBounds(36, 182, 137, 30);
		panel.add(lblEspecializacion);

		// Text field de especializaci�n del m�dico
		txtEspecializacion = new JTextField();
		txtEspecializacion.setColumns(10);
		txtEspecializacion.setBounds(183, 190, 189, 20);
		panel.add(txtEspecializacion);

		// Obtengo la instancia del DAO
		dao = DAO.obtenerInstancia();
		// Centro la ventana en el monitor
		setLocationRelativeTo(null);
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
							// Creamos un objeto del tipo m�dico con los datos de los textfields
							Medico med = new Medico(strCodMed, strNombre, strEspe);
							// Hacemos una llamada para insertar al m�dico en la DB
							dao.insertarMedico(med);
						} catch (Exception e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(null, "Ocurri� un error con la BD.", "Error",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}

						JOptionPane.showMessageDialog(null, "Medico registrado con �xito en la base de datos.",
								"M�dico registrado", JOptionPane.INFORMATION_MESSAGE);
						txtCodMedico.setText("");
						txtNombre.setText("");
						txtEspecializacion.setText("");
					} else {
						JOptionPane.showMessageDialog(null,
								"La especializaci�n se encuentra vac�a o es muy grande.", "Error",
								JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "El nombre se encuentra vac�a o es muy grande.", "Error",
							JOptionPane.INFORMATION_MESSAGE);
				}

			} else {
				JOptionPane.showMessageDialog(null, "El medico ya existe.", "Error", JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "�El c�digo ingresado no es v�lido!", "Error",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public boolean esCodigoValido(String codigo) {
		try {
			// Si es posible realizar el parse Int, significa que es un String n�merico
			// v�lido
			Integer.parseInt(codigo);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean existeMedico(int cod) {
		Medico medico = null;
		try {
			// Busco en la base de datos el m�dico por su codigo
			medico = dao.buscarMedicoPorCodigo(cod);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// En caso de que no devuelva nada, la variable inicial seguira en null, por lo
		// que no existe el m�dico para dicho codigo
		if (medico == null) {
			return false;
		} else {
			return true;
		}
	}

}
