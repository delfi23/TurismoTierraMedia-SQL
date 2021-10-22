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
	
	
	// >>>>>>>>>>  ESTO ES VIEJO
	// ---------------------------------------

	// ORDENAR PRODUCTOS POR PRECIO
	public static void ordenarPromosPorPrecio(List<Producto> producto) {
		Collections.sort(producto, new ProductosOrdenadosPrecio());
	}
}