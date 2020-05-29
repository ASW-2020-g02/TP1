package com.unlam.asw;

public class Vendedoras {
	private int numero;
	private int ventas;
	private int importeVenta[];

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getVentas() {
		return ventas;
	}

	public void setVentas(int ventas) {
		this.ventas = ventas;
	}

	public double getImporteVenta(int indice) {
		return importeVenta[indice];
	}

	public void setImporteVenta(int indice, int importeVenta) {
		this.importeVenta[indice] = importeVenta;
	}

	public void Vendedora(int ventas, int numero) {
		this.setNumero(numero);
		this.setVentas(ventas);
		importeVenta = new int[ventas];
	}

	public boolean compite(int ventasConsecutivas) {
		return ventas >= ventasConsecutivas;
	}

	public int mayorImporteVentasConsecutivas(int ventasConsecutivas) {
		int mayor = 0;
		for (int i = 0; i <= ventas - ventasConsecutivas; i++) {
			int importe = 0;
			for (int j = i; j < i + ventasConsecutivas; j++) {
				importe += importeVenta[j];
			}
			if (importe > mayor)
				mayor = importe;
		}
		return mayor;
	}
}
