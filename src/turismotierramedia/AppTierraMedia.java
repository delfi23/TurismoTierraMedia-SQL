package turismotierramedia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import dao.*;

public class AppTierraMedia {

	public static void main(String[] args) {
		
		// Conecta a la base de datos atracciones
		AtraccionesDAO atrCon = DAOFactory.getAtraccionesDAO();
		
		// Conecta a la base de datos itinerarios
		ItinerarioDAO itinCon = DAOFactory.getItinerariosDAO();
		
		// Conecta a la Base de datos usuario
		UserDAO usercon = DAOFactory.getUserDAO();

		System.out.println("App Tierra Media 2.0");
		System.out.println("-------------------------");

		// Solicita el ingreso de usuario
		//>>>>>>>>HABRIA QUE CONTROLAR QUE INGRESE UN USUARIO VALIDO
		System.out.println("Ingrese su usuario : ");
		Scanner userIng = new Scanner(System.in);
		String opt = userIng.next();
		Usuario user = usercon.findByName(opt);

		System.out.println(user.getNombreDeUsuario() + " Bienvenido a Tierra Media");

		// Se crea lista de atracciones
		AtraccionesDAO atrDAO = DAOFactory.getAtraccionesDAO();
		List<Atracciones> atracciones2 = atrDAO.findAll();

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
		ArrayList<String> atrCompradas = new ArrayList<String>();
		
		// Busco en itinerario si el usuario ya realizo alguna compra
		Itinerario itin_user = itinCon.findByName(user.getNombreDeUsuario());
		
		// Si ya compro
		if(itin_user!=null) {
			ArrayList<String> prodComprados = itin_user.getNombreProd();
			
			// agrega a la lista atrCompradas las que ya compro para no sugerirlas de nuevo
			for(int i = 0; i < sugerencias.size(); i++) {
				for(int j=0; j < prodComprados.size(); j++) {
					
					if(sugerencias.get(i).getNombreProducto().equals(prodComprados.get(j))) {
						if(sugerencias.get(i).esPromo()) {
							ArrayList<String> nombresAtrIncluidas = sugerencias.get(i).getNombreAtracEnPromo();
							for(int k = 0; k< nombresAtrIncluidas.size(); k++) {
								atrCompradas.add(nombresAtrIncluidas.get(k));
							}
						}else {
							atrCompradas.add(sugerencias.get(i).getNombreProducto());
						}
					}					
				}
			}
		}
		
		
		
		

		// EMPIEZA A RECORRER LA LISTA DE SUGERENCIAS
		for (Producto producto : sugerencias) {

			// SI PUDE COMPRAR, NO LA COMPRO AUN Y TIENE CUPO SUGIERE
			
			// >>> Aca hay que modificar tiene Cupo
			if (user.puedeComprar(producto) && producto.noFueComprado(atrCompradas) && producto.tieneCupo()) {

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
					
					//actualiza cupo atracciones
					if(producto.esPromo()) {
						ArrayList<Atracciones> atrIncluidas = producto.getAtraccionesPromo();
						for(int i = 0; i< atrIncluidas.size(); i++) {
							atrCon.update(atrIncluidas.get(i));
						}
					}else {
						atrCon.update(producto.getAtraccion());
					}

					// Descuenta tiempo y dinero en usuario
					user.descontarDineroDisponible(producto.getPrecioDescuento());
					user.descontarTiempoDisponible(producto.getDuracionTotal());
					
					//actualiza usuario tiempo y dinero
					usercon.update(user);

					// contadores para totalizar
					dineroTotal += producto.getPrecioDescuento();
					tiempoTotal += producto.getDuracionTotal();
					
					// agrega el producto comprado a la Lista de compras					
					compra.add(producto.getNombreProducto());
					
					//agrega a la lista los nombres de las ATRACCIONES compradas
					if(producto.esPromo()) {
						ArrayList<String> nombresAtrIncluidas = producto.getNombreAtracEnPromo();
						for(int i = 0; i< nombresAtrIncluidas.size(); i++) {
							atrCompradas.add(nombresAtrIncluidas.get(i));
						}
					}else {
						atrCompradas.add(producto.getNombreProducto());
					}
				}
			} // CIERRA EL IF
			
		} // TERMINA EL FOR
		
		// Aca ya termino de comprar --> insert todo a itinerario
		
		
		Itinerario itin= new Itinerario(user.getNombreDeUsuario(), compra, dineroTotal, tiempoTotal);
		
		if(itin_user==null) {
			// si compra por primera vez lo crea
			ItinerarioDAO itDB = new ItinerarioDAO();
			itDB.insert(itin);
		}else {
			// si ya existe en itinerario lo actualiza
			itinCon.update(itin);
		}

		System.out.println(">>>>>>TERMINANOS LAS SUGERENCIAS >>>> PARA MAS COMPRAS VUELVE A EJECUTAR SUGERENCIAS");
		
		System.out.println("Usted compr� los siguientes productos: ");
		for(int i=0; i<compra.size();i++) {
			System.out.println(compra.get(i));
		}
		System.out.println("A un precio total de: " + dineroTotal + " monedas.");
		System.out.println("En total la duraci�n es de: " + tiempoTotal + " horas.");
		
	} // Cierra el main
} 
