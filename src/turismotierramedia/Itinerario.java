package turismotierramedia;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Itinerario {
	private String usuario;
	private ArrayList<String> prod;
	private Double tiempo;
	private Double costo;
	
	public String getUsuario() {
		return usuario;
	}

	public Itinerario(String usuario, ArrayList<String> prod, double dineroTotal, double tiempoTotal) {
		this.tiempo=tiempoTotal;
		this.costo=dineroTotal;
		this.usuario = usuario;
		this.prod = prod;
	}
	
	public Double getCosto() {
		return this.costo;
	}
	
	public Double getDuracion() {
		return this.tiempo;
	}

	public ArrayList<String> getNombreProd() {
		return prod;
	}
	
	//Guarda todos los nombres de los productos comprados en un solo String
	public String todosProductosEnUnString() {
		String Arraystring = this.prod.get(0);

		for(int i = 1; i < this.prod.size(); i++) { 
		Arraystring += "," + this.prod.get(i);
		}
		
		return Arraystring;
	}
}
