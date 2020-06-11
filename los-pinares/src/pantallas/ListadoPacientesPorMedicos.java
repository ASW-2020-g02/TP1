package pantallas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import entidades.Paciente;

public class ListadoPacientesPorMedicos extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListadoPacientesPorMedicos frame = new ListadoPacientesPorMedicos();
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
	public ListadoPacientesPorMedicos() {
		String path = "C:\\";
		// Nombre de los archivos
		String datosPac = "datopac.txt";
		String datosMed = "datomed.txt";

		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 440, 570);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(153, 204, 204));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblListadoDePacientes = new JLabel("Listado de Pacientes por M\u00E9dico");
		lblListadoDePacientes.setHorizontalAlignment(SwingConstants.CENTER);
		lblListadoDePacientes.setFont(new Font("Arial", Font.BOLD, 18));
		lblListadoDePacientes.setBounds(37, 37, 347, 22);
		contentPane.add(lblListadoDePacientes);

		JLabel lblMdico = new JLabel("M\u00E9dico:");
		lblMdico.setBounds(63, 88, 98, 14);
		contentPane.add(lblMdico);

		JLabel lblPacientes = new JLabel("Pacientes:");
		lblPacientes.setBounds(63, 152, 98, 14);
		contentPane.add(lblPacientes);

		JTextArea taPacientes = new JTextArea();
		taPacientes.setBounds(63, 177, 300, 304);
		contentPane.add(taPacientes);

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCerrar.setBounds(327, 503, 87, 22);
		contentPane.add(btnCerrar);

		JComboBox cbMedicos = new JComboBox();
		cbMedicos.setBounds(59, 113, 227, 22);
		contentPane.add(cbMedicos);

		// carga de medicos

		ArrayList<Paciente> alpacientes = leerArchivoPacientes(path + datosMed);
		for (Paciente p : alpacientes) {
			String item = p.getCodigo() + " - " + p.getNombre();
			cbMedicos.addItem(item);
		}

		ItemListener changeClick = new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (cbMedicos.getSelectedItem().equals(e.getItem())) {
					System.out.println(e.getItem().toString());
					// String[] datosMedico = e.getItem().toString().split("-");

				}
			}
		};

		cbMedicos.addItemListener(changeClick);
	}

	public static ArrayList<Paciente> leerArchivoPacientes(String archivo) {
		try {
			BufferedReader entrada = new BufferedReader(new FileReader(archivo));
			String s = "";
			ArrayList<Paciente> ret = new ArrayList<Paciente>();
			while ((s = entrada.readLine()) != null) {
				String[] cadena = s.split(",");
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
}
