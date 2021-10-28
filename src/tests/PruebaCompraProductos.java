package tests;

import static org.junit.Assert.*;

import org.junit.After;
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
import turismotierramedia.TipoAtraccion;
import turismotierramedia.Usuario;

public class PruebaCompraProductos {

	UserDAO userDao;
	AtraccionesDAO atrDao;
	PromocionesDAO promoDao;
	LinkedList<Atracciones> listAtrac;
	Usuario auxiliar;

	@Before
	public void setUp() {
		userDao = DAOFactory.getUserDAO();
		atrDao = DAOFactory.getAtraccionesDAO();
		promoDao = DAOFactory.getPromocionesDAO();
		listAtrac = new LinkedList<Atracciones>();
		auxiliar = new Usuario(0, "Prueba", 500, 24, TipoAtraccion.AVENTURA);
		userDao.insert(auxiliar);
	}

	@Test
	public void CompraAtraccionesTest() {	
		Usuario usuario = userDao.findByName("Prueba");
		List<Atracciones> todasAtr = atrDao.findAll();

		// si el gusto de Sebas coincide con el tipo de atraccion agrega a la lista
		for (Atracciones atr : todasAtr) {
			if (atr.getTipoDeAtraccion() == usuario.getPreferencia()) {
				listAtrac.add(atr);
			}
		}
		for (Atracciones i : listAtrac) {
			usuario.descontarDineroDisponible(i.getCostoAtraccion());
			usuario.descontarTiempoDisponible(i.getDuracionAtraccion());
			userDao.update(usuario);
			assertEquals(usuario.getDineroDisponible(),
					userDao.findByName(usuario.getNombreDeUsuario()).getDineroDisponible(), 0.01);
			assertEquals(usuario.getTiempoDisponible(),
					userDao.findByName(usuario.getNombreDeUsuario()).getTiempoDisponible(), 0.01);
			
		}

	}

	@Test
	public void CompraPromoAxBTest() {
		List<Producto> promos = promoDao.findAll();
		Usuario usuario = userDao.findByName("Prueba");
		for (Producto promo : promos) {
			if (promo.getTipoPromo().equalsIgnoreCase("AxB")) {
				usuario.descontarDineroDisponible(promo.getCostoTotal());
				usuario.descontarTiempoDisponible(promo.getDuracionTotal());
				userDao.update(usuario);
				assertEquals(usuario.getDineroDisponible(),
						userDao.findByName(usuario.getNombreDeUsuario()).getDineroDisponible(), 0.01);
				assertEquals(usuario.getTiempoDisponible(),
						userDao.findByName(usuario.getNombreDeUsuario()).getTiempoDisponible(), 0.01);
			}
		}
	}
	
	@Test
	public void CompraPromoPorcentualTest() {
		List<Producto> promos = promoDao.findAll();
		Usuario usuario = userDao.findByName("Prueba");
		for (Producto promo : promos) {
			if (promo.getTipoPromo().equalsIgnoreCase("Por")) {
				usuario.descontarDineroDisponible(promo.getCostoTotal());
				usuario.descontarTiempoDisponible(promo.getDuracionTotal());
				userDao.update(usuario);
				assertEquals(usuario.getDineroDisponible(),
						userDao.findByName(usuario.getNombreDeUsuario()).getDineroDisponible(), 0.01);
				assertEquals(usuario.getTiempoDisponible(),
						userDao.findByName(usuario.getNombreDeUsuario()).getTiempoDisponible(), 0.01);
			}
		}
	}
	
	@Test
	public void CompraPromoAbsolutaTest() {
		List<Producto> promos = promoDao.findAll();
		Usuario usuario = userDao.findByName("Prueba");
		for (Producto promo : promos) {
			if (promo.getTipoPromo().equalsIgnoreCase("Abs")) {
				usuario.descontarDineroDisponible(promo.getCostoTotal());
				usuario.descontarTiempoDisponible(promo.getDuracionTotal());
				userDao.update(usuario);
				assertEquals(usuario.getDineroDisponible(),
						userDao.findByName(usuario.getNombreDeUsuario()).getDineroDisponible(), 0.01);
				assertEquals(usuario.getTiempoDisponible(),
						userDao.findByName(usuario.getNombreDeUsuario()).getTiempoDisponible(), 0.01);
			}
		}

	}
	
	@After
	public void teardown() {
		userDao.delete(auxiliar);
	}

}
