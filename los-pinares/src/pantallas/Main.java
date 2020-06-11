package pantallas;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Main extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
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
	public Main() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				salir();
			}
		});
		setTitle("Principal");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 458, 590);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnIngrModDatos = new JButton(
				"<html><div style=\"text-align: center;\">Ingreso/Modificaci\u00F3n de datos</html>");
		btnIngrModDatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainDatos mainDatos = new MainDatos();
				mainDatos.setVisible(true);
				dispose();
			}
		});
		btnIngrModDatos.setBounds(111, 82, 215, 72);
		contentPane.add(btnIngrModDatos);

		JButton btndiagnosticos = new JButton("<html><div style=\"text-align: center;\">Diagn\u00F3sticos</html>");
		btndiagnosticos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainDiagnostico mainDiagnostico = new MainDiagnostico();
				mainDiagnostico.setVisible(true);
				dispose();
			}
		});
		btndiagnosticos.setBounds(111, 167, 215, 72);
		contentPane.add(btndiagnosticos);

		JButton btninformes = new JButton("<html><div style=\"text-align: center;\">Informes</html>");
		btninformes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainInformes mainInforme = new MainInformes();
				mainInforme.setVisible(true);
				dispose();
			}
		});
		btninformes.setBounds(111, 252, 215, 72);
		contentPane.add(btninformes);

		JButton btnlistadoDePacientes = new JButton(
				"<html><div style=\"text-align: center;\">Listado de pacientes y m\u00E9dicos</html>");
		btnlistadoDePacientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainListados main = new MainListados();
				main.setVisible(true);
				dispose();
			}
		});
		btnlistadoDePacientes.setBounds(111, 337, 215, 72);
		contentPane.add(btnlistadoDePacientes);

		JButton btnsalir = new JButton("<html><div style=\"text-align: center;\">Salir</html>");
		btnsalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salir();
			}
		});
		btnsalir.setBounds(111, 436, 215, 72);
		contentPane.add(btnsalir);

		JLabel lblCentroMdicoLos = new JLabel("Centro m\u00E9dico Los Pinares");
		lblCentroMdicoLos.setFont(new Font("Meiryo", Font.PLAIN, 24));
		lblCentroMdicoLos.setHorizontalAlignment(SwingConstants.CENTER);
		lblCentroMdicoLos.setBounds(12, 13, 416, 42);
		contentPane.add(lblCentroMdicoLos);

		setLocationRelativeTo(null);

	}

	private void salir() {
		int dialogResult = JOptionPane.showConfirmDialog(null, "Esta seguro que desea salir del programa?", "Warning",
				JOptionPane.YES_NO_OPTION);
		if (dialogResult == 1)
			return;
		else
			System.exit(0);
	}

}
