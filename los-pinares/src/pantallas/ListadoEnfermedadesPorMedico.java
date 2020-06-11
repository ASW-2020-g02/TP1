package pantallas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Window.Type;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class ListadoEnfermedadesPorMedico extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListadoEnfermedadesPorMedico frame = new ListadoEnfermedadesPorMedico();
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
	public ListadoEnfermedadesPorMedico() {
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 440, 570);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(153, 204, 204));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblListadoDeEnfermedades = new JLabel("Enfermedades tratadas por m\u00E9dico");
		lblListadoDeEnfermedades.setHorizontalAlignment(SwingConstants.CENTER);
		lblListadoDeEnfermedades.setFont(new Font("Arial", Font.BOLD, 18));
		lblListadoDeEnfermedades.setBounds(10, 23, 404, 22);
		contentPane.add(lblListadoDeEnfermedades);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCerrar.setBounds(324, 503, 90, 22);
		contentPane.add(btnCerrar);
		
		JLabel label = new JLabel("M\u00E9dico:");
		label.setBounds(74, 59, 98, 14);
		contentPane.add(label);
		
		JComboBox cbMedico = new JComboBox();
		cbMedico.setBounds(74, 85, 227, 22);
		contentPane.add(cbMedico);
		
		JLabel lblEnfermedades = new JLabel("Enfermedades:");
		lblEnfermedades.setBounds(74, 118, 98, 14);
		contentPane.add(lblEnfermedades);
		
		JTextArea taEnfermedad = new JTextArea();
		taEnfermedad.setBounds(73, 150, 288, 336);
		contentPane.add(taEnfermedad);
	}
}
