package com.unlam.asw.pantallas.ingresos;

import java.awt.BorderLayout;
import java.awt.EventQueue;
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
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.unlam.asw.DB.DAO;
import com.unlam.asw.entities.Paciente;
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
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
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
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		UIManager.put("OptionPane.yesButtonText", "Si");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int confirmed = JOptionPane.showConfirmDialog(null, "Está seguro que desea salir?", "Atención",
						JOptionPane.YES_NO_OPTION);
				if (confirmed == JOptionPane.YES_OPTION) {
					DAO.obtenerInstancia().cerrar();
					setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				} else {
					setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				}
			}
		});
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblCodPaciente = new JLabel("C\u00F3digo");
		lblCodPaciente.setBounds(95, 50, 65, 14);
		panel.add(lblCodPaciente);

		txtCodPaciente = new JTextField();
		txtCodPaciente.setBounds(166, 47, 162, 20);
		panel.add(txtCodPaciente);
		txtCodPaciente.setColumns(10);

		JLabel lblAltaDePaciente = new JLabel("Alta de Paciente");
		lblAltaDePaciente.setBounds(173, 14, 151, 14);
		panel.add(lblAltaDePaciente);

		txtNombrePaciente = new JTextField();
		txtNombrePaciente.setBounds(166, 97, 162, 20);
		panel.add(txtNombrePaciente);
		txtNombrePaciente.setColumns(10);

		JLabel lblNombreDelPaciente = new JLabel("Nombre");
		lblNombreDelPaciente.setBounds(95, 99, 64, 14);
		panel.add(lblNombreDelPaciente);

		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				generarAltaPaciente();
			}
		});
		btnConfirmar.setBounds(82, 200, 118, 23);
		panel.add(btnConfirmar);

		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JIngresos jp = new JIngresos();
				jp.setVisible(true);
				dispose();
			}
		});
		btnSalir.setBounds(248, 200, 118, 23);
		panel.add(btnSalir);

		dao = DAO.obtenerInstancia();
		setLocationRelativeTo(null);
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
					try {
						registrarPaciente(new Paciente(strCod, strNombre));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Paciente registrado con ï¿½xito en la base de datos.",
							"Paciente registrado", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null,
							"El nombre ingresado excede el lï¿½mite de 50 caracteres, o estï¿½ vacï¿½o.",
							"Paciente registrado", JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "ï¿½El paciente ya existe!", "Error",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "ï¿½El cï¿½digo ingresado no es vï¿½lido!", "Error",
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

	public void registrarPaciente(Paciente pac) {
		try {
			dao.insertarPaciente(pac);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
