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
import com.unlam.asw.entities.Medico;
import com.unlam.asw.entities.Paciente;
import com.unlam.asw.entities.Situacion;
import com.unlam.asw.pantallas.general.JIngresos;

public class JSituacionPaciente extends JFrame {

	private JPanel contentPane;
	private DAO dao;
	private JFrame frame;
	private JTextField txtCodPaciente;
	private JTextField txtCodMed;
	private JTextField txtNumDiagnostico;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
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
		setResizable(false);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblCodPaciente = new JLabel("C\u00F3digo paciente");
		lblCodPaciente.setBounds(42, 50, 118, 14);
		panel.add(lblCodPaciente);

		txtCodPaciente = new JTextField();
		txtCodPaciente.setBounds(166, 47, 162, 20);
		panel.add(txtCodPaciente);
		txtCodPaciente.setColumns(10);

		JLabel lblSituacion = new JLabel("Situaci\u00F3n del Paciente");
		lblSituacion.setBounds(173, 14, 151, 14);
		panel.add(lblSituacion);

		txtCodMed = new JTextField();
		txtCodMed.setBounds(166, 78, 162, 20);
		panel.add(txtCodMed);
		txtCodMed.setColumns(10);

		JLabel lblCodigoMedico = new JLabel("C\u00F3digo m\u00E9dico");
		lblCodigoMedico.setBounds(42, 81, 117, 14);
		panel.add(lblCodigoMedico);

		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				registrarSituacion();
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

		txtNumDiagnostico = new JTextField();
		txtNumDiagnostico.setColumns(10);
		txtNumDiagnostico.setBounds(166, 109, 162, 20);
		panel.add(txtNumDiagnostico);

		JLabel lblCodDiag = new JLabel("N\u00FAm. Diagn\u00F3stico");
		lblCodDiag.setBounds(42, 112, 117, 14);
		panel.add(lblCodDiag);

		dao = DAO.obtenerInstancia();
		setLocationRelativeTo(null);
	}

	public void registrarSituacion() {
		// Strings de los textfields
		String strCodPac = txtCodPaciente.getText().trim();
		String strCodMed = txtCodMed.getText().trim();
		String strSituacion = txtNumDiagnostico.getText().trim();

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
						Situacion situ = new Situacion(id, codPac, codMed, strSituacion);
						// Agregamos la situacion a la base de datos
						dao.insertarSituacion(situ);

					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Ocurriï¿½ un error con la BD.", "Error",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					JOptionPane.showMessageDialog(null, "Situaciï¿½n registrada con ï¿½xito en la base de datos.",
							"Paciente registrado", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "La situaciï¿½n se encuentra vacï¿½a.", "Error",
							JOptionPane.INFORMATION_MESSAGE);
				}

			} else {
				JOptionPane.showMessageDialog(null, "El paciente o el mï¿½dico no existen.", "Error",
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
