package pantallas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import objetos.Paciente;
import otros.Constantes;
import otros.Encriptacion;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ListadoPacientes extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListadoPacientes frame = new ListadoPacientes();
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
	public ListadoPacientes() {
		setTitle("Pacientes");
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
		setBounds(100, 100, 338, 493);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(153, 204, 102));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblListadoDePacientes = new JLabel("Listado de Pacientes");
		lblListadoDePacientes.setHorizontalAlignment(SwingConstants.CENTER);
		lblListadoDePacientes.setFont(new Font("Arial", Font.BOLD, 18));
		lblListadoDePacientes.setBounds(37, 30, 257, 22);
		contentPane.add(lblListadoDePacientes);
		
		JButton btnCerrar = new JButton("Volver");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainListados mainlistado = new MainListados();
				mainlistado.setVisible(true);
				dispose();
			}
		});
		btnCerrar.setBounds(116, 409, 89, 23);
		contentPane.add(btnCerrar);
		
		JList listPacientes = new JList();
		listPacientes.setBounds(12, 65, 308, 320);
		contentPane.add(listPacientes);
		
		ArrayList<Paciente> pacientes = leerArchivoPacientes(Constantes.archivoPacientes);
		DefaultListModel modelo = new DefaultListModel();
		for (Paciente p : pacientes) {
			String item = " [" + p.getCodigo() + "] - " + p.getNombre();
			modelo.addElement(item);
		}
		
		listPacientes.setModel(modelo);
		setLocationRelativeTo(null);
		
	}
	
	private ArrayList<Paciente> leerArchivoPacientes(String archivo)
	{
		try
		{
			BufferedReader entrada =new BufferedReader(new FileReader(archivo));
			String s = "";
			ArrayList<Paciente> ret = new ArrayList<Paciente>();
			while((s = entrada.readLine())!= null)
			{
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
				ret.add(new Paciente(cod,nom));
			}
			entrada.close();
			return ret;
		}
		catch (java.io.IOException e) 
		{ 
			e.printStackTrace();
			return null;
		}
	}

}
