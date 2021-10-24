package turismotierramedia;

public class Usuario {

	private String nombre = " ";
	private double dineroDisponible;
	private double tiempoDisponible;
	private TipoAtraccion preferencia;

	public Usuario(String nombre, double dineroDisponible, double tiempoDisponible, TipoAtraccion preferencia) {
		this.nombre = nombre;
		this.dineroDisponible = dineroDisponible;
		this.tiempoDisponible = tiempoDisponible;
		this.preferencia = preferencia;
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

	// pregunta si tiene tiempo disponible
	// ver si se implementa o no
	public boolean tieneTiempo() {
		return this.tiempoDisponible > 0;
	}

	// pregunta si tiene plata disponible
	// ver si se implementa o no
	public boolean tieneDinero() {
		return this.dineroDisponible > 0;
	}

	// Pregunta si tiene dinero y tiempo para comprar una atraccion

	public boolean puedeComprar(Producto producto) {
		return (this.getDineroDisponible() >= producto.getPrecioDescuento()
				&& this.getTiempoDisponible() >= producto.getDuracionTotal());
	}

	@Override
	public String toString() {
		return nombre + "," + dineroDisponible + "," + tiempoDisponible + "," + preferencia;
	}
}