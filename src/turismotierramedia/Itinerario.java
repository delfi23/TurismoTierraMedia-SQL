package turismotierramedia;

public class Itinerario {
	private String usuario;
	private String producto;
	
	public Itinerario(String usuario, String producto) {
		this.usuario = usuario;
		this.producto = producto;
	}

	public String getUsuario() {
		return usuario;
	}

	public String getProducto() {
		return producto;
	}
}
