package tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import dao.AtraccionesDAO;
import dao.DAOFactory;
import dao.PromocionesDAO;
import dao.UserDAO;
import turismotierramedia.Atracciones;
import turismotierramedia.Producto;
import turismotierramedia.TipoAtraccion;
import turismotierramedia.Usuario;

public class PruebaUsuarios {

	@Test
	public void creacionDeUnUsuario() {
		Usuario angela = new Usuario(1, "Angela", 1000, 12, TipoAtraccion.AVENTURA);
		assertEquals("Angela", angela.getNombreDeUsuario());
		assertEquals(1000, angela.getDineroDisponible(), 0.01);
		assertEquals(12, angela.getTiempoDisponible(), 0.01);
		assertEquals("AVENTURA", angela.getPreferencia().toString());
		angela.descontarDineroDisponible(500);
		assertEquals(500, angela.getDineroDisponible(), 0.01);
		angela.descontarDineroDisponible(500);
		assertEquals(0, angela.getDineroDisponible(), 0.01);

		Usuario delfi = new Usuario(2, "Delfi", 1500, 8, TipoAtraccion.DEGUSTACION);
		assertEquals("Delfi", delfi.getNombreDeUsuario());
		assertEquals(1500, delfi.getDineroDisponible(), 0.01);
		assertEquals(8, delfi.getTiempoDisponible(), 0.01);
		assertEquals("DEGUSTACION", delfi.getPreferencia().toString());

		Usuario jere = new Usuario(3, "Jeremias", 500, 6, TipoAtraccion.PAISAJE);
		assertEquals("Jeremias", jere.getNombreDeUsuario());
		assertEquals(500, jere.getDineroDisponible(), 0.01);
		assertEquals(6, jere.getTiempoDisponible(), 0.01);
		assertEquals("PAISAJE", jere.getPreferencia().toString());
	}

	@Test
	public void findAllUsuarioTest() {
		UserDAO userConn = DAOFactory.getUserDAO();

		List<Usuario> usuarios = userConn.findAll();

		assertEquals("Angela", usuarios.get(0).getNombreDeUsuario());
		assertEquals("Delfina", usuarios.get(1).getNombreDeUsuario());
		assertEquals("Sebastian", usuarios.get(2).getNombreDeUsuario());
		assertEquals("Jeremias", usuarios.get(3).getNombreDeUsuario());
		assertEquals("Tomas", usuarios.get(4).getNombreDeUsuario());
		assertEquals("Diego", usuarios.get(5).getNombreDeUsuario());

		// Imprime todos los usuarios
		for (Usuario usuario : usuarios)
			System.out.println(usuario.getNombreDeUsuario());

	}

	@Test
	public void findByNameUsuarioTest() {
		UserDAO userConn = DAOFactory.getUserDAO();

		// Busca el usuario de nombre Delfina
		Usuario usuario = userConn.findByName("Delfina");

		assertEquals("Delfina", usuario.getNombreDeUsuario());
		assertEquals("PAISAJE", usuario.getPreferencia().toString());
		assertEquals(2, usuario.getIdUsuario());

		// no prueba tiempo y dinero pq cambia a medida que compra

	}

	@Test(expected = Error.class)
	public void ingresaUsarioIncorrectoTest() {
		UserDAO userConn = DAOFactory.getUserDAO();
		assertNotNull(userConn.findByName("Alejandro"));
	}

	@Test(expected = Error.class)
	public void dineroCeroTest() {
		Usuario user = new Usuario(1, "Usuario", 0, 12, TipoAtraccion.AVENTURA);
		PromocionesDAO promoConn = DAOFactory.getPromocionesDAO();
		Producto producto = promoConn.findByName("PACK TRIPLE PAISAJE");
		assertNotNull(user.puedeComprar(producto));
	}

	@Test(expected = Error.class)
	public void tiempoCeroTest() {
		Usuario user = new Usuario(1, "Usuario", 20, 0, TipoAtraccion.AVENTURA);
		PromocionesDAO promoConn = DAOFactory.getPromocionesDAO();
		Producto producto = promoConn.findByName("PACK TRIPLE PAISAJE");
		assertNotNull(user.puedeComprar(producto));
	}
}
