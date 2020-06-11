package pantallas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainListados extends JFrame {

	private JPanel contentPane;
	ListadoPacientes listadoPacientes = new ListadoPacientes();
	ListadoMedicos listadoMedicos = new ListadoMedicos();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainListados frame = new MainListados();
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
	public MainListados() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				if(listadoPacientes.isVisible() || listadoMedicos.isVisible())
					return;
				Main main = new Main();
				main.setVisible(true);
			}
		});
		setTitle("Listados");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 304, 275);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton button = new JButton("Volver");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		button.setBounds(57, 175, 167, 34);
		contentPane.add(button);
		
		JButton button_1 = new JButton("Listado de m\u00E9dicos");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listadoMedicos.setVisible(true);
				dispose();
			}
		});
		button_1.setBounds(57, 94, 167, 34);
		contentPane.add(button_1);
		
		JButton button_2 = new JButton("Listado de pacientes");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				listadoPacientes.setVisible(true);
				dispose();				
			}
		});
		button_2.setBounds(57, 39, 167, 34);
		contentPane.add(button_2);
		setLocationRelativeTo(null);
	}
}
