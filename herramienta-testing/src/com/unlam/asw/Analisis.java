package com.unlam.asw;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
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
		obtenerCantLineasComentadas(this.codigo);
		complejidadCiclomatica = calcularComplejidadCiclomatica(this.codigo);

		Halstead analisisHalstead = new Halstead(this.codigo);
		halsteadEsfuerzo = analisisHalstead.getEsfuerzo();
		halsteadVolumen = analisisHalstead.getVolumen();
		halsteadLongitud = analisisHalstead.getLongitud();

		fanIn = FanInFanOut.getFanIn(this.codigo, listaMetodos);
		fanOut = FanInFanOut.getFanOut(this.codigo, listaMetodos, listaArchivos);
	}

	private void obtenerCantLineasComentadas(String codigo) {
		String line = "";
		int comment_count = 0;
		int line_count = 0;
		int blank_lines = 0;
		int code_line = 0;
		// Se comienza a leer linea por linea el código
		try {
			BufferedReader br = new BufferedReader(new StringReader(codigo));

			while ((line = br.readLine()) != null) {
				// Por cada linea leida, aumento el contador
				line_count++;
				if (line.trim().isEmpty()) {
					blank_lines++;
				} else if (line.contains("//")) {
					comment_count++;
					if (!line.trim().startsWith("//")) {
						code_line++;// En caso de que la linea comience con codigo y luego venga un comentario, se
									// debe contabilizar
					}
				} else if (line.contains("/*")) {
					if (!line.trim().startsWith("/*")) {
						code_line++; // Mismo caso que el anterior
					}
					comment_count++;
					if (!line.trim().endsWith("*/")) { // Iteración utilizada para comentarios en bloque
						while (!(line = br.readLine()).trim().endsWith("'*/'")) {
							line_count++;
							comment_count++;
							if (line.endsWith("*/")) {
								break;
							}
						}
					}
				} else {
					code_line++;
				}
			}
			br.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Debo descontar la linea de la firma y el } final
		lineasTotales = line_count - 2;
		lineasCodigo = code_line - 2;

		lineasComentadas = comment_count;
		lineasEnBlanco = blank_lines;
	}

	private int calcularComplejidadCiclomatica(String codigo) {
		int contador = 1;
		String[] lineas = codigo.split("\\n");
		// Por cada linea, analizo
		for (int i = 0; i < lineas.length; i++) {
			// Cuento la cantidad de AND y OR
			contador += (Utils.contarOcurrencias(lineas[i], '&') + Utils.contarOcurrencias(lineas[i], '|')) / 2;

			// Chequeamos que no sea un comentario, y despues nos fijamos si la linea
			// contiene algun if, ciclo iterativo, switch case, y la cantidad de && y || que
			// tenga un if
			if ((!(lineas[i].trim().startsWith("//") || lineas[i].trim().startsWith("/*")
					|| lineas[i].trim().startsWith("*"))) && lineas[i].contains("while (")
					|| lineas[i].contains("for (") || lineas[i].contains("case ") || lineas[i].contains("catch ")
					|| lineas[i].contains("if (")) {
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
