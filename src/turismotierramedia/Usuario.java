package turismotierramedia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Usuario {

	private int idUsuario;
	private String nombre = " ";
	private double dineroDisponible;
	private double tiempoDisponible;
	private TipoAtraccion preferencia;

	public Usuario(int idUsuario, String nombre, double dineroDisponible, double tiempoDisponible,
			TipoAtraccion preferencia) {
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.dineroDisponible = dineroDisponible;
		this.tiempoDisponible = tiempoDisponible;
		this.preferencia = preferencia;
	}

	// devuelve el idUsuario
	public int getIdUsuario() {
		return this.idUsuario;
	}

	// devuelve el nombre de usuario
	public String getNombreDeUsuario() {
		return this.nombre;
	}

	// devuelve que atracciones le gusta
	// aca casteo como string el valor que toma del TipoDeAtraccion OJO
	// para que devuelva solamente el valor EJEMPLO "AVENTURA"

	public TipoAtraccion getPreferencia() {
		return this.preferencia;
	}

	// devuelve la cantidad de dinero que posee
	public double getDineroDisponible() {
		double dinero = Math.round(dineroDisponible * 100) / 100d;
		return dinero;
	}

	// actualiza el dinero disponible
	public void descontarDineroDisponible(double nuevoDinero) {
		this.dineroDisponible -= nuevoDinero;
	}

	// devuelve la cantidad de tiempo que posee.
	public double getTiempoDisponible() {
		return this.tiempoDisponible;
	}

	// actualiza el tiempo disponible
	public void descontarTiempoDisponible(double nuevoTiempo) {
		this.tiempoDisponible -= nuevoTiempo;
	}

	// Pregunta si tiene dinero y tiempo para comprar una atraccion

	public boolean puedeComprar(Producto producto) {
		return (this.getDineroDisponible() >= producto.getPrecioDescuento()
				&& this.getTiempoDisponible() >= producto.getDuracionTotal());
	}

	// ORDENAR PRODUCTOS POR PRECIO Y DURACION
	public static void ordenarProductos(List<Producto> sugerencias) {
		Collections.sort(sugerencias, new ProductosOrdenados());
	}

	// ORDENAR PRODUCTOS PONIEDO PRIMERO LOS DE SU PREFERENCIA
	public List<Producto> ordenarSegunPreferencia(LinkedList<Producto> productos) {

		// Primero ordena por Precio y Tiempo
		ordenarProductos(productos);

		List<Producto> sugerencias = new ArrayList<Producto>();
		List<Producto> queNoCoinciden = new ArrayList<Producto>();

		for (Producto ca : productos)
			if (ca.getTipoDeAtraccion() == this.getPreferencia())
				sugerencias.add(ca);
			else
				queNoCoinciden.add(ca);

		// AGREGA LOS PRODUCTOS QUE NO COINCIDEN AL FINAL DE LA LISTA
		sugerencias.addAll(queNoCoinciden);

		return sugerencias;

	}
}