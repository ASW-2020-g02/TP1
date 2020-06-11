package pantallas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainDatos extends JFrame {

	private JPanel contentPane;
	AltaPaciente altaPaciente = new AltaPaciente();
	ModificacionPaciente modPaciente = new ModificacionPaciente();			
	AltaMedico altaMedico = new AltaMedico();
	ModificacionMedico modMedico = new ModificacionMedico();	


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainDatos frame = new MainDatos();
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
	public MainDatos() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				if(altaPaciente.isVisible())
					return;
				if(altaMedico.isVisible())
					return;
				if(modMedico.isVisible())
					return;
				if(modPaciente.isVisible())
					return;
					
				Main main = new Main();
				main.setVisible(true);
			}
		});
		setTitle("Ingreso/Modificaci\u00F3n de datos");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 412, 347);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton button = new JButton("Alta paciente");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				altaPaciente.setVisible(true);
				dispose();
			}
		});
		button.setBounds(101, 13, 180, 44);
		contentPane.add(button);
		
		JButton button_1 = new JButton("Modificaci\u00F3n paciente");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modPaciente.setVisible(true);
				dispose();
			}
		});
		button_1.setBounds(101, 70, 180, 44);
		contentPane.add(button_1);
		
		JButton button_2 = new JButton("Alta m\u00E9dico");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				altaMedico.setVisible(true);
				dispose();				
			}
		});
		button_2.setBounds(101, 127, 180, 44);
		contentPane.add(button_2);
		
		JButton button_3 = new JButton("Modificaci\u00F3n m\u00E9dico");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modMedico.setVisible(true);
				dispose();
			}
		});
		button_3.setBounds(101, 184, 180, 44);
		contentPane.add(button_3);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnVolver.setBounds(101, 241, 180, 44);
		contentPane.add(btnVolver);
		
		setLocationRelativeTo(null);
	}

}
