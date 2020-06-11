package pantallas;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import objetos.Medico;
import objetos.Paciente;
import objetos.SituacionPaciente;
import otros.Constantes;
import otros.Encriptacion;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.DropMode;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainDiagnostico extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainDiagnostico frame = new MainDiagnostico();
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
	public MainDiagnostico() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				Main main = new Main();
				main.setVisible(true);
			}
		});
		setTitle("Diagn\u00F3stico");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 487, 383);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblDiagnstico = new JLabel("Diagn\u00F3stico:");
		lblDiagnstico.setHorizontalAlignment(SwingConstants.CENTER);
		lblDiagnstico.setBounds(12, 111, 90, 16);
		contentPane.add(lblDiagnstico);
		
		ArrayList<Medico> medicos = leerArchivoMedicos();
		
		JComboBox cboMedico = new JComboBox();
		cboMedico.setBackground(Color.WHITE);
		cboMedico.setBounds(97, 27, 272, 29);
		contentPane.add(cboMedico);
		
		for (Medico m : medicos) {
			cboMedico.addItem(m.Imprimir());
		}
		
		ArrayList<Paciente> pacientes = leerArchivoPacientes();
		
		JComboBox cboPaciente = new JComboBox();
		cboPaciente.setBackground(Color.WHITE);
		cboPaciente.setBounds(97, 69, 272, 29);
		contentPane.add(cboPaciente);
		
		for (Paciente paciente : pacientes) {
			cboPaciente.addItem(paciente.Imprimir());
			
		}
		
		
		JLabel lblMedico = new JLabel("M\u00E9dico:");
		lblMedico.setHorizontalAlignment(SwingConstants.CENTER);
		lblMedico.setBounds(12, 36, 90, 16);
		contentPane.add(lblMedico);
		
		JLabel lblPaciente = new JLabel("Paciente:");
		lblPaciente.setHorizontalAlignment(SwingConstants.CENTER);
		lblPaciente.setBounds(12, 75, 90, 16);
		contentPane.add(lblPaciente);
		
		JTextPane txtpnDiagnostico = new JTextPane();
		txtpnDiagnostico.setBounds(22, 140, 424, 143);
		contentPane.add(txtpnDiagnostico);

		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					if(txtpnDiagnostico.getText().length()==0)
						throw new Exception("Debe ingresar un diagnóstico.");
					
					String cadpac[]= cboPaciente.getSelectedItem().toString().split(" :: ");					
					String cadmed[]= cboMedico.getSelectedItem().toString().split(" :: ");
					
					SituacionPaciente sp = new SituacionPaciente(Integer.parseInt(cadpac[0]), Integer.parseInt(cadmed[0]), txtpnDiagnostico.getText());
					registrarSituacionPaciente(sp);
					JOptionPane.showMessageDialog(null, "¡Diagnóstico realizado correctamente!","Confirmación", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null, ex.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnAceptar.setBounds(229, 298, 97, 25);
		contentPane.add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCancelar.setBounds(354, 298, 97, 25);
		contentPane.add(btnCancelar);
		
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
	
	private ArrayList<Paciente> leerArchivoPacientes()
	{
		try
		{
			BufferedReader entrada =new BufferedReader(new FileReader(Constantes.archivoPacientes));
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
	
	private void registrarSituacionPaciente(SituacionPaciente sp)
	{
		try{
			FileWriter datopac = new FileWriter(Constantes.archivoSituacionPaciente,true);
			String registro = Encriptacion.Encriptar(sp.getCodpaciente() + "," + sp.getCodmedico() +"," + sp.getSituacion());
			//System.out.println(sp.Imprimir());
			datopac.write(registro+ "\n");
			datopac.close();
		}catch (IOException ex){
			ex.printStackTrace();
		}
	}
}
