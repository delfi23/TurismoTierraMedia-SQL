package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dao.AtraccionesDAO;
import dao.DAOFactory;
import dao.ItinerarioDAO;
import dao.PromocionesDAO;
import dao.UserDAO;
import turismotierramedia.Atracciones;
import turismotierramedia.Producto;
import turismotierramedia.PromoAbsoluta;
import turismotierramedia.PromoAxB;
import turismotierramedia.PromoPorcentaje;
import turismotierramedia.TipoAtraccion;
import turismotierramedia.Usuario;

public class PruebaInsertarDB {

	/*
	 * ANTES DE CORRER LOS TEST
	 * cambiar el atributo url en la clase Clase ConnectionProvider.
	 * Utilizar el url3 como argumento de la conexion,
	 *  asi no altera la DB original
	 */

	UserDAO userDao;
	AtraccionesDAO atraccionesDao;
	PromocionesDAO promoDao;
	ItinerarioDAO itinerarioDao;
	
	//declaro atracciones para test de atracciones
	
	Atracciones nuevaAtraccion1;
	Atracciones nuevaAtraccion2;
	Atracciones nuevaAtraccion3;
	
	//agrego las atracciones a la lista de atracciones para promos
	ArrayList<Atracciones> listaAtracciones;


	@Before
	public void setUp() {
		userDao = DAOFactory.getUserDAO();
		atraccionesDao=DAOFactory.getAtraccionesDAO();
		promoDao = DAOFactory.getPromocionesDAO();
		itinerarioDao = DAOFactory.getItinerariosDAO();
		nuevaAtraccion1 = new Atracciones (0,"Nueva Atraccion1", 5,5,10,TipoAtraccion.AVENTURA);
		nuevaAtraccion2 = new Atracciones (0,"Nueva Atraccion2", 6,4,10,TipoAtraccion.PAISAJE);
		nuevaAtraccion3 = new Atracciones (0,"Nueva Atraccion3", 7,3,10,TipoAtraccion.DEGUSTACION);
		listaAtracciones=new ArrayList<Atracciones>();

	}

	@Test
	public void InsertarYEliminarUsuarioTest() {
		//cuento la cantidad de atracciones y agrego una para ingresar el id correspondiente
		Usuario Emilio = new Usuario(0, "Emilio",50,10,TipoAtraccion.AVENTURA);
		userDao.insert(Emilio);
		assertEquals(Emilio.getNombreDeUsuario(),userDao.findByName("Emilio").getNombreDeUsuario());
		assertEquals(Emilio.getDineroDisponible(),userDao.findByName("Emilio").getDineroDisponible(),0.001);
		assertEquals(Emilio.getTiempoDisponible(),userDao.findByName("Emilio").getTiempoDisponible(),0.001);
		assertEquals(Emilio.getPreferencia(),userDao.findByName("Emilio").getPreferencia());
		userDao.delete(Emilio);
		
	}
	
	@Test
	public void InsertarYEliminarAtraccionesTest() {
		
		
		atraccionesDao.insert(nuevaAtraccion1);
		assertEquals(nuevaAtraccion1.getNombreAtraccion(),atraccionesDao.findByName("Nueva Atraccion1").getNombreAtraccion());
		assertEquals(nuevaAtraccion1.getCostoAtraccion(),atraccionesDao.findByName("Nueva Atraccion1").getCostoAtraccion(),0.01);
		assertEquals(nuevaAtraccion1.getDuracionTotal(),atraccionesDao.findByName("Nueva Atraccion1").getDuracionTotal(),0.001);
		assertEquals(nuevaAtraccion1.getCupoPersonas(),atraccionesDao.findByName("Nueva Atraccion1").getCupoPersonas(),0.001);
		assertEquals(nuevaAtraccion1.getTipoDeAtraccion(),atraccionesDao.findByName("Nueva Atraccion1").getTipoDeAtraccion());
		atraccionesDao.delete(nuevaAtraccion1);
		
	}
	@Test
	public void InsertarYEliminarPromocionesTest() {
		listaAtracciones.add(nuevaAtraccion1);
		listaAtracciones.add(nuevaAtraccion2);	
		
		//creo cada una de las promos
		PromoAxB nuevaPromoAxB = new PromoAxB (0,listaAtracciones, nuevaAtraccion3,"Promo uno",TipoAtraccion.AVENTURA);
		PromoAbsoluta nuevaPromoAbsoluta=new PromoAbsoluta(0,listaAtracciones,50.,"Promo dos",TipoAtraccion.DEGUSTACION);
		PromoPorcentaje nuevaPromoPorcentaje=new PromoPorcentaje(0,listaAtracciones, 15,"Promo tres",TipoAtraccion.PAISAJE);
		
		//las inserto en la DB
		promoDao.insert(nuevaPromoAxB);
		promoDao.insert(nuevaPromoAbsoluta);
		promoDao.insert(nuevaPromoPorcentaje);
		
		//Testeo que exista c/u con el nombre del objeto		
		assertEquals(nuevaPromoAxB.getNombreProducto(),promoDao.findByName("Promo uno").getNombreProducto());
		assertEquals(nuevaPromoAbsoluta.getNombreProducto(),promoDao.findByName("Promo dos").getNombreProducto());
		assertEquals(nuevaPromoPorcentaje.getNombreProducto(),promoDao.findByName("Promo tres").getNombreProducto());
		
		//Testeo que las atracciones se encuentren en las promos
		assertEquals(listaAtracciones,promoDao.findByName("Promo tres").getAtraccionesPromo());
		assertEquals(listaAtracciones,promoDao.findByName("Promo dos").getAtraccionesPromo());
		listaAtracciones.add(nuevaAtraccion3);
		assertEquals(listaAtracciones, promoDao.findByName("Promo uno").getAtraccionesPromo());
		
		System.out.println(nuevaPromoAxB.getNombreProducto());
		promoDao.delete(nuevaPromoPorcentaje);
		promoDao.delete(nuevaPromoAbsoluta);
		promoDao.delete(nuevaPromoAxB);
	
	}
}
