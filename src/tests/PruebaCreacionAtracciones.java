package tests;

import static org.junit.Assert.*;

import org.junit.Test;


import turismotierramedia.Atracciones;
import turismotierramedia.TipoAtraccion;

public class PruebaCreacionAtracciones {

	@Test
	public void test() {
		Atracciones mordor = new Atracciones("Mordor", 25, 3, 4, TipoAtraccion.AVENTURA);
		assertEquals("Mordor", mordor.getNombreAtraccion());
		assertEquals(25, mordor.getCostoAtraccion(),0.01);
		assertEquals(3, mordor.getDuracionAtraccion(), 0.01);
		assertEquals(4, mordor.getCupoPersonas());
		assertEquals("AVENTURA", mordor.getTipoDeAtraccion().toString());
		}

}
