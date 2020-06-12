package com.unlam.asw;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.unlam.asw.DB.DAO;
import com.unlam.asw.entities.Medico;
import com.unlam.asw.entities.Paciente;

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
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblCodMedico = new JLabel("C\u00F3digo");
		lblCodMedico.setBounds(95, 50, 65, 14);
		panel.add(lblCodMedico);

		txtCodMedico = new JTextField();
		txtCodMedico.setBounds(166, 47, 162, 20);
		panel.add(txtCodMedico);
		txtCodMedico.setColumns(10);

		JLabel lblRegistro = new JLabel("Registro de m\u00E9dico");
		lblRegistro.setBounds(173, 14, 151, 14);
		panel.add(lblRegistro);

		txtNombre = new JTextField();
		txtNombre.setBounds(166, 75, 162, 20);
		panel.add(txtNombre);
		txtNombre.setColumns(10);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(95, 77, 64, 14);
		panel.add(lblNombre);

		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				registrarMedico();
			}
		});
		btnConfirmar.setBounds(82, 200, 118, 23);
		panel.add(btnConfirmar);

		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JIngresoPacientes jp = new JIngresoPacientes();
				jp.setVisible(true);
				dispose();
			}
		});
		btnSalir.setBounds(248, 200, 118, 23);
		panel.add(btnSalir);

		JLabel lblEspecializacion = new JLabel("Especializaci\u00F3n");
		lblEspecializacion.setBounds(69, 110, 90, 14);
		panel.add(lblEspecializacion);

		txtEspecializacion = new JTextField();
		txtEspecializacion.setColumns(10);
		txtEspecializacion.setBounds(166, 108, 162, 20);
		panel.add(txtEspecializacion);

		dao = new DAO();
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
							// Creamos un objeto del tipo médico con los datos de los textfields
							Medico med = new Medico(strCodMed, strNombre, strEspe);
							// Hacemos una llamada para insertar al médico en la DB
							dao.insertarMedico(med);
						} catch (Exception e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(null, "Ocurrió un error con la BD.", "Error",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}

						JOptionPane.showMessageDialog(null, "Medico registrado con éxito en la base de datos.",
								"Paciente registrado", JOptionPane.INFORMATION_MESSAGE);
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
			Integer.parseInt(codigo);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean existePaciente(int cod) {
		Paciente paciente = null;
		try {
			paciente = dao.buscarPacientePorCodigo(cod);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return true;
		}

		if (paciente == null) {
			return false;
		} else {
			return true;
		}
	}

	public boolean existeMedico(int cod) {
		Medico medico = null;
		try {
			medico = dao.buscarMedicoPorCodigo(cod);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return true;
		}

		if (medico == null) {
			return false;
		} else {
			return true;
		}
	}

}
