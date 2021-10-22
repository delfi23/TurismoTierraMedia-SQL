package turismotierramedia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import dao.*;

public class AppTierraMedia {

	public static void main(String[] args) {

		// Conecta a la Base de datos
		UserDAO usercon = DAOFactory.getUserDAO();

		System.out.println("App Tierra Media");
		System.out.println("-------------------------");

		// Solicita el ingreso de usuario
		//>>>>>>>>HABRIA QUE CONTROLAR QUE INGRESE UN USUARIO VALIDO
		System.out.println("Ingrese su usuario : ");
		Scanner userIng = new Scanner(System.in);
		String opt = userIng.next();
		Usuario user = usercon.findByName(opt);

		
		
		System.out.println(user.getNombreDeUsuario() + " Bienvenido a Tierra Media");

		// Se crea lista de atracciones
		AtraccionesDAO atrDAO = new AtraccionesDAO();
		List<Atracciones> atracciones2 = atrDAO.findAll();

		// Crear Lista de Promociones
		PromocionesDAO proDAO = new PromocionesDAO();
		List<Producto> promociones = proDAO.findAll();

		// Se crea lista de Sugerencias
		LinkedList<Producto> sugerencias = new LinkedList<Producto>();

		// Agrego Promo y Atracc a sugerencias
		sugerencias.addAll(promociones);
		sugerencias.addAll(atracciones2);

		// -----ANGELA------TIENE QUE PASAR EL PRODUCTOSORDENAR

		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

		// PONE EN CERO LAS VARIABLES DE TIEMPO Y DINERO.
		double dineroTotal = 0;
		double tiempoTotal = 0;

		// SE CREA LISTA PARA GUARDAR ITINERARIO

		LinkedList<Atracciones> itinerario = new LinkedList<Atracciones>();

		// EMPIEZA A RECORRER LA LISTA DE SUGERENCIAS
		for (Producto producto : sugerencias) {

			// SI PUDE COMPRAR, NO LA COMPRO AUN Y TIENE CUPO SUGIERE
			if (user.puedeComprar(producto) && producto.noEstaEnItinerario(itinerario) && producto.tieneCupo()) {

				// IMPRIME POR CONSOLA SALDO Y TIEMPO DIPONIBLE
				System.out.println("Su saldo es: " + user.getDineroDisponible() + ". Su tiempo disponible es: "
						+ user.getTiempoDisponible());

				System.out.println(">>>--------------------------------------------<<<");
				System.out.println("Nuestra sugerencia es: " + producto.getNombreProducto());
				

				//Lista las atracciones incluidas en las Promos
				if(producto.esPromo()) {
					System.out.println("Que incluye las atracciones:");
					ArrayList<String> nombresAtrIncluidas = producto.getNombreAtracEnPromo();
					  for (int i = 0; i < nombresAtrIncluidas.size(); i++) {
					  System.out.println(nombresAtrIncluidas.get(i)); }
				}
				
				

				System.out.println("A un precio de " + producto.getPrecioDescuento() + " Monedas");
				System.out.println("La duracion en horas es : " + producto.getDuracionTotal());

				// Si no es una atraccion simple es promo y se ahorra monedas
				if (producto.getCostoTotal() != producto.getPrecioDescuento()) {
					double ahorro = Math.round((producto.getCostoTotal() - producto.getPrecioDescuento()) * 100) / 100d;
					System.out.println("Comprando este pack se ahorra un total de " + ahorro + " Monedas");
				}

				// Solicita si quiere comprar esa Atraccion
				Scanner opcion = new Scanner(System.in);
				System.out.println("Desea comprar esta oferta S/N");
				String opt2 = opcion.next();

				System.out.println(opt2);

				if (opt.equals("S") || opt2.equals("s")) {

					System.out.println(">>> GRACIAS POR SU COMPRA ");
					System.out.println("-----------------------------------------");
					System.out.println();

					// descuenta CUPO en atracciones
					producto.descontarCupoProducto();

					// Descuenta tiempo y dinero en usuario
					user.descontarDineroDisponible(producto.getPrecioDescuento());
					user.descontarTiempoDisponible(producto.getDuracionTotal());

					// contadores para totalizar
					dineroTotal += producto.getPrecioDescuento();
					tiempoTotal += producto.getDuracionTotal();

					
					// agrega al itinerario la compra
					//>>>>> aca hay que ver si dejamos asi 
					//para cuando implementemos lo que ya compro
					// o vemos de tirar todo no se!
					
					Itinerario compra= new Itinerario(user.getNombreDeUsuario(), producto.getNombreProducto());
					ItinerarioDAO itDB = new ItinerarioDAO();
					itDB.insert(compra);					
					
					if(producto.esPromo()) {
						ArrayList<Atracciones> atrac = producto.getAtraccionesPromo();
						for (int i = 0; i < atrac.size(); i++) {
							itinerario.add(atrac.get(i));}
					}else {
						Atracciones atrac = producto.getAtraccion();
						itinerario.add(atrac);
					}

				}
			} // CIERRA EL IF
			
		} // CIERRA EL IF SI TIENE DINERO
	}

	// Actualizar base usuarios tiempo y dinero
	
	
	
	//>>>>>>>>> aca falta mandar el array de "itinerario"
	//>>>>>>>>> a base de datos !
	
	// System.out.println(" ");
	// String archSalida = ("Itinerario" + user.getNombreDeUsuario() + ".out");
	// Sistema.escribirCompras(user.getNombreDeUsuario(), tiempoTotal, dineroTotal,
	// itinerario, archSalida);

} // TERMINA FOR
