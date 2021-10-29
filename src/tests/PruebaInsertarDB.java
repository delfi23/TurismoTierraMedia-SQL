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
import turismotierramedia.Itinerario;
import turismotierramedia.Producto;
import turismotierramedia.PromoAbsoluta;
import turismotierramedia.PromoAxB;
import turismotierramedia.PromoPorcentaje;
import turismotierramedia.TipoAtraccion;
import turismotierramedia.Usuario;

public class PruebaInsertarDB {

	UserDAO userDao;
	AtraccionesDAO atraccionesDao;
	PromocionesDAO promoDao;
	ItinerarioDAO itinerarioDao;

	// declaro atracciones para test de atracciones

	Atracciones nuevaAtraccion1;
	Atracciones nuevaAtraccion2;
	Atracciones nuevaAtraccion3;

	ArrayList<Atracciones> listaAtracciones;

	@Before
	public void setUp() {
		userDao = DAOFactory.getUserDAO();
		atraccionesDao = DAOFactory.getAtraccionesDAO();
		promoDao = DAOFactory.getPromocionesDAO();
		itinerarioDao = DAOFactory.getItinerariosDAO();
		nuevaAtraccion1 = new Atracciones(0, "Nueva Atraccion1", 5, 5, 10, TipoAtraccion.AVENTURA);
		nuevaAtraccion2 = new Atracciones(0, "Nueva Atraccion2", 6, 4, 10, TipoAtraccion.AVENTURA);
		nuevaAtraccion3 = new Atracciones(0, "Nueva Atraccion3", 7, 3, 10, TipoAtraccion.AVENTURA);
		listaAtracciones = new ArrayList<Atracciones>();

	}

	@Test
	public void InsertarYEliminarUsuarioTest() {
		// cuento la cantidad de atracciones y agrego una para ingresar el id
		// correspondiente
		Usuario Emilio = new Usuario(0, "Emilio", 50, 10, TipoAtraccion.AVENTURA);
		userDao.insert(Emilio);
		assertEquals(Emilio.getNombreDeUsuario(), userDao.findByName("Emilio").getNombreDeUsuario());
		assertEquals(Emilio.getDineroDisponible(), userDao.findByName("Emilio").getDineroDisponible(), 0.001);
		assertEquals(Emilio.getTiempoDisponible(), userDao.findByName("Emilio").getTiempoDisponible(), 0.001);
		assertEquals(Emilio.getPreferencia(), userDao.findByName("Emilio").getPreferencia());
		userDao.delete(Emilio);

	}

	
	@Test
	public void InsertarYEliminarAtraccionesTest() {

		atraccionesDao.insert(nuevaAtraccion1);
		assertEquals(nuevaAtraccion1.getNombreAtraccion(),
				atraccionesDao.findByName("Nueva Atraccion1").getNombreAtraccion());
		assertEquals(nuevaAtraccion1.getCostoAtraccion(),
				atraccionesDao.findByName("Nueva Atraccion1").getCostoAtraccion(), 0.01);
		assertEquals(nuevaAtraccion1.getDuracionTotal(),
				atraccionesDao.findByName("Nueva Atraccion1").getDuracionTotal(), 0.001);
		assertEquals(nuevaAtraccion1.getCupoPersonas(), atraccionesDao.findByName("Nueva Atraccion1").getCupoPersonas(),
				0.001);
		assertEquals(nuevaAtraccion1.getTipoDeAtraccion(),
				atraccionesDao.findByName("Nueva Atraccion1").getTipoDeAtraccion());
		atraccionesDao.delete(nuevaAtraccion1);

	}
	

