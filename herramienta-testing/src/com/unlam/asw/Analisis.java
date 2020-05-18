package com.unlam.asw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import javax.swing.JList;

public class Analisis {

	private int lineasTotales;
	private int lineasComentadas;
	private int lineasEnBlanco;
	private int lineasCodigo;
	private int fanIn;
	private int fanOut;
	private int complejidadCiclomatica;
	private float halsteadEsfuerzo;
	private float halsteadVolumen;
	private float halsteadLongitud;
	private JList<String> listaMetodos;
	private JList<String> listaArchivos;
	private String codigo;

	public Analisis(String codigo, JList<String> listaMetodos, JList<String> listaArchivos) {
		this.codigo = codigo;
		this.listaMetodos = listaMetodos;
		this.listaArchivos = listaArchivos;
		calcularEstadisticas();
	}

	private void calcularEstadisticas() {
		// Saco la primer y ultima linea
		lineasTotales = obtenerCantLineasTotales(this.codigo) - 2;
		lineasComentadas = obtenerCantLineasComentadas(this.codigo);
		lineasEnBlanco = obtenerCantLineasEnBlanco(this.codigo);
		lineasCodigo = lineasTotales - lineasComentadas - lineasEnBlanco;
		complejidadCiclomatica = calcularComplejidadCiclomatica(this.codigo);

		Halstead analisisHalstead = new Halstead(this.codigo);
		halsteadEsfuerzo = analisisHalstead.getEsfuerzo();
		halsteadVolumen = analisisHalstead.getVolumen();
		halsteadLongitud = analisisHalstead.getLongitud();

		fanIn = FanInFanOut.getFanIn(this.codigo, listaMetodos);
		fanOut = FanInFanOut.getFanOut(this.codigo, listaMetodos, listaArchivos);
	}

	// INICIO Metodos de lineas
	private int obtenerCantLineasEnBlanco(String codigo) {
		final BufferedReader br = new BufferedReader(new StringReader(codigo));
		String linea;
		int contador = 0;

		try {
			while ((linea = br.readLine()) != null) {
				if (linea.trim().isEmpty()) {
					contador++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return contador;
	}

	private int obtenerCantLineasTotales(String codigo) {
		int posicion, contador = 0;
		posicion = codigo.indexOf('\n');

		while (posicion != -1) {
			contador++;
			posicion = codigo.indexOf('\n', posicion + 1);
		}

		return contador + 1;
	}

	private int obtenerCantLineasComentadas(String codigo) {
		int posicion, posicionSalto = 0, contador = 0;
		posicion = codigo.indexOf("//");

		while (posicion != -1) {
			contador++;
			posicionSalto = codigo.indexOf('\n', posicion + 1);
			posicion = codigo.indexOf("//", posicionSalto + 1);
		}

		return contador;
	}
	// FIN Metodos de lineas

	/* TODO: Poner una condicion para que no lea las lineas comentadas */
	private int calcularComplejidadCiclomatica(String codigo) {
		int contador = 1;
		String[] lineas = codigo.split("\\n");
		for (int i = 0; i < lineas.length; i++) {
			contador += (Utils.contarOcurrencias(lineas[i], '&') + Utils.contarOcurrencias(lineas[i], '|')) / 2;

			if (lineas[i].contains("while (") || lineas[i].contains("for (") || lineas[i].contains("case ")
					|| lineas[i].contains("catch ") || lineas[i].contains("if (")) {
				contador++;
			}
		}

		return contador;
	}

	public String getCodigo() {
		return codigo;
	}

	public int getLineasTotales() {
		return lineasTotales;
	}

	public int getLineasComentadas() {
		return lineasComentadas;
	}

	public int getLineasEnBlanco() {
		return lineasEnBlanco;
	}

	public int getLineasCodigo() {
		return lineasCodigo;
	}

	public int getComplejidadCiclomatica() {
		return complejidadCiclomatica;
	}

	public float getHalsteadEsfuerzo() {
		return halsteadEsfuerzo;
	}

	public float getHalsteadVolumen() {
		return halsteadVolumen;
	}

	public float getHalsteadLongitud() {
		return halsteadLongitud;
	}

	public int getFanIn() {
		return fanIn;
	}

	public int getFanOut() {
		return fanOut;
	}

}
