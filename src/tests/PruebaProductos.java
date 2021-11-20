package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import dao.AtraccionesDAO;
import dao.DAOFactory;
import dao.PromocionesDAO;
import turismotierramedia.Atracciones;
import turismotierramedia.Producto;
import turismotierramedia.TipoAtraccion;

public class PruebaProductos {

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
		AtraccionesDAO atrConn = DAOFactory.getAtraccionesDAO();

		List<Atracciones> atracciones = atrConn.findAll();

		assertEquals("MORIA", atracciones.get(0).getNombreAtraccion());
		assertEquals("MINAS TIRITH", atracciones.get(1).getNombreAtraccion());
		assertEquals("LA COMARCA", atracciones.get(2).getNombreAtraccion());
		assertEquals("MORDOR", atracciones.get(3).getNombreAtraccion());
		assertEquals("ABISMO DE HELM", atracciones.get(4).getNombreAtraccion());
		assertEquals("LOTHLORIEN", atracciones.get(5).getNombreAtraccion());
		assertEquals("EREBOR", atracciones.get(6).getNombreAtraccion());
		assertEquals("BOSQUE NEGRO", atracciones.get(7).getNombreAtraccion());
		assertEquals("MINAS MORGUL", atracciones.get(8).getNombreAtraccion());
		assertEquals("GONDOLIN", atracciones.get(9).getNombreAtraccion());
		assertEquals("MONTE GUNDABAD", atracciones.get(10).getNombreAtraccion());
		assertEquals("RIVENDEL", atracciones.get(11).getNombreAtraccion());
		assertEquals("TIRION", atracciones.get(12).getNombreAtraccion());
		assertEquals("EGLAREST", atracciones.get(13).getNombreAtraccion());

		// Imprime todas las atracciones
		for (Atracciones atr : atracciones)
			System.out.println(atr.getNombreAtraccion());
	}

	@Test
	public void testUnaAtraccion() {
		AtraccionesDAO atrConn = DAOFactory.getAtraccionesDAO();

		// Busca la atraccion de nombre MORIA
		Atracciones moria = atrConn.findByName("Moria");
		assertEquals(1, moria.getIdProducto());
		assertEquals("MORIA", moria.getNombreAtraccion());
		assertEquals(10.0, moria.getCostoAtraccion(), 0.01);
		assertEquals(2.0, moria.getDuracionAtraccion(), 0.01);

		// El cupo va cambiando a medida se compra
		// assertEquals(7, moria.getCupoPersonas());

		assertEquals("AVENTURA", moria.getTipoDeAtraccion().toString());
	}

	@Test
	public void testPromociones() {

		// Imprime todas las promos
		PromocionesDAO promoConn = DAOFactory.getPromocionesDAO();

		List<Producto> promociones = promoConn.findAll();

		assertEquals("PACK TRIPLE PAISAJE", promociones.get(0).getNombreProducto());
		assertEquals("PACK TRIPLE AVENTURA", promociones.get(1).getNombreProducto());
		assertEquals("PACK 2X1 DE PAISAJE", promociones.get(2).getNombreProducto());
		assertEquals("PACK 2X1 DEGUSTACION", promociones.get(3).getNombreProducto());
		assertEquals("PACK AVENTURA", promociones.get(4).getNombreProducto());
		assertEquals("PACK DOBLE AVENTURA", promociones.get(5).getNombreProducto());
		assertEquals("PACK AVENTURERO", promociones.get(6).getNombreProducto());
		assertEquals("PACK SIMPLE DEGUSTACION", promociones.get(7).getNombreProducto());
		assertEquals("PACK SIMPLE AVENTURA", promociones.get(8).getNombreProducto());
		assertEquals("PACK DEGUSTACION", promociones.get(9).getNombreProducto());
		assertEquals("PACK DOBLE PAISAJE", promociones.get(10).getNombreProducto());
		assertEquals("PACK LO MEJOR EN PAISAJE", promociones.get(11).getNombreProducto());

		for (Producto promo : promociones)
			System.out.println(promo.getNombreProducto());
	}

	@Test
	public void promocionAxBTest() {

		PromocionesDAO promoConn = DAOFactory.getPromocionesDAO();

		// Busca la promo PACK TRIPLE PAISAJE y compara
		Producto packTriplePaisaje = promoConn.findByName("PACK TRIPLE PAISAJE");
		assertEquals("AxB", packTriplePaisaje.getTipoPromo());
		assertEquals("PACK TRIPLE PAISAJE", packTriplePaisaje.getNombreProducto());
		assertEquals(4, packTriplePaisaje.getTipoDeAtraccion().toInt());

		assertEquals("PAISAJE", packTriplePaisaje.getTipoDeAtraccion().toString());

		// Obtengo las atracciones que contiene esa promo
		ArrayList<Atracciones> atrIncluidas = packTriplePaisaje.getAtraccionesPromo();
		assertEquals("MINAS TIRITH", atrIncluidas.get(0).getNombreAtraccion());
		assertEquals("ABISMO DE HELM", atrIncluidas.get(1).getNombreAtraccion());
		assertEquals("EREBOR", atrIncluidas.get(2).getNombreAtraccion());

		assertEquals(1, packTriplePaisaje.getIdProducto());
	}

	@Test
	public void promocionPorTest() {

		PromocionesDAO promoConn = DAOFactory.getPromocionesDAO();

		// Busca la promo PACK AVENTURA y compara
		Producto packAventura = promoConn.findByName("PACK AVENTURA");
		assertEquals("Por", packAventura.getTipoPromo());
		assertEquals("PACK AVENTURA", packAventura.getNombreProducto());
		assertEquals(1, packAventura.getTipoDeAtraccion().toInt());

		assertEquals("AVENTURA", packAventura.getTipoDeAtraccion().toString());

		// Obtengo las atracciones que contiene esa promo
		ArrayList<Atracciones> atrIncluidas = packAventura.getAtraccionesPromo();
		assertEquals("BOSQUE NEGRO", atrIncluidas.get(0).getNombreAtraccion());
		assertEquals("MORDOR", atrIncluidas.get(1).getNombreAtraccion());

		assertEquals(5, packAventura.getIdProducto());
	}

	@Test
	public void promocionAbsTest() {

		PromocionesDAO promoConn = DAOFactory.getPromocionesDAO();

		// Busca la promo PACK DEGUSTACION y compara
		Producto packDegustacion = promoConn.findByName("PACK DEGUSTACION");
		assertEquals("Abs", packDegustacion.getTipoPromo());
		assertEquals("PACK DEGUSTACION", packDegustacion.getNombreProducto());
		assertEquals(3, packDegustacion.getTipoDeAtraccion().toInt());

		assertEquals("DEGUSTACION", packDegustacion.getTipoDeAtraccion().toString());

		// Obtengo las atracciones que contiene esa promo
		ArrayList<Atracciones> atrIncluidas = packDegustacion.getAtraccionesPromo();
		assertEquals("LOTHLORIEN", atrIncluidas.get(0).getNombreAtraccion());
		assertEquals("LA COMARCA", atrIncluidas.get(1).getNombreAtraccion());

		assertEquals(10, packDegustacion.getIdProducto());
	}

}
