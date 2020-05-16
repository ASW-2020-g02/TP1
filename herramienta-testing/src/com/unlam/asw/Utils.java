package com.unlam.asw;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ListModel;

public class Utils {
	public static int obtenerOverloading(int index, ListModel lista) {
		// Averiguamos si hay varios metodos con el mismo nombre y, de ser asi,
		// obtenemos el numero del que seleccionamos.
		int c = 1;
		String nombre = (String) lista.getElementAt(index);
		for (int i = 0; i < index; i++) {
			if (nombre.compareTo((String) lista.getElementAt(i)) == 0) {
				c++;
			}
		}
		return c;
	}

	public static int contarOcurrencias(String string, char caracter) {
		int contador = 0;
		for (int i = 0; i < string.length(); i++) {
			if (string.charAt(i) == caracter) {
				contador++;
			}
		}
		return contador;
	}
	
	public static String[] leerArchivo(String rutaArchivo) throws IOException {
		FileReader fileReader = new FileReader(rutaArchivo);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		List<String> lines = new ArrayList<String>();
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			lines.add(line);
		}
		bufferedReader.close();
		return lines.toArray(new String[lines.size()]);
	}
	
	public static String obtenerCodigo(String rutaArchivo, String nombre, int i) {
		String codigo = "";
		try {
			String[] archivo = leerArchivo(rutaArchivo);

			int inicio = -1;
			while (i != 0) {
				if (archivo[inicio + 1].contains(" " + nombre + "(") && archivo[inicio + 1].endsWith("{")) {
					i--;
				}
				inicio++;
			}

			int fin = inicio + 1, contadorLlaves = 1;
			while (contadorLlaves != 0) {
				contadorLlaves += contarOcurrencias(archivo[fin], '{');
				contadorLlaves -= contarOcurrencias(archivo[fin], '}');
				fin++;
			}

			String[] arrayCodigo = Arrays.copyOfRange(archivo, inicio, fin);
			for (int j = 0; j < arrayCodigo.length; j++) {
				try {
					arrayCodigo[j] = arrayCodigo[j].substring(1);
				} catch (Exception e) {
				}
			}

			codigo = String.join("\n", arrayCodigo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return codigo;
	}

}
