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
		
		// Lista para guardar los nombres de los productos comprados
		ArrayList<String> compra = new ArrayList<String>();
		
		// Lista con los nombres de las atracciones compradas
		ArrayList<String> atrCompradas = new ArrayList<String>();

		// EMPIEZA A RECORRER LA LISTA DE SUGERENCIAS
		for (Producto producto : sugerencias) {

			// SI PUDE COMPRAR, NO LA COMPRO AUN Y TIENE CUPO SUGIERE
			
			// >>> Aca hay que modificar noEstaEnItinerario (ahora es compra distinto tipo variable OJO)  y tiene Cupo
			//if (user.puedeComprar(producto) && producto.noEstaEnItinerario(itinerario) && producto.tieneCupo()) {
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

				if (opt.equals("S") || opt2.equals("s")) {

					System.out.println(">>> GRACIAS POR SU COMPRA ");
					System.out.println("-----------------------------------------");
					System.out.println();

					//>>> NO FUNCIONA
					// descuenta CUPO en atracciones
					producto.descontarCupoProducto();
					
					/*
					if(producto.esPromo()) {
						ArrayList<Atracciones> atrIncluidas = producto.getAtraccionesPromo();
						for(int i = 0; i< atrIncluidas.size(); i++) {
							atrCon.update(atrIncluidas.get(i));
						}
					}else {
						atrCon.update(producto.getAtraccion());
					}
					*/

					// Descuenta tiempo y dinero en usuario
					user.descontarDineroDisponible(producto.getPrecioDescuento());
					user.descontarTiempoDisponible(producto.getDuracionTotal());
					
					//>>> NO ANDA este update tampoco
					usercon.update(user);

					// contadores para totalizar
					dineroTotal += producto.getPrecioDescuento();
					tiempoTotal += producto.getDuracionTotal();
					
					// agrega el producto comprado a la Lista de compras					
					compra.add(producto.getNombreProducto());
					
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
			
		} // CIERRA EL IF SI TIENE DINERO
		
		// Aca ya termino de comprar --> insert todo a itinerario
		Itinerario itin= new Itinerario(user.getNombreDeUsuario(), compra, dineroTotal, tiempoTotal);
		ItinerarioDAO itDB = new ItinerarioDAO();
		itDB.insert(itin);
	}

} // TERMINA FOR
