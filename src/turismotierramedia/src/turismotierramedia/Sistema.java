package turismotierramedia;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Sistema {

	// Se crea la lista de Usuarios EN BASE DE DATOS

	public static LinkedList<Usuario> getUsuario() throws SQLException {
		
		LinkedList<Usuario> usuario = new LinkedList<Usuario>();
	
		try {
		//declaro la base de datos
		String url="jdbc:sqlite:TierraMedia.db";
		Connection connection = DriverManager.getConnection(url);
		//declaro la tabla de usuarios
		String sql = "SELECT * FROM usuarios";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet usuariosdb= statement.executeQuery();
		
		while (usuariosdb.next()) {
			String nombre = usuariosdb.getString("nombre");
			int dinero= usuariosdb.getInt("dinero");
			double tiempo= usuariosdb.getDouble("tiempo");
			TipoAtraccion prefe= TipoAtraccion.valueOf(usuariosdb.getString("preferencia"));
			Usuario nuevoUsuario = new Usuario(nombre, dinero, tiempo, prefe);
			usuario.add(nuevoUsuario);
		}
		// cierro archivo
		connection.close();
		
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return usuario;
		
	}
	
	// ORDENAR PRODUCTOS POR PRECIO Y DURACION
	public static void ordenarProductos(LinkedList<Producto> sugerencias) {
		Collections.sort(sugerencias, new ProductosOrdenados());
	}

	// ORDENAR PRODUCTOS PONIEDO PRIMERO LOS DE SU PREFERENCIA
	public static List<Producto> ordenarSegunPreferencia(LinkedList<Producto> productos, TipoAtraccion tipo) {
		//Boolean.false < Boolean.true 
		List<Producto> sugerencias = new ArrayList<Producto>();
		List<Producto> queNoCoinciden = new ArrayList<Producto>();

		for (Producto ca : productos)
			if (ca.getTipoDeAtraccion() == tipo)
				sugerencias.add(ca);
			else
				queNoCoinciden.add(ca);

		// AGREGA LOS PRODUCTOS QUE NO COINCIDEN AL FINAL DE LA LISTA
		sugerencias.addAll(queNoCoinciden);

		return sugerencias;

	}	
}