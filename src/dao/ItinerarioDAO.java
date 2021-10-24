package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jdbc.ConnectionProvider;
import turismotierramedia.*;

public class ItinerarioDAO implements GenericDAO<Itinerario> {

	public int insert(Itinerario itinerario) {

		try {
			String sql = "INSERT INTO itinerarios (usuario, producto, costo_total, duracion_total) VALUES (?,?,?,?)";

			Connection conn = ConnectionProvider.getConnection();

			// Preparo la declaracion para SQL
			PreparedStatement statement = conn.prepareStatement(sql);

			// Que poga el nombre en una y todo lo comprado en otra
			statement.setString(1, itinerario.getUsuario());
			statement.setString(2, itinerario.todosProductosEnUnString());
			statement.setDouble(3, itinerario.getCosto());
			statement.setDouble(4, itinerario.getDuracion());

			int rows = statement.executeUpdate();

			return rows;

		} catch (Exception e) {

			throw new MissingDataException(e);
		}
	}

	public Itinerario findByName(String name) {
		try {
			String sql = "SELECT usuario,producto,costo_total,duracion_total FROM itinerarios WHERE usuario LIKE ?";
			// String sql = "SELECT * FROM promociones WHERE nombre_promo like ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, name);
			ResultSet results = statement.executeQuery();
			Itinerario nuevoItin = null;

			if (results.next()) {

				nuevoItin = toItinerario(results);
			}

			return nuevoItin;

		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	private Itinerario toItinerario(ResultSet results) throws SQLException {

		String nombre = String.valueOf(results.getString(1));
		Double costo = Double.valueOf(results.getDouble(3));
		Double tiempo = Double.valueOf(results.getDouble(4));

		String productos = results.getString(2);

		String[] arrayProd = productos.split(",");
		List<String> arrayALista = Arrays.asList(arrayProd);
		// Lista a ArrayList
		ArrayList<String> compra = new ArrayList<String>(arrayALista);

		Itinerario itin = new Itinerario(nombre, compra, costo, tiempo);

		return itin;
	}
	
	@Override
	public int update(Itinerario itin) {
		try {
			String sql = "UPDATE itinerarios SET producto = producto || ',' || ?, costo_total = costo_total + ?, duracion_total = duracion_total + ? WHERE usuario = ?";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, itin.todosProductosEnUnString());
			statement.setDouble(2, itin.getCosto());
			statement.setDouble(3, itin.getDuracion());
			statement.setString(4, itin.getUsuario());

			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	@Override
	public List<Itinerario> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countAll() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Itinerario t) {
		// TODO Auto-generated method stub
		return 0;
	}

}
