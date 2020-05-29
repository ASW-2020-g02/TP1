package com.unlam.asw;

public class Luchador {
	
	private int numero;
	private double peso;
	private double altura;
	
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	
	public double getAltura() {
		return altura;
	}
	public void setAltura(double altura) {
		this.altura = altura;
	}
	
	public Luchador(int numero) {
		this.numero = numero;
		this.peso = 0;
		this.altura = 0;
	}
	
	public boolean domina(Luchador luchador) {
		if (peso > luchador.peso && altura > luchador.altura)
			return true;
		else if (peso > luchador.peso && !(altura < luchador.altura))
			return true;
		else if (!(peso < luchador.peso && altura > luchador.altura))
			return true;
		else
			return false;
	}

}
