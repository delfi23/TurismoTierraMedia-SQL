package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import jdbc.ConnectionProvider;
import turismotierramedia.*;

public class ItinerarioDAO implements GenericDAO<Itinerario> {

	public int insert(Itinerario itinerario) {

		try {
			String sql = "INSERT INTO itinerarios (id_usuario, id_atraccion, id_promocion) VALUES (?,?,?)";

			Connection conn = ConnectionProvider.getConnection();

			// Preparo la declaracion para SQL
			PreparedStatement statement = conn.prepareStatement(sql);

			// Que poga el nombre en una y todo lo comprado en otra
			statement.setInt(1, itinerario.getId_usuario());
			statement.setInt(2, itinerario.getId_atraccion());
			statement.setInt(3, itinerario.getId_promocion());

			int rows = statement.executeUpdate();

			return rows;

		} catch (Exception e) {

			throw new MissingDataException(e);
		}
	}

	
	public List<Itinerario> findByName(Integer user_id) {
		try {
			String sql = "SELECT id_itinerario,id_usuario,id_atraccion,id_promocion FROM itinerarios WHERE id_usuario LIKE ?";
			// String sql = "SELECT * FROM promociones WHERE nombre_promo like ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, user_id);
			ResultSet results = statement.executeQuery();

			List<Itinerario> nuevoItin = new LinkedList<Itinerario>();

			while (results.next()) {
				nuevoItin.add(toItinerario(results));	
			}
			return nuevoItin;

		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

private Itinerario toItinerario(ResultSet results) throws SQLException {
		Integer id_itinerario = Integer.valueOf(results.getInt("id_itinerario"));
		Integer id_usuario = Integer.valueOf(results.getInt("id_usuario"));
		Integer id_atraccion = Integer.valueOf(results.getInt("id_atraccion"));
		Integer id_promocion = Integer.valueOf(results.getInt("id_promocion"));
		
		Itinerario itin = new Itinerario(id_itinerario, id_usuario, id_atraccion, id_promocion);
		return itin;
	}

	@Override
	public int update(Itinerario itin) {
		/*
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
		*/
		return 0;
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
