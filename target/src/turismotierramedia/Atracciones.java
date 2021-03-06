package turismotierramedia;

import java.util.ArrayList;

import turismotierramedia.*;

public class Atracciones extends Producto {
	private int cupoPersonas;

	public Atracciones(int idProducto,String nombreAtraccion, double costoAtraccion, double duracionAtraccion, int cupoPersonas,
			TipoAtraccion tipoDeAtraccion) {
		super(idProducto,costoAtraccion, duracionAtraccion, nombreAtraccion, tipoDeAtraccion);
		this.cupoPersonas = cupoPersonas;
	}

	public Atracciones() {
		super();
	}

	//informa el id de Atraccion
	public int getIdProducto() {
		return super.getIdProducto();
	}
	
	// informa el nombre de la atraccion
	public String getNombreAtraccion() {
		return super.getNombreProducto();
	}

	// informa el costo de la atraccion.
	public double getCostoAtraccion() {
		return super.getCostoTotal();
	}

	// informa la duracion de la atraccion.
	public double getDuracionAtraccion() {
		return super.getDuracionTotal();
	}

	// informa el cupo de personas que queda.
	public int getCupoPersonas() {
		return this.cupoPersonas;
	}

	public boolean tieneCupo() {
		return this.getCupoPersonas() > 0;
	}

	// Descuenta 1 persona al total
	public void descontarCupoAtraccion() {
		this.cupoPersonas -= 1;
	}

	// Descuenta 1 persona al total
	@Override
	public void descontarCupoProducto() {
		this.descontarCupoAtraccion();
	}

	// Para la lista producto
	@Override
	public double getPrecioDescuento() {
		return super.getCostoTotal();
	}

	@Override
	public ArrayList<String> getNombreAtracEnPromo() {
		return null;
	}

	@Override
	public boolean esPromo() {
		return false;
	}

	@Override
	public Atracciones getAtraccion() {
		return this;
	}

	@Override
	public ArrayList<Atracciones> getAtraccionesPromo() {
		return null;
	}

	@Override
	public String getTipoPromo() {
		return null;
	}

}