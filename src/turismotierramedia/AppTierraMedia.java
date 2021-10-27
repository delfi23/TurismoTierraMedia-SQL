package turismotierramedia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import dao.*;

public class AppTierraMedia {

	public static void main(String[] args) {

		// Conecta a la base de datos atracciones
		AtraccionesDAO atrConn = DAOFactory.getAtraccionesDAO();

		// Conecta a la base de datos itinerarios
		ItinerarioDAO itinConn = DAOFactory.getItinerariosDAO();

		// Conecta a la Base de datos usuario
		UserDAO userConn = DAOFactory.getUserDAO();

		System.out.println("App Tierra Media 2.0");
		System.out.println("-------------------------");

		// Solicita el ingreso de usuario
		// >>>>>>>>HABRIA QUE CONTROLAR QUE INGRESE UN USUARIO VALIDO
		System.out.println("Ingrese su usuario : ");
		Scanner userIng = new Scanner(System.in);
		String opt = userIng.next();
		Usuario user = userConn.findByName(opt);

		System.out.println(user.getNombreDeUsuario() + " Bienvenido a Tierra Media");

		// Se crea lista de atracciones
		List<Atracciones> atracciones2 = atrConn.findAll();

		// Crear Lista de Promociones
		PromocionesDAO proDAO = DAOFactory.getPromocionesDAO();
		List<Producto> promociones = proDAO.findAll();

		// Se crea lista de Sugerencias
		LinkedList<Producto> productosFinales = new LinkedList<Producto>();

		// Agrego Promo y Atracc a sugerencias
		productosFinales.addAll(promociones);
		productosFinales.addAll(atracciones2);

		// Ordena por precio y tiempo
		Sistema.ordenarProductos(productosFinales);

		// Ordena dejando las preferencias primero
		List<Producto> sugerencias = Sistema.ordenarSegunPreferencia(productosFinales, user.getPreferencia());

		// PONE EN CERO LAS VARIABLES DE TIEMPO Y DINERO.
		double dineroTotal = 0;
		double tiempoTotal = 0;

		// Lista para guardar los nombres de los productos comprados
		ArrayList<String> compra = new ArrayList<String>();

		// Lista con los nombres de las atracciones compradas
		ArrayList<Integer> atrCompradasId = new ArrayList<Integer>();

		// Busca en itinerario si el usuario ya realizo alguna compra, devuelve Lista
		// con todos sus itinerarios
		List<Itinerario> itin_user = itinConn.findByUserId(user.getIdUsuario());

		// Si ya compro agrega los ID de sus atr compradas a la lista atrCompradasId
		if (itin_user != null) {
			for (Itinerario itin : itin_user)
				itin.agregarComprasPrevias(atrCompradasId, sugerencias);
		}

		// EMPIEZA A RECORRER LA LISTA DE SUGERENCIAS
		for (Producto producto : sugerencias) {

			// SI PUDE COMPRAR, NO LA COMPRO AUN Y TIENE CUPO SUGIERE

			if (user.puedeComprar(producto) && producto.noFueComprado(atrCompradasId) && producto.tieneCupo()) {

				// IMPRIME POR CONSOLA SALDO Y TIEMPO DIPONIBLE
				System.out.println("Su saldo es: " + user.getDineroDisponible() + ". Su tiempo disponible es: "
						+ user.getTiempoDisponible());

				System.out.println(">>>--------------------------------------------<<<");
				System.out.println("Nuestra sugerencia es: " + producto.getNombreProducto());

				// Lista las atracciones incluidas en las Promos
				if (producto.esPromo()) {
					System.out.println("Que incluye las atracciones:");
					ArrayList<String> nombresAtrIncluidas = producto.getNombreAtracEnPromo();

					for (String nombre : nombresAtrIncluidas)
						System.out.println(nombre);
				}

				System.out.println("A un precio de " + producto.getPrecioDescuento() + " Monedas");
				System.out.println("La duracion en horas es : " + producto.getDuracionTotal());

				// Si no es una atraccion simple es promo y se ahorra monedas
				if (producto.esPromo()) {
					double ahorro = Math.round((producto.getCostoTotal() - producto.getPrecioDescuento()) * 100) / 100d;
					System.out.println("Comprando este pack se ahorra un total de " + ahorro + " monedas");
				}

				// Solicita si quiere comprar esa Atraccion
				Scanner opcion = new Scanner(System.in);
				System.out.println("Desea comprar esta oferta S/N");

				String opt2 = opcion.next();
				System.out.println(opt2);

				if (opt2.equalsIgnoreCase("s")) {

					System.out.println(">>> GRACIAS POR SU COMPRA ");
					System.out.println("-----------------------------------------");
					System.out.println();

					// descuenta CUPO en atracciones
					producto.descontarCupoProducto();

					// Descuenta tiempo y dinero en usuario
					user.descontarDineroDisponible(producto.getPrecioDescuento());
					user.descontarTiempoDisponible(producto.getDuracionTotal());

					// actualiza usuario tiempo y dinero
					userConn.update(user);

					// contadores para totalizar
					dineroTotal += producto.getPrecioDescuento();
					tiempoTotal += producto.getDuracionTotal();

					// agrega el producto comprado a la Lista de compras
					compra.add(producto.getNombreProducto());

					// agrega a la lista los ID de las ATRACCIONES compradas
					producto.agregarIdCompra(atrCompradasId);

					// Agrega a la DB itinerario y actualiza el cupo en atracciones
					ItinerarioDAO itDB = new ItinerarioDAO();

					if (producto.esPromo()) {
						itDB.insert(new Itinerario(user.getIdUsuario(), 0, producto.getIdProducto()));

						ArrayList<Atracciones> atrIncluidas = producto.getAtraccionesPromo();
						for (Atracciones atr : atrIncluidas)
							atrConn.update(atr);

					} else {
						itDB.insert(new Itinerario(user.getIdUsuario(), producto.getIdProducto(), 0));

						atrConn.update(producto.getAtraccion());
					}

				}
			} // CIERRA EL IF

		} // TERMINA EL FOR

		System.out.println("--------------------------------------------------------------------------");
		System.out.println(">>>>>> TERMINANOS LAS SUGERENCIAS >>>> PARA MAS COMPRAS VUELVE A EJECUTAR.");
		System.out.println("--------------------------------------------------------------------------");
		System.out.println();
		System.out.println("Usted compró los siguientes productos: ");
		for (String nombreProd : compra)
			System.out.println(nombreProd);
		System.out.println();
		System.out.println("A un precio total de: " + dineroTotal + " monedas.");
		System.out.println("La duración total del recorrido es de: " + tiempoTotal + " horas.");
	} // Cierra el main
}
