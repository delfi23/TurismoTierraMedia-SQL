package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import dao.AtraccionesDAO;
import dao.DAOFactory;
import dao.ItinerarioDAO;
import dao.PromocionesDAO;
import dao.UserDAO;
import turismotierramedia.Atracciones;
import turismotierramedia.Itinerario;
import turismotierramedia.Producto;
import turismotierramedia.Usuario;

public class PruebaCompraProductos {

	UserDAO userDao;
	AtraccionesDAO atrDao;
	ItinerarioDAO itinDao;
	PromocionesDAO promoDao;
	List<Itinerario> listaItin;
	LinkedList<Atracciones> itinerario;

	@Before
	public void setUp() {
		System.out.println("ACORDATE DE DESCONECTAR LA DB TEST Y RECONECTAR LA ORIGINAL");
		userDao = DAOFactory.getUserDAO();
		atrDao = DAOFactory.getAtraccionesDAO();
		itinDao = DAOFactory.getItinerariosDAO();
		promoDao = DAOFactory.getPromocionesDAO();
		listaItin = new LinkedList<Itinerario>();
		itinerario = new LinkedList<Atracciones>();
	}

	@Test
	public void CompraAtraccionesTest() {
		Usuario sebas = userDao.findByName("Sebastian");
		Usuario sebastian = userDao.findByName("Sebastian");
		List<Atracciones> todasAtr = atrDao.findAll();

		// si el gusto de Sebas coincide con el tipo de atraccion agrega a la lista
		for (Atracciones atr : todasAtr) {
			if (atr.getTipoDeAtraccion() == sebas.getPreferencia()) {
				itinerario.add(atr);
			}
		}
		for (Atracciones i : itinerario) {
			Itinerario itinNuevo = new Itinerario(sebas.getIdUsuario(), i.getIdProducto(), 0);
			itinDao.insert(itinNuevo);
			listaItin.add(itinNuevo);
			sebas.descontarDineroDisponible(i.getCostoAtraccion());
			sebas.descontarTiempoDisponible(i.getDuracionAtraccion());
			userDao.update(sebas);
			assertEquals(sebas.getDineroDisponible(),
					userDao.findByName(sebas.getNombreDeUsuario()).getDineroDisponible(), 0.01);
			assertEquals(sebas.getTiempoDisponible(),
					userDao.findByName(sebas.getNombreDeUsuario()).getTiempoDisponible(), 0.01);
		}

	}

	@Test
	public void CompraPromoAxBTest() {
		List<Producto> promos = promoDao.findAll();
		Usuario delfi = userDao.findByName("Delfina");
		LinkedList<Producto> compra = new LinkedList<Producto>();
		for (Producto promo : promos) {
			if (promo.getTipoPromo().equalsIgnoreCase("AxB")) {
				compra.add(promo);
				Itinerario itinNuevo = new Itinerario(delfi.getIdUsuario(), promo.getIdProducto(), 0);
				listaItin.add(itinNuevo);
				delfi.descontarDineroDisponible(promo.getCostoTotal());
				delfi.descontarTiempoDisponible(promo.getDuracionTotal());
				userDao.update(delfi);
				assertEquals(delfi.getDineroDisponible(),
						userDao.findByName(delfi.getNombreDeUsuario()).getDineroDisponible(), 0.01);
				assertEquals(delfi.getTiempoDisponible(),
						userDao.findByName(delfi.getNombreDeUsuario()).getTiempoDisponible(), 0.01);
			}
		}
	}
	
	@Test
	public void CompraPromoPorcentualTest() {
		List<Producto> promos = promoDao.findAll();
		Usuario diego = userDao.findByName("Diego");
		LinkedList<Producto> compra = new LinkedList<Producto>();
		for (Producto promo : promos) {
			if (promo.getTipoPromo().equalsIgnoreCase("Por")) {
				compra.add(promo);
				Itinerario itinNuevo = new Itinerario(diego.getIdUsuario(), promo.getIdProducto(), 0);
				listaItin.add(itinNuevo);
				diego.descontarDineroDisponible(promo.getCostoTotal());
				diego.descontarTiempoDisponible(promo.getDuracionTotal());
				userDao.update(diego);
				assertEquals(diego.getDineroDisponible(),
						userDao.findByName(diego.getNombreDeUsuario()).getDineroDisponible(), 0.01);
				assertEquals(diego.getTiempoDisponible(),
						userDao.findByName(diego.getNombreDeUsuario()).getTiempoDisponible(), 0.01);
			}
		}
	}
	
	@Test
	public void CompraPromoAbsolutaTest() {
		List<Producto> promos = promoDao.findAll();
		Usuario angela = userDao.findByName("Angela");
		LinkedList<Producto> compra = new LinkedList<Producto>();
		for (Producto promo : promos) {
			if (promo.getTipoPromo().equalsIgnoreCase("Abs")) {
				compra.add(promo);
				Itinerario itinNuevo = new Itinerario(angela.getIdUsuario(), promo.getIdProducto(), 0);
				listaItin.add(itinNuevo);
				angela.descontarDineroDisponible(promo.getCostoTotal());
				angela.descontarTiempoDisponible(promo.getDuracionTotal());
				userDao.update(angela);
				assertEquals(angela.getDineroDisponible(),
						userDao.findByName(angela.getNombreDeUsuario()).getDineroDisponible(), 0.01);
				assertEquals(angela.getTiempoDisponible(),
						userDao.findByName(angela.getNombreDeUsuario()).getTiempoDisponible(), 0.01);
			}
		}

	}

}
