package turismotierramedia;

import java.util.ArrayList;

public class PromoPorcentaje extends Producto {
	private double porcentajeDescuento;
	private ArrayList<Atracciones>atracciones;

	public PromoPorcentaje(ArrayList<Atracciones> atracciones, double Porcent, String nombre,
			TipoAtraccion tipoAtraccion) {
		super(atracciones, nombre, tipoAtraccion);
		this.atracciones = atracciones;
		this.setPorcentajeDescuento(Porcent);
	}
	public PromoPorcentaje(ArrayList<Atracciones> atracciones, double Porcent, String nombre,
			String tipoAtraccion) {
		super(atracciones, nombre, tipoAtraccion);
		this.atracciones = atracciones;
		this.setPorcentajeDescuento(Porcent);
	}

	public void setPorcentajeDescuento(double porcentaje) {
		this.porcentajeDescuento = porcentaje;
	}

	// Obtener precio CON descuento
	@Override
	public double getPrecioDescuento() {
		return super.getCostoTotal() - (super.getCostoTotal() * this.getPorcentajeDescuento());
	}

	// Obtener porcentaje descuento
	public double getPorcentajeDescuento() {
		return this.porcentajeDescuento / 100;
	}

	// Descuenta un cupo a las atracciones incluidas
	@Override
	public void descontarCupoProducto() {
		for (int i = 0; i < this.getAtracciones().size(); i++) {
			this.getAtracciones().get(i).descontarCupoAtraccion();
		}
	}

	// obtener nombre atracciones
	@Override
	public ArrayList<String> getNombreAtracEnPromo() {
		ArrayList<String> nombres = new ArrayList<String>();
		for (int i = 0; i < this.getAtracciones().size(); i++) {
			nombres.add(this.getAtracciones().get(i).getNombreAtraccion());
		}
		return nombres;
	}

	@Override
	public boolean esPromo() {
		return true;
	}

	@Override
	public ArrayList<Atracciones> getAtraccionesPromo() {
		return this.getAtracciones();
	}

	@Override
	public Atracciones getAtraccion() {
		return null;
	}
	public ArrayList<Atracciones> getAtracciones(){
		return this.atracciones;
	}


}