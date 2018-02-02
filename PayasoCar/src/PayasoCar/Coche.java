package PayasoCar;

import java.util.Scanner;

import PayasoCar.Payaso;

//Andrés Valera y Juan Carlos Medina.

public class Coche {
	// Capacidad máxima de payasos y cantidad de payasos introducidos hasta el momento.
	private int capacidad = 200;
	private int cantidad = 0;
	
	public void setCapacidad(int capacidad) {
		this.capacidad=capacidad;
	}
	
	public int getCapacidad() {
		return this.capacidad;
	}
	
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	public int getCantidad() {
		return this.cantidad;
	}
	
	public void meterPayaso() {
		cantidad++;
	}
	
	public void sacarPayaso() {
		cantidad--;
	}
}
