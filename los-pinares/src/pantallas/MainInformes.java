package pantallas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import entidades.Medico;
import entidades.Paciente;
import entidades.SituacionPaciente;
import otros.Constantes;
import otros.Encriptacion;

public class MainInformes extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainInformes frame = new MainInformes();
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
	public MainInformes() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				Main main = new Main();
				main.setVisible(true);
			}
		});
		setTitle("Informes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 529, 421);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		ArrayList<Medico> medicos = leerArchivoMedicos();
		ArrayList<Paciente> pacientes = leerArchivoPacientes();
		ArrayList<SituacionPaciente> situPac = leerArchivoSituacionPacientes();

		JList jlistPacientes = new JList();
		jlistPacientes.setForeground(new Color(0, 102, 255));
		jlistPacientes.setBounds(22, 102, 149, 208);
		contentPane.add(jlistPacientes);

		JComboBox cboMedicos = new JComboBox();
		cboMedicos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cadmed[] = cboMedicos.getSelectedItem().toString().split(" :: ");

				// buscar pacientes por medico
				ArrayList<Paciente> pacientesXmedico = buscarPacientesPorMedico(Integer.parseInt(cadmed[0]), situPac,
						pacientes);

				DefaultListModel modelo = new DefaultListModel();
				for (Paciente p : pacientesXmedico) {
					String item = p.getCodigo() + " - " + p.getNombre();
					modelo.addElement(item);
				}
				jlistPacientes.setModel(modelo);

				// buscar diagnostico paciente seleccionado.
			}
		});
		cboMedicos.setBounds(156, 26, 204, 32);
		contentPane.add(cboMedicos);

		for (Medico m : medicos) {
			cboMedicos.addItem(m.Imprimir());
		}

		JTextPane txtDiagnosticos = new JTextPane();
		txtDiagnosticos.setEditable(false);
		txtDiagnosticos.setBounds(183, 128, 293, 182);
		contentPane.add(txtDiagnosticos);

		jlistPacientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JList list = (JList) arg0.getSource();
				if (arg0.getClickCount() == 2) {

					String cadmed[] = cboMedicos.getSelectedItem().toString().split(" :: ");
					int codmed = Integer.parseInt(cadmed[0]);

					String cadpac[] = jlistPacientes.getSelectedValue().toString().split(" - ");
					int codpac = Integer.parseInt(cadpac[0]);

					String diagn = "";
					for (SituacionPaciente sp : situPac) {
						if (codmed == sp.getCodmedico() && codpac == sp.getCodpaciente())
							diagn += sp.getSituacion() + " || ";
					}
					txtDiagnosticos.setText(diagn);
				}
			}
		});

		JLabel lblNewLabel = new JLabel("Seleccionar m\u00E9dico:");
		lblNewLabel.setBounds(22, 26, 122, 32);
		contentPane.add(lblNewLabel);

		JLabel lblPacientesAtendidos = new JLabel(
				"Pacientes atendidos (haga doble-click sobre el paciente para ver los diagn\u00F3sticos):");
		lblPacientesAtendidos.setFont(new Font("Tahoma", Font.ITALIC, 13));
		lblPacientesAtendidos.setBounds(22, 71, 477, 32);
		contentPane.add(lblPacientesAtendidos);

		JLabel lblDiagnsticos = new JLabel("Diagn\u00F3sticos");
		lblDiagnsticos.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDiagnsticos.setBounds(182, 103, 128, 24);
		contentPane.add(lblDiagnsticos);

		JButton btnNewButton = new JButton("Volver");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main main = new Main();
				main.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBounds(379, 335, 97, 25);
		contentPane.add(btnNewButton);

		setLocationRelativeTo(null);
	}

	private ArrayList<Medico> leerArchivoMedicos() {
		try {
			BufferedReader entrada = new BufferedReader(new FileReader(Constantes.archivoMedicos));
			String s = "";
			ArrayList<Medico> ret = new ArrayList<Medico>();
			while ((s = entrada.readLine()) != null) {
				String cd = "";
				try {
					cd = Encriptacion.Desencriptar(s);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String[] cadena = cd.split(",");
				int cod = Integer.parseInt(cadena[0]);
				String nom = cadena[1];
				String esp = cadena[2];
				ret.add(new Medico(cod, nom, esp));
			}
			entrada.close();
			return ret;
		} catch (java.io.IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private ArrayList<Paciente> leerArchivoPacientes() {
		try {
			BufferedReader entrada = new BufferedReader(new FileReader(Constantes.archivoPacientes));
			String s = "";
			ArrayList<Paciente> ret = new ArrayList<Paciente>();
			while ((s = entrada.readLine()) != null) {
				String cd = "";
				try {
					cd = Encriptacion.Desencriptar(s);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String[] cadena = cd.split(",");
				int cod = Integer.parseInt(cadena[0]);
				String nom = cadena[1];
				ret.add(new Paciente(cod, nom));
			}
			entrada.close();
			return ret;
		} catch (java.io.IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private ArrayList<SituacionPaciente> leerArchivoSituacionPacientes() {
		try {
			BufferedReader entrada = new BufferedReader(new FileReader(Constantes.archivoSituacionPaciente));
			String s = "";
			ArrayList<SituacionPaciente> ret = new ArrayList<SituacionPaciente>();
			while ((s = entrada.readLine()) != null) {
				String cd = "";
				try {
					cd = Encriptacion.Desencriptar(s);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String[] cadena = cd.split(",");
				int codp = Integer.parseInt(cadena[0]);
				int codm = Integer.parseInt(cadena[1]);
				String diag = cadena[2];
				SituacionPaciente sp = new SituacionPaciente(codp, codm, diag);
				ret.add(sp);
			}
			entrada.close();
			return ret;
		} catch (java.io.IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private ArrayList<Paciente> buscarPacientesPorMedico(int codMed, ArrayList<SituacionPaciente> situPac,
			ArrayList<Paciente> pacientes) {
		ArrayList<Paciente> lret = new ArrayList<Paciente>();
		for (SituacionPaciente sp : situPac) {
			if (codMed == sp.getCodmedico()) {
				for (Paciente p : pacientes) {
					if (sp.getCodpaciente() == p.getCodigo() && !lret.contains(p))
						lret.add(p);
				}
			}
		}
		return lret;
	}

}
