package com.unlam.asw;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuItem;
import java.awt.Font;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.SwingConstants;

public class Programa extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7097174973249116900L;

	private JPanel contentPane;

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

		// MENU //
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 700, 21);
		contentPane.add(menuBar);

		JMenu mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);

		JMenuItem menuItemSeleccionarCarpeta = new JMenuItem("Seleccionar carpeta...");
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

		JList listaArchivos = new JList();
		listaArchivos.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		listaArchivos.setBounds(10, 50, 345, 284);
		contentPane.add(listaArchivos);

		JLabel lblClases = new JLabel("Clases");
		lblClases.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblClases.setBounds(365, 21, 166, 31);
		contentPane.add(lblClases);

		JList listaClases = new JList();
		listaClases.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		listaClases.setBounds(365, 50, 319, 122);
		contentPane.add(listaClases);

		JList listaMetodos = new JList();
		listaMetodos.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		listaMetodos.setBounds(365, 212, 319, 122);
		contentPane.add(listaMetodos);

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

		JLabel lblResultadoCantidadLineasTotales = new JLabel("-");
		lblResultadoCantidadLineasTotales.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultadoCantidadLineasTotales.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblResultadoCantidadLineasTotales.setBounds(250, 366, 46, 21);
		contentPane.add(lblResultadoCantidadLineasTotales);

		JLabel lblResultadoLineasCodigo = new JLabel("-");
		lblResultadoLineasCodigo.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultadoLineasCodigo.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblResultadoLineasCodigo.setBounds(250, 396, 46, 21);
		contentPane.add(lblResultadoLineasCodigo);

		JLabel lblResultadoLineasComentadas = new JLabel("-");
		lblResultadoLineasComentadas.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultadoLineasComentadas.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblResultadoLineasComentadas.setBounds(250, 426, 46, 21);
		contentPane.add(lblResultadoLineasComentadas);

		JLabel lblResultadoLineasBlanco = new JLabel("-");
		lblResultadoLineasBlanco.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultadoLineasBlanco.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblResultadoLineasBlanco.setBounds(250, 456, 46, 21);
		contentPane.add(lblResultadoLineasBlanco);

		JLabel lblPorcentajeDeComentarios = new JLabel("Porcentaje de comentarios");
		lblPorcentajeDeComentarios.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblPorcentajeDeComentarios.setBounds(318, 396, 198, 21);
		contentPane.add(lblPorcentajeDeComentarios);

		JLabel lblResultadoPjeComentarios = new JLabel("-");
		lblResultadoPjeComentarios.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultadoPjeComentarios.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblResultadoPjeComentarios.setBounds(523, 396, 46, 21);
		contentPane.add(lblResultadoPjeComentarios);

		JLabel lblComplejidadCiclomtica = new JLabel("Complejidad ciclomática");
		lblComplejidadCiclomtica.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblComplejidadCiclomtica.setBounds(318, 366, 179, 21);
		contentPane.add(lblComplejidadCiclomtica);

		JLabel lblResultadoComplejidadCiclomatica = new JLabel("-");
		lblResultadoComplejidadCiclomatica.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultadoComplejidadCiclomatica.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblResultadoComplejidadCiclomatica.setBounds(523, 366, 46, 21);
		contentPane.add(lblResultadoComplejidadCiclomatica);

		JLabel lblFanOut = new JLabel("Fan out");
		lblFanOut.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblFanOut.setBounds(590, 396, 56, 21);
		contentPane.add(lblFanOut);

		JLabel lblResultadoFanIn = new JLabel("-");
		lblResultadoFanIn.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultadoFanIn.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblResultadoFanIn.setBounds(638, 366, 46, 21);
		contentPane.add(lblResultadoFanIn);

		JLabel lblFanIn = new JLabel("Fan in");
		lblFanIn.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblFanIn.setBounds(590, 366, 56, 21);
		contentPane.add(lblFanIn);

		JLabel lblResultadoFanOut = new JLabel("-");
		lblResultadoFanOut.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultadoFanOut.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblResultadoFanOut.setBounds(638, 396, 46, 21);
		contentPane.add(lblResultadoFanOut);

		JLabel lblLongitud = new JLabel("Longitud");
		lblLongitud.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblLongitud.setBounds(335, 439, 72, 21);
		contentPane.add(lblLongitud);

		JLabel lblResultadoLongitud = new JLabel("-");
		lblResultadoLongitud.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultadoLongitud.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblResultadoLongitud.setBounds(401, 439, 40, 21);
		contentPane.add(lblResultadoLongitud);

		JLabel lblVolumen = new JLabel("Volumen");
		lblVolumen.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblVolumen.setBounds(451, 439, 72, 21);
		contentPane.add(lblVolumen);

		JLabel lblResultadoVolumen = new JLabel("-");
		lblResultadoVolumen.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultadoVolumen.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblResultadoVolumen.setBounds(516, 439, 40, 21);
		contentPane.add(lblResultadoVolumen);

		JLabel lblEsfuerzo = new JLabel("Esfuerzo");
		lblEsfuerzo.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblEsfuerzo.setBounds(562, 439, 72, 21);
		contentPane.add(lblEsfuerzo);

		JLabel lblResultadoEsfuerzo = new JLabel("-");
		lblResultadoEsfuerzo.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultadoEsfuerzo.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblResultadoEsfuerzo.setBounds(628, 439, 40, 21);
		contentPane.add(lblResultadoEsfuerzo);

		JPanel panelHalstead = new JPanel();
		panelHalstead.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Halstead", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		((TitledBorder) panelHalstead.getBorder()).setTitleFont(((TitledBorder) panelHalstead.getBorder())
				.getTitleFont().deriveFont(Font.BOLD).deriveFont((float) 15.00));
		panelHalstead.setBounds(315, 419, 356, 58);
		contentPane.add(panelHalstead);

		JPanel panelAnalisis = new JPanel();
		panelAnalisis.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null),
				"Resultados del an\u00E1lisis", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		((TitledBorder) panelAnalisis.getBorder()).setTitleFont(((TitledBorder) panelAnalisis.getBorder())
				.getTitleFont().deriveFont(Font.BOLD).deriveFont((float) 15.00));
		panelAnalisis.setToolTipText("Resultados");
		panelAnalisis.setBounds(10, 345, 674, 145);
		contentPane.add(panelAnalisis);

	}
}
