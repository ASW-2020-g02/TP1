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

	public Analisis(String codigo) {
		this.codigo = codigo;
	}

	public void calcularEstadisticas() {
		lineasTotales = obtenerCantLineasTotales(this.codigo);
		lineasComentadas = obtenerCantLineasComentadas(this.codigo);
		lineasEnBlanco = obtenerCantLineasEnBlanco(this.codigo);
		lineasCodigo = lineasTotales - lineasComentadas - lineasEnBlanco;

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
}
