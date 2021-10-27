package turismotierramedia;

import java.util.ArrayList;
import java.util.List;

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

	public void agregarComprasPrevias(ArrayList<Integer> atrCompradasId, List<Producto> sugerencias) {
		// si es una atraccion agrega su ID
		if (this.getId_promocion() == 0) {
			atrCompradasId.add(this.getId_atraccion());

		} else {
			// si es una promo que obtenga los ID de las atracciones que incluye
			for (Producto producto : sugerencias) {
				if (this.getId_promocion().equals(producto.getIdProducto())) {
					producto.agregarIdCompra(atrCompradasId);
				}
			}
		}
	}

}
