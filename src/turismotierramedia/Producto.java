package turismotierramedia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class Producto {
	private int idProducto;
	private String nombreProducto;
	private TipoAtraccion tipoAtraccion;
	private Double costoTotal;
	private Double duracionTotal;

	// Constructor para Producto con Promos
	public Producto(int idProducto, ArrayList<Atracciones> atracciones, String nombreProducto,
			TipoAtraccion tipoAtraccion) {
		this.idProducto = idProducto;
		this.setCostoTotal(atracciones);
		this.setDuracionTotal(atracciones);
		this.nombreProducto = nombreProducto;
		this.tipoAtraccion = tipoAtraccion;
	}

	// Constructor para Producto con Atracciones
	public Producto(int idProducto, double costo, double duracion, String nombreAtraccion,
			TipoAtraccion tipoDeAtraccion) {
		this.idProducto = idProducto;
		this.costoTotal = costo;
		this.duracionTotal = duracion;
		this.nombreProducto = nombreAtraccion;
		this.tipoAtraccion = tipoDeAtraccion;
	}

	public Producto() {
	}

	public int getIdProducto() {
		return this.idProducto;
	}

	public String getNombreProducto() {
		return this.nombreProducto;
	}

	public abstract ArrayList<String> getNombreAtracEnPromo();

	// Set el precio SIN el descuento
	public void setCostoTotal(ArrayList<Atracciones> atrIncluidas) {
		double costo = 0;
		for (int i = 0; i < atrIncluidas.size(); i++) {
			costo += atrIncluidas.get(i).getCostoAtraccion();
		}
		this.costoTotal = costo;
	}

	// Obtener el precio SIN descuento
	public Double getCostoTotal() {
		return this.costoTotal;
	}

	// Obtenga precio con descuento
	public abstract double getPrecioDescuento();

	// Set el tiempo total
	public void setDuracionTotal(ArrayList<Atracciones> atrIncluidas) {
		double tiempo = 0;
		for (int i = 0; i < atrIncluidas.size(); i++) {
			tiempo += atrIncluidas.get(i).getDuracionAtraccion();
		}
		this.duracionTotal = tiempo;
	}

	public abstract String getTipoPromo();

	// descontar cupo
	public abstract void descontarCupoProducto();

	// Obtener duracion total
	public Double getDuracionTotal() {
		return this.duracionTotal;
	}

	// obtenertipoAtraccion
	public TipoAtraccion getTipoDeAtraccion() {
		return this.tipoAtraccion;
	}

	// chequea si tiene cupo la promo o la atraccion
	public boolean tieneCupo() {
		boolean hayCupo = true;

		// si es promo chequea cada una de las atracciones que la componen
		if (this.esPromo()) {
			ArrayList<Atracciones> atrac = this.getAtraccionesPromo();
			for (int i = 0; i < atrac.size(); i++) {
				if (!atrac.get(i).tieneCupo())
					return false;
			}
		} // SI es atraccion se fija solo en esa
		else {
			if (!this.tieneCupo())
				return false;
		}

		return hayCupo;
	}

	// devuelve si es promo o no
	public abstract boolean esPromo();

	// si es promo obtiene las atracciones que incluye, si es atraccion no hace nada
	public abstract ArrayList<Atracciones> getAtraccionesPromo();

	// si es atraccion obtiene a ella misma, si es promo no hace nada
	public abstract Atracciones getAtraccion();

	public void setNombreProducto(String nombreProd) {
		this.nombreProducto = nombreProd;
	}

	public void setTipoAtraccion(TipoAtraccion tipo) {
		this.tipoAtraccion = tipo;
	}

	// chequea si la atraccion ya fue comprada
	public boolean noFueComprado(ArrayList<Integer> atrCompradas) {
		boolean noEncontrado = true;

		if (this.esPromo()) {
			ArrayList<Atracciones> atrIncluidas = this.getAtraccionesPromo();

			// if itinerario tiene alguna de las atracciones de la promo

			for (int i = 0; i < atrIncluidas.size(); i++) {
				if (atrCompradas.contains(atrIncluidas.get(i).getIdProducto()))
					return false;
			}
		} // SI no es promo se fija si contiene el nombre de la atraccion simple
		else {
			for (int i = 0; i < atrCompradas.size(); i++) {
				if (atrCompradas.get(i).equals(this.idProducto))
					return false;
			}
		}

		return noEncontrado;
	}

	public void agregarIdCompra(ArrayList<Integer> atrCompradasId) {
		if (this.esPromo()) {
			ArrayList<Atracciones> atrIncluidas = this.getAtraccionesPromo();
			for (int i = 0; i < atrIncluidas.size(); i++) {
				atrCompradasId.add(atrIncluidas.get(i).getIdProducto());
			}
		} else {
			atrCompradasId.add(this.getIdProducto());
		}

	}
}