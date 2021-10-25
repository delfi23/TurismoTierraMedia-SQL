package turismotierramedia;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Itinerario {
	
	private Integer id_itinerario;
	private Integer id_promocion;
	private Integer id_atraccion;
	private Integer id_usuario;
	
	public Itinerario(Integer id_itin, Integer id_usuario, Integer id_atraccion, Integer id_promo) {
		this.id_itinerario = id_itin;
		this.id_usuario = id_usuario;
		this.id_atraccion = id_atraccion;
		this.id_promocion = id_promo;
	}
	
	public Itinerario(Integer id_usuario, Integer id_atraccion, Integer id_promo) {
		//this.id_itinerario = id_itin;
		this.id_usuario = id_usuario;
		this.id_atraccion = id_atraccion;
		this.id_promocion = id_promo;
	}
	
	public Integer getId_itinerario() {
		return id_itinerario;
	}

	public Integer getId_promocion() {
		return id_promocion;
	}

	public Integer getId_atraccion() {
		return id_atraccion;
	}

	public Integer getId_usuario() {
		return id_usuario;
	}



	// VIEJO
	
	/*
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
	
	*/
}
