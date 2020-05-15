package com.unlam.asw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class Analisis {

	private String codigo;
	private int lineasTotales;
	private int lineasComentadas;
	private int lineasEnBlanco;
	private int lineasCodigo;
	private int complejidadCiclomatica;

	public Analisis(String codigo) {
		this.codigo = codigo;
	}

	public void calcularEstadisticas() {
		lineasTotales = obtenerCantLineasTotales(this.codigo);
		lineasComentadas = obtenerCantLineasComentadas(this.codigo);
		lineasEnBlanco = obtenerCantLineasEnBlanco(this.codigo);
		lineasCodigo = lineasTotales - lineasComentadas - lineasEnBlanco;
		complejidadCiclomatica = calcularComplejidadCiclomatica(codigo);
//		System.out.println("Lineas totales: " + lineasTotales);
//		System.out.println("Lineas comentadas: " + lineasComentadas);
//		System.out.println("Lineas en blanco: " + lineasEnBlanco);
//		System.out.println("Lineas codigo: " + lineasCodigo);
	}

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
			// TODO Auto-generated catch block
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

	/* TODO: Poner una condicion para que no lea las lineas comentadas */
	private int calcularComplejidadCiclomatica(String codigo) {
		int contador = 1;
		String[] lineas = codigo.split("\\n");
		for (int i = 0; i < lineas.length; i++) {
			contador += (Programa.contarOcurrencias(lineas[i], '&') + Programa.contarOcurrencias(lineas[i], '|')) / 2;

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
}
