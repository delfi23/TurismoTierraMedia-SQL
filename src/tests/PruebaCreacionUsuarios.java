package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import turismotierramedia.TipoAtraccion;
import turismotierramedia.TipoAtraccion;
import turismotierramedia.Usuario;

@SuppressWarnings("unused")
public class PruebaCreacionUsuarios {

	@Test
	public void creacionDeUnUsuario() {
		
		Usuario angela = new Usuario("Angela", 1000, 12, TipoAtraccion.AVENTURA);
		assertEquals("Angela", angela.getNombreDeUsuario());
		assertEquals(1000, angela.getDineroDisponible(),0.01);
		assertEquals(12, angela.getTiempoDisponible(), 0.01);
		assertEquals("AVENTURA", angela.getPreferencia().toString());
		angela.descontarDineroDisponible(500);
		assertEquals(500, angela.getDineroDisponible(),0.01);
		angela.descontarDineroDisponible(500);
		assertEquals(0, angela.getDineroDisponible(),0.01);
		
		Usuario delfi = new Usuario("Delfi", 1500, 8, TipoAtraccion.DEGUSTACION);
		assertEquals("Delfi", delfi.getNombreDeUsuario());
		assertEquals(1500, delfi.getDineroDisponible(),0.01);
		assertEquals(8, delfi.getTiempoDisponible(), 0.01);
		assertEquals("DEGUSTACION", delfi.getPreferencia().toString());
		
		Usuario jere = new Usuario("Jeremias", 500, 6, TipoAtraccion.PAISAJE);
		assertEquals("Jeremias", jere.getNombreDeUsuario());
		assertEquals(500, jere.getDineroDisponible(),0.01);
		assertEquals(6, jere.getTiempoDisponible(), 0.01);
		assertEquals("PAISAJE", jere.getPreferencia().toString());
	}
	
	
		
	
	
	
	

}
