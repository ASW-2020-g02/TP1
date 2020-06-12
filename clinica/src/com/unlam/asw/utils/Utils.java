package com.unlam.asw.utils;

import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.JList;

import com.unlam.asw.entities.Paciente;

public class Utils {
	public static void actualizarLista(JList<String> lista, String[] elementos) {

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
