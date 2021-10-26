package turismotierramedia;

import java.util.ArrayList;

public class PromoPorcentaje extends Producto {
	private double porcentajeDescuento;
	private ArrayList<Atracciones>atracciones;

	
	public PromoPorcentaje(int idProducto,ArrayList<Atracciones> atracciones, double Porcent, String nombre,
			TipoAtraccion tipoAtraccion) {
		super(idProducto,atracciones, nombre, tipoAtraccion);
		this.atracciones = atracciones;
		this.setPorcentajeDescuento(Porcent);
	}

	public void setPorcentajeDescuento(double porcentaje) {
		this.porcentajeDescuento = porcentaje;
	}

	
	//consulta su ID
		public int getIdProducto() {
			return super.getIdProducto();
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
		for (int i = 0; i < this.getAtraccionesPromo().size(); i++) {
			this.getAtraccionesPromo().get(i).descontarCupoAtraccion();
		}
	}

	// obtener nombre atracciones
	@Override
	public ArrayList<String> getNombreAtracEnPromo() {
		ArrayList<String> nombres = new ArrayList<String>();
		for (int i = 0; i < this.getAtraccionesPromo().size(); i++) {
			nombres.add(this.getAtraccionesPromo().get(i).getNombreAtraccion());
		}
		return nombres;
	}

	@Override
	public boolean esPromo() {
		return true;
	}

	@Override
	public ArrayList<Atracciones> getAtraccionesPromo() {
		return this.atracciones;
	}

	@Override
	public Atracciones getAtraccion() {
		return null;
	}

}