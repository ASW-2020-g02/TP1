package com.unlam.asw;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Halstead {
	private float longitud;
	private float volumen;
	private float esfuerzo;

	public Halstead(String codigo) {
		calcularMetricasHalstead(codigo);
	}

	private void calcularMetricasHalstead(String codigo) {
		boolean flagElse = false;
		String key = "else if";

		HashMap<String, Integer> operadores = new HashMap<String, Integer>();
		HashMap<String, Integer> operandos = new HashMap<String, Integer>();
		int indexFirstNewLine = codigo.indexOf('\n');
		int indexLastNewLine = codigo.lastIndexOf('\n');	

		if (indexFirstNewLine != -1 && indexLastNewLine != -1 && indexFirstNewLine != indexLastNewLine) {
			codigo = codigo.substring(codigo.indexOf('\n') + 1, codigo.lastIndexOf('\n'));
		}
		String[] lineas = codigo.split("\n");
		for (String string : lineas) {
			Pattern pattern = Pattern.compile("//.*|/\\*((.|\\n)(?!=*/))+\\*/");
			Matcher matcher = pattern.matcher(string);
			// Salteo lineas con comentarios
			if (!matcher.find()) {
				String[] contenidoString = string.split(" ");
				for (String string2 : contenidoString) {
					string2 = string2.trim();
					if (!string2.equals("")) {
						if (isNumeric(string2)) {
							incrementarContador(operandos, String.valueOf(string2), 1);
						} else if (esKeyword(string2)) {
							if (string2.equals("else")) {
								flagElse = true;
							}
							incrementarContador(operadores, string2, 1);
						} else if (esOperador(string2)) {
							incrementarContador(operadores, string2, 1);
						} else if (!Character.isUpperCase(string2.charAt(0))) {
							string2 = string2.replaceAll("[^a-zA-Z0-9]", "");

							if (!string2.equals("")) {
								incrementarContador(operandos, string2, 1);
							}
						} else if (string2.toUpperCase().equals(string2)) {
							string2 = string2.replaceAll("[^a-zA-Z0-9]", "");

							if (!string2.equals("")) {
								incrementarContador(operandos, string2, 1);
							}
						}
					}
				}
				// Debo verificar si hay un else if, y de esta forma no duplico
				if (flagElse) {
					int res = (string.length() - string.replace(key, "").length()) / key.length();
					if (res > 0) {
						incrementarContador(operadores, "if", -1);
						incrementarContador(operadores, "else", -1);
						incrementarContador(operadores, "else if", 1);
					}
					flagElse = false;
				}
			}
		}

		int operadoresUnicos = 0, operandosUnicos = 0;
		Iterator it = operadores.entrySet().iterator();
		while (it.hasNext()) {
			Entry entrada = (Entry) it.next();
			if ((int) entrada.getValue() > 0) {
				operadoresUnicos++;
			}
		}
		it = operandos.entrySet().iterator();
		while (it.hasNext()) {
			Entry entrada = (Entry) it.next();
			if ((int) entrada.getValue() > 0) {
				operandosUnicos++;
			}
		}
		longitud = operadoresUnicos + operandosUnicos;

		float operandosTotales = 0, operadoresTotales = 0;
		for (int v : operandos.values()) {
			operandosTotales += v;
		}
		for (int v : operadores.values()) {
			operadoresTotales += v;
		}

		volumen = (float) (longitud * Math.log10(operandosTotales + operadoresTotales) / Math.log10(2));
		esfuerzo = (float) (operadoresUnicos / 2) * (operandosTotales / 2) * volumen;
	}

	private boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?");
	}

	private boolean esOperador(String s) {
		switch (s) {
		case "+":
		case "-":
		case "*":
		case "/":
		case "%":
		case "=":
		case "<":
		case ">":
		case "?":
		case ":":
		case "&":
		case "&&":
		case "|":
		case "||":
		case "!":
		case "==":
		case ">=":
		case "<=":
		case "+=":
		case "-=":
		case "/=":
		case "*=":
		case "!=":
			return true;
		default:
			return false;
		}
	}

	private void incrementarContador(HashMap<String, Integer> mapa, String key, int incremento) {
		int actual = (mapa.containsKey(key)) ? mapa.get(key) : 0;
		mapa.put(key, actual + incremento);
	}

	private boolean esKeyword(String s) {
		switch (s) {
		case "abstract":
		case "for":
		case "new":
		case "switch":
		case "default":
		case "continue":
		case "synchronized":
		case "boolean":
		case "do":
		case "if":
		case "private":
		case "break":
		case "double":
		case "implements":
		case "protected":
		case "throw":
		case "byte":
		case "else":
		case "public":
		case "throws":
		case "case":
		case "enum":
		case "return":
		case "catch":
		case "extends":
		case "int":
		case "short":
		case "try":
		case "char":
		case "interface":
		case "static":
		case "final":
		case "void":
		case "class":
		case "finally":
		case "long":
		case "const":
		case "float":
		case "super":
		case "while":
			return true;
		default:
			return false;
		}
	}

	public float getLongitud() {
		return longitud;
	}

	public float getEsfuerzo() {
		return esfuerzo;
	}

	public float getVolumen() {
		return volumen;
	}
}
