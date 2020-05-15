package com.unlam.asw.tp1;

public class Triangulo {

	private final double lado1;
	private final double lado2;
	private final double lado3;
	public static final int ERROR = -2;
	public static final int NO_ES_TRIANGULO = -1;
	public static final int EQUILATERO = 0;
	public static final int ISOSCELES = 1;
	public static final int ESCALENO = 2;

	public Triangulo(double lado1, double lado2, double lado3) {
		this.lado1 = lado1;
		this.lado2 = lado2;
		this.lado3 = lado3;
	}

	public int obtenerTipoTriangulo() {
		// Verifico que todos los lados sean positivos
		if (lado1 <= 0 || lado2 <= 0 || lado3 <= 0) {
			return ERROR;
		}

		// Comprobacion de que se puede formar un triangulo con los lados
		if (lado1 + lado2 <= lado3 || lado1 + lado3 <= lado2 || lado2 + lado3 <= lado1) {
			return NO_ES_TRIANGULO;
		}

		// Si los tres lados son iguales, se tiene un triangulo equilatero
		if (lado1 == lado2 && lado1 == lado3) {
			return EQUILATERO;
		} else if (lado2 == lado3) {
			return ESCALENO;
		} else {
			return ISOSCELES;
		}
	}
}
