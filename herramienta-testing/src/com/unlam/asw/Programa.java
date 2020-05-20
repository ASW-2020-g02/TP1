package com.unlam.asw;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

public class Programa extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7097174973249116900L;

	private JPanel contentPane;

	private JList listaArchivos;

	private JList listaMetodos;

	private JList listaClases;

	private JPanel panelAnalisis;

	private JLabel lblResultadoFanIn;

	private JLabel lblResultadoFanOut;

	private JLabel lblResultadoLongitud;

	private JLabel lblResultadoVolumen;

	private JLabel lblResultadoEsfuerzo;

	private JLabel lblResultadoPjeComentarios;

	private JLabel lblResultadoComplejidadCiclomatica;

	private JLabel lblResultadoLineasCodigo;

	private JLabel lblResultadoLineasComentadas;

	private JLabel lblResultadoLineasBlanco;

	private JLabel lblResultadoCantidadLineasTotales;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					Programa frame = new Programa();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Programa() {
		setTitle("Herramienta de testing - Grupo 2 - 1º Cuatrimestre 2020");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 530);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Barra de menu
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 700, 21);
		contentPane.add(menuBar);

		JMenu mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);

		JMenuItem menuItemSeleccionarCarpeta = new JMenuItem("Seleccionar carpeta...");
		menuItemSeleccionarCarpeta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				seleccionarCarpeta();
			}
		});
		mnArchivo.add(menuItemSeleccionarCarpeta);

		JMenuItem menuItemSalir = new JMenuItem("Salir");
		menuItemSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnArchivo.add(menuItemSalir);

		JLabel lblListadoDeArchivos = new JLabel("Listado de archivos");
		lblListadoDeArchivos.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblListadoDeArchivos.setBounds(10, 21, 166, 31);
		contentPane.add(lblListadoDeArchivos);

		listaArchivos = new JList();
		listaArchivos.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				onClickArchivo();
			}
		});
		listaArchivos.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		listaArchivos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaArchivos.setBounds(10, 50, 345, 284);
		JScrollPane listScrollerArchivos = new JScrollPane();
		listScrollerArchivos.setBounds(10, 50, 345, 284);
		listScrollerArchivos.setViewportView(listaArchivos);
		listaArchivos.setLayoutOrientation(JList.VERTICAL);
		contentPane.add(listScrollerArchivos);

		JLabel lblClases = new JLabel("Clases");
		lblClases.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblClases.setBounds(365, 21, 166, 31);
		contentPane.add(lblClases);

		listaClases = new JList();
		listaClases.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				onClickClase();
			}
		});
		listaClases.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		listaClases.setBounds(365, 50, 319, 122);
		JScrollPane listScrollerClases = new JScrollPane();
		listScrollerClases.setBounds(365, 50, 319, 122);
		listScrollerClases.setViewportView(listaClases);
		listaClases.setLayoutOrientation(JList.VERTICAL);
		contentPane.add(listScrollerClases);

		listaMetodos = new JList();
		listaMetodos.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				onClickMetodo();
			}
		});
		listaMetodos.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		listaMetodos.setBounds(365, 212, 319, 122);
		JScrollPane listScrollerMetodos = new JScrollPane();
		listScrollerMetodos.setBounds(365, 212, 319, 122);
		listScrollerMetodos.setViewportView(listaMetodos);
		listaMetodos.setLayoutOrientation(JList.VERTICAL);
		contentPane.add(listScrollerMetodos);

		JLabel lblMtodos = new JLabel("Métodos");
		lblMtodos.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMtodos.setBounds(365, 183, 166, 31);
		contentPane.add(lblMtodos);

		JLabel lblCantidadDeLneas = new JLabel("Cantidad de líneas totales");
		lblCantidadDeLneas.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblCantidadDeLneas.setBounds(20, 366, 231, 21);
		contentPane.add(lblCantidadDeLneas);

		JLabel lblCantidadDeLneas_1 = new JLabel("Cantidad de líneas de código");
		lblCantidadDeLneas_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblCantidadDeLneas_1.setBounds(20, 396, 231, 21);
		contentPane.add(lblCantidadDeLneas_1);

		JLabel lblCantidadDeLneas_2 = new JLabel("Cantidad de líneas comentadas");
		lblCantidadDeLneas_2.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblCantidadDeLneas_2.setBounds(20, 426, 231, 21);
		contentPane.add(lblCantidadDeLneas_2);

		JLabel lblCantidadDeLneas_3 = new JLabel("Cantidad de líneas en blanco");
		lblCantidadDeLneas_3.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblCantidadDeLneas_3.setBounds(20, 456, 231, 21);
		contentPane.add(lblCantidadDeLneas_3);

		lblResultadoCantidadLineasTotales = new JLabel("-");
		lblResultadoCantidadLineasTotales.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultadoCantidadLineasTotales.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblResultadoCantidadLineasTotales.setBounds(250, 366, 46, 21);
		contentPane.add(lblResultadoCantidadLineasTotales);

		lblResultadoLineasCodigo = new JLabel("-");
		lblResultadoLineasCodigo.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultadoLineasCodigo.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblResultadoLineasCodigo.setBounds(250, 396, 46, 21);
		contentPane.add(lblResultadoLineasCodigo);

		lblResultadoLineasComentadas = new JLabel("-");
		lblResultadoLineasComentadas.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultadoLineasComentadas.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblResultadoLineasComentadas.setBounds(250, 426, 46, 21);
		contentPane.add(lblResultadoLineasComentadas);

		lblResultadoLineasBlanco = new JLabel("-");
		lblResultadoLineasBlanco.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultadoLineasBlanco.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblResultadoLineasBlanco.setBounds(250, 456, 46, 21);
		contentPane.add(lblResultadoLineasBlanco);

		JLabel lblPorcentajeDeComentarios = new JLabel("Porcentaje de comentarios");
		lblPorcentajeDeComentarios.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblPorcentajeDeComentarios.setBounds(296, 396, 198, 21);
		contentPane.add(lblPorcentajeDeComentarios);

		lblResultadoPjeComentarios = new JLabel("-");
		lblResultadoPjeComentarios.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultadoPjeComentarios.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblResultadoPjeComentarios.setBounds(502, 397, 72, 21);
		contentPane.add(lblResultadoPjeComentarios);

		JLabel lblComplejidadCiclomtica = new JLabel("Complejidad ciclomática");
		lblComplejidadCiclomtica.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblComplejidadCiclomtica.setBounds(296, 366, 179, 21);
		contentPane.add(lblComplejidadCiclomtica);

		lblResultadoComplejidadCiclomatica = new JLabel("-");
		lblResultadoComplejidadCiclomatica.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultadoComplejidadCiclomatica.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblResultadoComplejidadCiclomatica.setBounds(502, 367, 70, 21);
		contentPane.add(lblResultadoComplejidadCiclomatica);

		JLabel lblFanOut = new JLabel("Fan out");
		lblFanOut.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblFanOut.setBounds(584, 396, 56, 21);
		contentPane.add(lblFanOut);

		lblResultadoFanIn = new JLabel("-");
		lblResultadoFanIn.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultadoFanIn.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblResultadoFanIn.setBounds(638, 366, 46, 21);
		contentPane.add(lblResultadoFanIn);

		JLabel lblFanIn = new JLabel("Fan in");
		lblFanIn.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblFanIn.setBounds(582, 366, 56, 21);
		contentPane.add(lblFanIn);

		lblResultadoFanOut = new JLabel("-");
		lblResultadoFanOut.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultadoFanOut.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblResultadoFanOut.setBounds(638, 396, 46, 21);
		contentPane.add(lblResultadoFanOut);

		JLabel lblLongitud = new JLabel("Longitud");
		lblLongitud.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblLongitud.setBounds(306, 438, 65, 21);
		contentPane.add(lblLongitud);

		lblResultadoLongitud = new JLabel("-");
		lblResultadoLongitud.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultadoLongitud.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblResultadoLongitud.setBounds(365, 439, 65, 21);
		contentPane.add(lblResultadoLongitud);

		JLabel lblVolumen = new JLabel("Volumen");
		lblVolumen.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblVolumen.setBounds(430, 438, 64, 21);
		contentPane.add(lblVolumen);

		lblResultadoVolumen = new JLabel("-");
		lblResultadoVolumen.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultadoVolumen.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblResultadoVolumen.setBounds(489, 439, 71, 21);
		contentPane.add(lblResultadoVolumen);

		JLabel lblEsfuerzo = new JLabel("Esfuerzo");
		lblEsfuerzo.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblEsfuerzo.setBounds(562, 438, 72, 21);
		contentPane.add(lblEsfuerzo);

		lblResultadoEsfuerzo = new JLabel("-");
		lblResultadoEsfuerzo.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultadoEsfuerzo.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblResultadoEsfuerzo.setBounds(624, 439, 46, 21);
		contentPane.add(lblResultadoEsfuerzo);

		JPanel panelHalstead = new JPanel();
		panelHalstead.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Halstead",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		((TitledBorder) panelHalstead.getBorder()).setTitleFont(((TitledBorder) panelHalstead.getBorder())
				.getTitleFont().deriveFont(Font.BOLD).deriveFont((float) 15.00));
		panelHalstead.setBounds(296, 419, 380, 58);
		contentPane.add(panelHalstead);

		panelAnalisis = new JPanel();
		panelAnalisis.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null),
				"An\u00E1lisis del m\u00E9todo", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		((TitledBorder) panelAnalisis.getBorder()).setTitleFont(((TitledBorder) panelAnalisis.getBorder())
				.getTitleFont().deriveFont(Font.BOLD).deriveFont((float) 15.00));
		panelAnalisis.setToolTipText("");
		panelAnalisis.setBounds(10, 345, 674, 145);
		contentPane.add(panelAnalisis);
	}

	/// INICIO Metodos onClick para las distintas listas
	private void onClickArchivo() {
		try {
			// Al tocar un archivo, se obtiene la lista de clases incluidas en el mismo
			String archivo = (String) listaArchivos.getSelectedValue();

			if (archivo != "") {
				String[] clases = new String[1];
				clases[0] = archivo.substring(archivo.lastIndexOf("\\") + 1, archivo.lastIndexOf("."));
				actualizarLista(listaClases, clases);
				actualizarLista(listaMetodos, new String[0]);
				resetearCampos();
			}
		} catch (Exception e) {
		}
	}

	private void onClickClase() {
		try {
			FileInputStream in = new FileInputStream((String) listaArchivos.getSelectedValue());
			CompilationUnit cu = JavaParser.parse(in);
			if (listaClases.getSelectedValue() != null) {
				ClassOrInterfaceDeclaration clase = cu.getClassByName((String) listaClases.getSelectedValue()).get();

				List<MethodDeclaration> metodos = clase.getMethods();
				List<ConstructorDeclaration> constructores = clase.getConstructors();

				String nombres[] = new String[metodos.size() + constructores.size()];
				for (int i = 0; i < constructores.size(); i++) {
					nombres[i] = constructores.get(i).getName().toString();
				}
				for (int i = 0; i < metodos.size(); i++) {
					nombres[i + constructores.size()] = metodos.get(i).getName().toString();
				}

				actualizarLista(listaMetodos, nombres);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void onClickMetodo() {
		if (!listaMetodos.getSelectedValue().toString().isEmpty()) {
			// Seteo el nombre del recuadro de resultados
			String nombre = "An\u00E1lisis del m\u00E9todo \"" + (String) listaMetodos.getSelectedValue() + "\"";
			TitledBorder titledBorder = (TitledBorder) panelAnalisis.getBorder();
			titledBorder.setTitle(nombre);
			repaint();

			String codigo = Utils.obtenerCodigo((String) listaArchivos.getSelectedValue(),
					(String) listaMetodos.getSelectedValue(),
					Utils.obtenerOverloading(listaMetodos.getSelectedIndex(), listaMetodos.getModel()));

			Analisis analisis = new Analisis(codigo, listaMetodos, listaArchivos);
			this.completarCampos(analisis);
		}
	}

	/// FIN Metodos onClick para las distintas listas
	private void resetearCampos() {
		String nombre = "An\u00E1lisis del m\u00E9todo";
		TitledBorder titledBorder = (TitledBorder) panelAnalisis.getBorder();
		titledBorder.setTitle(nombre);
		repaint();

		lblResultadoCantidadLineasTotales.setText("-");
		lblResultadoLineasBlanco.setText("-");
		lblResultadoLineasCodigo.setText("-");
		lblResultadoLineasComentadas.setText("-");
		lblResultadoPjeComentarios.setText("-");
		lblResultadoEsfuerzo.setText("-");
		lblResultadoLongitud.setText("-");
		lblResultadoComplejidadCiclomatica.setText("-");
		lblResultadoVolumen.setText("-");
		lblResultadoFanIn.setText("-");
		lblResultadoFanOut.setText("-");
	}

	private void completarCampos(Analisis analisis) {
		DecimalFormat df = new DecimalFormat("0.00");
		lblResultadoCantidadLineasTotales.setText(String.valueOf(analisis.getLineasTotales()));
		lblResultadoLineasBlanco.setText(String.valueOf(analisis.getLineasEnBlanco()));
		lblResultadoLineasCodigo.setText(String.valueOf(analisis.getLineasCodigo()));
		lblResultadoLineasComentadas.setText(String.valueOf(analisis.getLineasComentadas()));
		lblResultadoPjeComentarios.setText(String.valueOf(
				df.format(((float) analisis.getLineasComentadas() / analisis.getLineasTotales()) * 100) + "%"));
		lblResultadoComplejidadCiclomatica.setText(String.valueOf(analisis.getComplejidadCiclomatica()));
		lblResultadoEsfuerzo.setText(String.valueOf(df.format(analisis.getHalsteadEsfuerzo())));
		lblResultadoLongitud.setText(String.valueOf(df.format(analisis.getHalsteadLongitud())));
		lblResultadoVolumen.setText(String.valueOf(df.format(analisis.getHalsteadVolumen())));
		lblResultadoFanIn.setText(String.valueOf(analisis.getFanIn()));
		lblResultadoFanOut.setText(String.valueOf(analisis.getFanOut()));
	}

	private void seleccionarCarpeta() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (chooser.showOpenDialog(Programa.this) == JFileChooser.APPROVE_OPTION) {

			// Se busca todos los .java
			File[] archivos = chooser.getSelectedFile().listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.toLowerCase().endsWith(".java");
				}
			});

			if (archivos.length > 0) {
				String[] nombres = new String[archivos.length];
				for (int i = 0; i < archivos.length; i++) {
					nombres[i] = archivos[i].getPath();
				}

				actualizarLista(listaArchivos, nombres);
				actualizarLista(listaClases, new String[1]);
				actualizarLista(listaMetodos, new String[1]);
			} else {
				JOptionPane.showMessageDialog(Programa.this, "La carpeta seleccionada no contiene archivos .java",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private static void actualizarLista(JList<String> lista, String[] elementos) {
		try {
			lista.setModel(new AbstractListModel<String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = -2834588284593084928L;
				String[] values = elementos;

				public int getSize() {
					return values.length;
				}

				public String getElementAt(int index) {
					return values[index];
				}
			});
		} catch (Exception ex) {
		}
	}
}