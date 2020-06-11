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
import java.awt.FlowLayout;

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
		btnConfirmar.setBounds(170, 201, 118, 23);
		panel.add(btnConfirmar);

		dao = new DAO();
	}

	
	
	private void generarAltaPaciente() {
		String strCod = txtCodPaciente.getText().trim();
		String strNombre = txtNombrePaciente.getText().trim();
		int nombreLength = strNombre.length();
		
		//Validacion de parse-int
		if (esCodigoValido(strCod))
		{
			int cod = Integer.parseInt(strCod);			
			
			//Busca el paciente en una query, si existe devuelve true
			if (!existePaciente(cod)) {
				//Si el paciente no existe, chequeamos que se haya ingresado bien el nombre
				if (nombreLength <= 50 && nombreLength > 0) {					
					registrarPaciente(cod, strNombre);
					JOptionPane.showMessageDialog(null, "Paciente registrado con éxito en la base de datos.", "Paciente registrado", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "El nombre ingresado excede el límite de 50 caracteres, o está vacío.", "Paciente registrado", JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "¡El paciente ya existe!", "Error", JOptionPane.INFORMATION_MESSAGE);
			}			
		} else {
			JOptionPane.showMessageDialog(null, "¡El código ingresado no es válido!", "Error", JOptionPane.INFORMATION_MESSAGE);
		}				
	}
	
	public boolean esCodigoValido(String codigo) {		
		try {
			Integer.parseInt(codigo);
			return true;
		}	
		 catch (Exception e) {
			return false;
		}
	}
	
	public boolean existePaciente(int cod) {
		//TODO: conexion bd y query paciente
		return false;
	}
	
	public void registrarPaciente(int cod, String strNombre) {
		//TODO: conexion bd, insert paciente
	}
}
