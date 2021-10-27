package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import dao.AtraccionesDAO;
import dao.DAOFactory;
import dao.PromocionesDAO;
import turismotierramedia.Atracciones;
import turismotierramedia.TipoAtraccion;

public class PruebaCreacionAtracciones {

	@Test
	public void test() {
		Atracciones mordor = new Atracciones(1, "Mordor", 25, 3, 4, TipoAtraccion.AVENTURA);
		assertEquals("Mordor", mordor.getNombreAtraccion());
		assertEquals(25, mordor.getCostoAtraccion(), 0.01);
		assertEquals(3, mordor.getDuracionAtraccion(), 0.01);
		assertEquals(4, mordor.getCupoPersonas());
		assertEquals("AVENTURA", mordor.getTipoDeAtraccion().toString());
	}

	@Test
	public void testAtracciones() {
		AtraccionesDAO atr = DAOFactory.getAtraccionesDAO();
		System.out.println(atr.findAll());
		System.out.println(atr.findByName("Moria"));

		PromocionesDAO promo = DAOFactory.getPromocionesDAO();
		System.out.println(promo.findAll());
		System.out.println(promo.findByName("PACK TRIPLE PAISAJE"));
	}

}
