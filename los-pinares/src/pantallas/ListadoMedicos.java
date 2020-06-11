package pantallas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import entidades.Medico;
import otros.Constantes;
import otros.Encriptacion;

public class ListadoMedicos extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListadoMedicos frame = new ListadoMedicos();
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
	public ListadoMedicos() {
		setTitle("M\u00E9dicos");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				MainListados mainlistado = new MainListados();
				mainlistado.setVisible(true);
			}
		});

		setType(Type.UTILITY);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 333, 446);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(153, 204, 102));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnCerrar = new JButton("Volver");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainListados mainlistado = new MainListados();
				mainlistado.setVisible(true);
				dispose();
			}
		});
		btnCerrar.setBounds(118, 375, 89, 23);
		contentPane.add(btnCerrar);

		JLabel lblListadoDeMdicos = new JLabel("Listado de M\u00E9dicos");
		lblListadoDeMdicos.setHorizontalAlignment(SwingConstants.CENTER);
		lblListadoDeMdicos.setFont(new Font("Arial", Font.BOLD, 18));
		lblListadoDeMdicos.setBounds(25, 23, 257, 22);
		contentPane.add(lblListadoDeMdicos);

		JList listMedicos = new JList();
		listMedicos.setBounds(12, 58, 303, 299);
		contentPane.add(listMedicos);

		ArrayList<Medico> medicos = leerArchivoMedicos(Constantes.archivoMedicos);
		DefaultListModel modelo = new DefaultListModel();
		for (Medico p : medicos) {
			String item = " [" + p.getCodigo() + "] - " + p.getNombre() + " (" + p.getEspecializacion() + ")";
			modelo.addElement(item);
		}

		listMedicos.setModel(modelo);
		setLocationRelativeTo(null);

	}

	private ArrayList<Medico> leerArchivoMedicos(String archivo) {
		try {
			BufferedReader entrada = new BufferedReader(new FileReader(archivo));
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

}
