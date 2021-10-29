package turismotierramedia;

import java.util.ArrayList;

public class PromoAbsoluta extends Producto {
	private Double precioFinal;
	private ArrayList<Atracciones> atracciones;
	private String tipoPromo;

	public PromoAbsoluta(int idPromo, ArrayList<Atracciones> atracciones, Double precioFinal, String nombre,
			TipoAtraccion tipoAtraccion) {
		super(idPromo, atracciones, nombre, tipoAtraccion);
		this.setDescuentoAbsoluto(precioFinal);
		this.atracciones = atracciones;
		this.tipoPromo = "Abs";

	}

	private void setDescuentoAbsoluto(Double precio) {
		this.precioFinal = precio;
	}

	// consulta su ID
	public int getIdProducto() {
		return super.getIdProducto();
	}

	// Obtener precio CON descuento
	@Override
	public double getPrecioDescuento() {
		return this.precioFinal;
	}

	// Descuento un lugar al cupo de las atracciones que incluye esta promo
	@Override
	public void descontarCupoProducto() {
		for (int i = 0; i < this.getAtraccionesPromo().size(); i++) {
			this.getAtraccionesPromo().get(i).descontarCupoAtraccion();
		}
	}

	// Obtener el nombre de las atracciones
	@Override
	public ArrayList<String> getNombreAtracEnPromo() {
		ArrayList<String> nombres = new ArrayList<String>();
		for (int i = 0; i < this.getAtraccionesPromo().size(); i++) {
			nombres.add(this.getAtraccionesPromo().get(i).getNombreAtraccion());
		}
		return nombres;
	}

	public String getTipoPromo() {
		return this.tipoPromo;
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