package turismotierramedia;

import java.util.Comparator;

public class ProductosOrdenados implements Comparator<Producto> {

	@Override
	public int compare(Producto prod1, Producto prod2) {

		if (prod1.getCostoTotal() != prod2.getCostoTotal()) {
			return (prod1.getCostoTotal().compareTo(prod2.getCostoTotal())) * -1;
		} else {
			return (prod1.getDuracionTotal().compareTo(prod2.getDuracionTotal())) * -1;
		}
	}

}