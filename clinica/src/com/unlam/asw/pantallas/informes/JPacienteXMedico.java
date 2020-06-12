package com.unlam.asw.pantallas.informes;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import com.unlam.asw.DB.DAO;
import com.unlam.asw.entities.Medico;
import com.unlam.asw.entities.Paciente;
import com.unlam.asw.pantallas.general.JInformes;
import com.unlam.asw.utils.Utils;

public class JPacienteXMedico extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8505758328180540372L;
	private JPanel contentPane;
	private JList listaPacientes;
	private DAO dao;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JPacienteXMedico frame = new JPacienteXMedico();
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
	public JPacienteXMedico() {
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
		setBounds(100, 100, 429, 404);
		setLocationRelativeTo(null);
		setTitle("Listado de Pacientes por M\u00E9dico");

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblListadoDePacientes = new JLabel("Listado de Pacientes por M\u00E9dico");
		lblListadoDePacientes.setHorizontalAlignment(SwingConstants.CENTER);
		lblListadoDePacientes.setFont(new Font("Arial", Font.BOLD, 18));
		lblListadoDePacientes.setBounds(23, 11, 374, 22);
		contentPane.add(lblListadoDePacientes);

		JLabel lblMdico = new JLabel("M\u00E9dico");
		lblMdico.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblMdico.setBounds(23, 44, 74, 22);
		contentPane.add(lblMdico);

		JLabel lblPacientes = new JLabel("Pacientes");
		lblPacientes.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblPacientes.setBounds(23, 77, 98, 14);
		contentPane.add(lblPacientes);

		this.listaPacientes = new JList<Paciente>();
		listaPacientes.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		listaPacientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaPacientes.setBounds(10, 50, 445, 284);
		JScrollPane listScrollerPacientes = new JScrollPane();
		listScrollerPacientes.setBounds(33, 102, 364, 214);
		listScrollerPacientes.setViewportView(listaPacientes);
		listaPacientes.setLayoutOrientation(JList.VERTICAL);
		contentPane.add(listScrollerPacientes);
		JButton btnCerrar = new JButton("Volver");
		btnCerrar.setFocusPainted(false);
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JInformes informes = new JInformes();
				informes.setVisible(true);
				dispose();
			}
		});
		btnCerrar.setBounds(324, 332, 74, 22);
		contentPane.add(btnCerrar);

		// Creación del combo box
		JComboBox<Medico> cbMedicos = new JComboBox<Medico>();
		cbMedicos.setBounds(98, 44, 300, 22);
		contentPane.add(cbMedicos);

		// En primer lugar, creo el key listener con el cual detectare el cambio de
		// médico en el combobox
		ItemListener changeClick = new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (cbMedicos.getSelectedItem().equals(e.getItem())) {
					seleccionarMedico((Medico) e.getItem());
				}
			}
		};

		// Una vez creado este, se lo añado a cada item
		cbMedicos.addItemListener(changeClick);

		// Una vez creado, debo obtener la data directo desde la BD
		this.dao = DAO.obtenerInstancia();
		ArrayList<Medico> medicos = new ArrayList<Medico>();
		// Es posible que ocurra un error al conectarse con la BD, por lo que es
		// necesario encerrarlo en un bloque try catch
		try {
			medicos = dao.obtenerMedicos();

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Agrego la opci�n dummy, la cual ser� validada por el sistema
		// Dado que es una opcion simplemente de muestra, la misma no deber� arrojar una
		// excepcion
		// pero debido a como esta programada la clase, se debera atrapar dicha
		// excepcion
		try {
			cbMedicos.addItem(new Medico("-1", "Seleccione un médico", "!"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// Agrego al combo box los resultados obtenidos
		for (Medico medico : medicos) {
			cbMedicos.addItem(medico);
		}
	}

	@SuppressWarnings("unchecked")
	protected void seleccionarMedico(Medico medico) {
		ArrayList<Paciente> listaTempPacientes = new ArrayList<Paciente>();
		// Verifico que no se trata del caso default
		if (medico.getCodigo() != -1) {

			// Aca debo obtener todos los pacientes asociados al m�dico
			// Una vez obtenidos, se llena la lista
			try {
				listaTempPacientes = dao.obtenerPacientesXMedico(medico.getCodigo());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		String[] elementos = new String[listaTempPacientes.size()];

		for (int i = 0; i < listaTempPacientes.size(); i++) {
			elementos[i] = listaTempPacientes.get(i).getNombre();
		}

		Utils.actualizarLista(listaPacientes, elementos);
	}
}
