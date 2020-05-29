package com.unlam.asw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JList;

public class FanInFanOut {
	public static int getFanIn(String codigo, JList<String> listaMetodos) {
		int fanIn = 0;
		// Separo el código en líneas
		String[] lineas = codigo.split("\\n");

		// Obtengo la lista de método
		String[] metodos = new String[listaMetodos.getModel().getSize() - 1];

		int indice = 0, seleccionado = listaMetodos.getSelectedIndex();

		// Recorro los distintos metodos
		for (int i = 0; i <= metodos.length; i++) {
			String metodo = listaMetodos.getModel().getElementAt(i);
			if (i != seleccionado) {
				metodos[indice] = metodo;
				indice++;
			}
		}

		// Contabilizo los métodos llamadas por un determinado método
		for (int i = 0; i < lineas.length; i++) {
			for (int j = 0; j < metodos.length; j++) {
				if ((lineas[i].contains(" " + metodos[j] + "(") || lineas[i].contains("." + metodos[j] + "("))) {
					fanIn++;
				}
			}
		}

		return fanIn;
	}

	public static int getFanOut(String codigo, JList<String> listaMetodos, JList<String> listaArchivos) {
		int contadorFanOut = 0;
		String rutaArchivo = listaArchivos.getSelectedValue();
		String metodoSelecionado = listaMetodos.getSelectedValue();
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;

		try {
			archivo = new File(rutaArchivo);
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);

			String linea;
			Pattern p = Pattern.compile(".*\\b" + metodoSelecionado + ".*");

			// En este caso, contabilizo donde es utilizado el método seleccionado
			while ((linea = br.readLine()) != null) {
				Matcher m = p.matcher(linea);

				if (m.find() && !linea.contains("class") && !linea.contains("public") && !linea.contains("private")
						&& !linea.contains("protected")) {
					contadorFanOut++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (null != fr) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return contadorFanOut;
	}
}