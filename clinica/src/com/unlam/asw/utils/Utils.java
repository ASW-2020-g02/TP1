package com.unlam.asw.utils;

import javax.swing.AbstractListModel;
import javax.swing.JList;

public class Utils {
	public static void actualizarLista(JList<String> lista, String[] elementos) {
		lista.setModel(new AbstractListModel<String>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -2834588284593084928L;

			// Se setean los elementos como los values de la lista
			String[] values = elementos;

			// Metodos especificos de la lista
			public int getSize() {
				return values.length;
			}

			public String getElementAt(int index) {
				return values[index];
			}
		});
	}
}
