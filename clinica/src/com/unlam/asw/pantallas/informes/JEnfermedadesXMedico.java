package com.unlam.asw.pantallas.informes;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import com.unlam.asw.DB.DAO;
import com.unlam.asw.entities.Medico;
import com.unlam.asw.entities.Paciente;
import com.unlam.asw.pantallas.access.JLogin;
import com.unlam.asw.pantallas.general.JInformes;
import com.unlam.asw.utils.Utils;

public class JEnfermedadesXMedico extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3190942335460739846L;
	/**
	 * 
	 */
	private JPanel contentPane;
	private JList listaEnfermedades;
	private DAO dao;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// Creo un nuevo thread para la ventanas
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Creo una instancia de JEnfermedadesXMedico y la hago visible
					JEnfermedadesXMedico frame = new JEnfermedadesXMedico();
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
	public JEnfermedadesXMedico() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		UIManager.put("OptionPane.yesButtonText", "Si");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int confirmed = JOptionPane.showConfirmDialog(null, "Está seguro que desea salir?", "Atención",
						JOptionPane.YES_NO_OPTION);
				if (confirmed == JOptionPane.YES_OPTION) {
					DAO.obtenerInstancia().cerrar();
					setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				} else {
					setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				}
			}
		});
		setBounds(100, 100, 429, 404);
		setLocationRelativeTo(null);
		setTitle("Listado de Enfermedades por M\u00E9dico");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblListadoDePacientes = new JLabel("Listado de Enfermedades por M\u00E9dico");
		lblListadoDePacientes.setHorizontalAlignment(SwingConstants.CENTER);
		lblListadoDePacientes.setFont(new Font("Arial", Font.BOLD, 18));
		lblListadoDePacientes.setBounds(23, 11, 374, 22);
		contentPane.add(lblListadoDePacientes);

		JLabel lblMdico = new JLabel("M\u00E9dico");
		lblMdico.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblMdico.setBounds(23, 44, 74, 22);
		contentPane.add(lblMdico);

		JLabel lblPacientes = new JLabel("Enfermedades");
		lblPacientes.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblPacientes.setBounds(23, 77, 133, 14);
		contentPane.add(lblPacientes);

		this.listaEnfermedades = new JList<Paciente>();
		listaEnfermedades.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		listaEnfermedades.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaEnfermedades.setBounds(10, 50, 445, 284);
		JScrollPane listScrollerPacientes = new JScrollPane();
		listScrollerPacientes.setBounds(33, 102, 364, 214);
		listScrollerPacientes.setViewportView(listaEnfermedades);
		listaEnfermedades.setLayoutOrientation(JList.VERTICAL);
		contentPane.add(listScrollerPacientes);
		JButton btnCerrar = new JButton("Volver");
		btnCerrar.setFocusPainted(false);
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JInformes informes = new JInformes();
				informes.setVisible(true);
				dispose();
			}
		});
		btnCerrar.setBounds(324, 332, 74, 22);
		contentPane.add(btnCerrar);

		// Creación del combo box
		JComboBox<Medico> cbMedicos = new JComboBox<Medico>();
		cbMedicos.setBounds(98, 44, 300, 22);
		contentPane.add(cbMedicos);

		// En primer lugar, creo el key listener con el cual detectare el cambio de
		// médico en el combobox
		ItemListener changeClick = new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (cbMedicos.getSelectedItem().equals(e.getItem())) {
					seleccionarMedico((Medico) e.getItem());
				}
			}
		};

		// Una vez creado este, se lo aÃ±ado a cada item
		cbMedicos.addItemListener(changeClick);

		// Una vez creado, debo obtener la data directo desde la BD
		this.dao = DAO.obtenerInstancia();
		ArrayList<Medico> medicos = new ArrayList<Medico>();
		// Es posible que ocurra un error al conectarse con la BD, por lo que es
		// necesario encerrarlo en un bloque try catch
		try {
			medicos = dao.obtenerMedicos();

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Agrego la opción dummy, la cual será validada por el sistema
		// Dado que es una opcion simplemente de muestra, la misma no deberá arrojar
		// una
		// excepcion
		// pero debido a como esta programada la clase, se debera atrapar dicha
		// excepcion
		try {
			cbMedicos.addItem(new Medico("-1", "Seleccione un médico", "!"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// Agrego al combo box los resultados obtenidos
		for (Medico medico : medicos) {
			cbMedicos.addItem(medico);
		}

		try {
			// Creo el boton
			JButton botonAyuda = new JButton();

			// Seteo los bounds
			botonAyuda.setBounds(new Rectangle(381, 5, 32, 32));

			// Obtengo el url de la imagen
			URL url = JLogin.class.getResource("/informacion.png");

			// Creo el buffer para la imagen
			BufferedImage img;
			img = ImageIO.read(url);

			// Creo una variable del tipo ImageIcon
			ImageIcon image = new ImageIcon(img);
			// Seteo la imagen como icono
			botonAyuda.setIcon(image);

			// Pongo el cuadrado del mismo color de fondo
			botonAyuda.setBackground(new Color(245, 245, 220));
			botonAyuda.setBorderPainted(false);

			// Lo agrego al panel
			contentPane.add(botonAyuda);

			// Agrego el onClick para mostrar el diálogo
			botonAyuda.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, String.format(
							"<html>Seleccione un médico de la lista desplegable para visualizar las enfermedades atendidas por el mismo</html>",
							100, 100), "Información", JOptionPane.INFORMATION_MESSAGE);

				}
			});
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	protected void seleccionarMedico(Medico medico) {
		ArrayList<String> listaTempEnfermedades = new ArrayList<String>();
		// Verifico que no se trata del caso default
		if (medico.getCodigo() != -1) {

			// Aca debo obtener todas las enfermedades asociadas al médico
			// Una vez obtenidos, se llena la lista
			try {
				listaTempEnfermedades = dao.obtenerEnfermedadesXMedico(medico.getCodigo());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		String[] elementos = new String[listaTempEnfermedades.size()];

		for (int i = 0; i < listaTempEnfermedades.size(); i++) {
			elementos[i] = listaTempEnfermedades.get(i);
		}

		Utils.actualizarLista(listaEnfermedades, elementos);
	}
}
