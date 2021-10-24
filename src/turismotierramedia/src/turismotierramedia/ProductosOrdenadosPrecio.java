package turismotierramedia;

import java.util.Comparator;

public class ProductosOrdenadosPrecio implements Comparator<Producto>{

	@Override
	public int compare (Producto prod1, Producto prod2) {
		
		return (prod1.getCostoTotal().compareTo(prod2.getCostoTotal()))*-1;
				
		
	}
	
}