	@Test
	public void InsertarYEliminarItinerarioTest() {

		Usuario Emilio = new Usuario(0, "Emilio", 50, 10, TipoAtraccion.AVENTURA);
		userDao.insert(Emilio);
		
		Itinerario itinPrueba = new Itinerario(Emilio.getIdUsuario(), nuevaAtraccion1.getIdProducto(), 0);
		itinerarioDao.insert(itinPrueba);
		
		userDao.delete(Emilio);
		itinerarioDao.delete(itinPrueba);
		

	}
	
	
	@Test
	public void InsertarYEliminarPromoPorTest() {

		atraccionesDao.insert(nuevaAtraccion1);
		atraccionesDao.insert(nuevaAtraccion2);
		atraccionesDao.insert(nuevaAtraccion3);
		
		listaAtracciones.add(nuevaAtraccion1);
		listaAtracciones.add(nuevaAtraccion2);
		
		PromoPorcentaje promoPor = new PromoPorcentaje(100, listaAtracciones, 20.0, "PromoPor", TipoAtraccion.AVENTURA);
		promoDao.insert(promoPor);
		
		Producto producto = promoDao.findByName("PromoPor");
		assertEquals(promoPor.getNombreProducto(),
				producto.getNombreProducto());
		assertEquals(promoPor.getAtraccionesPromo().get(0).getNombreAtraccion(),
				producto.getAtraccionesPromo().get(0).getNombreAtraccion());
		assertEquals(promoPor.getAtraccionesPromo().get(1).getNombreAtraccion(),
				producto.getAtraccionesPromo().get(1).getNombreAtraccion());
		assertEquals(promoPor.getPorcentajeDescuento(),
				(((producto.getPrecioDescuento()-producto.getCostoTotal())*(-1))/producto.getCostoTotal()), 0.01);
		assertEquals(promoPor.getTipoDeAtraccion().toString(), producto.getTipoDeAtraccion().toString());
		
		atraccionesDao.delete(nuevaAtraccion1);
		atraccionesDao.delete(nuevaAtraccion2);
		
		promoDao.delete(promoPor);
	}
	
	@Test
	public void InsertarYEliminarPromoAbsTest() {

		atraccionesDao.insert(nuevaAtraccion1);
		atraccionesDao.insert(nuevaAtraccion2);
		
		listaAtracciones.add(nuevaAtraccion1);
		listaAtracciones.add(nuevaAtraccion2);
		
		PromoAbsoluta promoAbs = new PromoAbsoluta(100, listaAtracciones, 15.0, "PromoAbs", TipoAtraccion.AVENTURA);
		promoDao.insert(promoAbs);
		
		Producto producto = promoDao.findByName("PromoAbs");
		assertEquals(promoAbs.getNombreProducto(),
				producto.getNombreProducto());
		assertEquals(promoAbs.getAtraccionesPromo().get(0).getNombreAtraccion(),
				producto.getAtraccionesPromo().get(0).getNombreAtraccion());
		assertEquals(promoAbs.getAtraccionesPromo().get(1).getNombreAtraccion(),
				producto.getAtraccionesPromo().get(1).getNombreAtraccion());
		assertEquals(promoAbs.getPrecioDescuento(), producto.getPrecioDescuento(), 0.01);
		assertEquals(promoAbs.getTipoDeAtraccion().toString(), producto.getTipoDeAtraccion().toString());
		
		atraccionesDao.delete(nuevaAtraccion1);
		atraccionesDao.delete(nuevaAtraccion2);
		
		promoDao.delete(promoAbs);
	}
	
	@Test
	public void InsertarYEliminarPromoAxBTest() {

		atraccionesDao.insert(nuevaAtraccion1);
		atraccionesDao.insert(nuevaAtraccion2);
		atraccionesDao.insert(nuevaAtraccion3);
		
		listaAtracciones.add(nuevaAtraccion1);
		listaAtracciones.add(nuevaAtraccion2);
		
		PromoAxB promoAxB = new PromoAxB(100, listaAtracciones, nuevaAtraccion3, "PromoAxB", TipoAtraccion.AVENTURA);
		promoDao.insert(promoAxB);
		
		Producto producto = promoDao.findByName("PromoAxB");
		assertEquals(promoAxB.getNombreProducto(),
				producto.getNombreProducto());
		assertEquals(promoAxB.getAtraccionesPromo().get(0).getNombreAtraccion(),
				producto.getAtraccionesPromo().get(0).getNombreAtraccion());
		assertEquals(promoAxB.getAtraccionesPromo().get(1).getNombreAtraccion(),
				producto.getAtraccionesPromo().get(1).getNombreAtraccion());
		assertEquals(promoAxB.getAtraccionGratuita().getNombreAtraccion(),
				producto.getAtraccionesPromo().get(2).getNombreAtraccion());
		assertEquals(promoAxB.getPrecioDescuento(), producto.getPrecioDescuento(), 0.01);
		assertEquals(promoAxB.getTipoDeAtraccion().toString(), producto.getTipoDeAtraccion().toString());
		
		atraccionesDao.delete(nuevaAtraccion1);
		atraccionesDao.delete(nuevaAtraccion2);
		atraccionesDao.delete(nuevaAtraccion3);
		
		promoDao.delete(promoAxB);
	}
	
}
