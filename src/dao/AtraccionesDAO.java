package dao;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import jdbc.ConnectionProvider;
import turismotierramedia.Atracciones;
import turismotierramedia.TipoAtraccion;

public class AtraccionesDAO implements GenericDAO<Atracciones> {

	public List<Atracciones> findAll() {
		try {
			String sql = "SELECT * FROM atracciones";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet results = statement.executeQuery();
			List<Atracciones> atracciones = new LinkedList<Atracciones>();
			while (results.next()) {
				
				atracciones.add(toAtraccion(results));
			}
			
			return atracciones;
			
		} catch (Exception e) {	 
			throw new MissingDataException(e);
		}
	}

	public int countAll() {
		try {
			String sql = "SELECT COUNT(1) AS total FROM atracciones";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet results = statement.executeQuery();
			results.next();
			int total = results.getInt("total");
			return total;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int insert(Atracciones atraccion) {
		try {
			String sql = "INSERT INTO atracciones (nombre_atraccion, costo, tiempo_recorrido, cupo_personas, tipo_atraccion) VALUES (?,?,?,?,?)";
			Connection conn = ConnectionProvider.getConnection();

			// Preparo la declaracion para SQL
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, atraccion.getNombreAtraccion());
			statement.setDouble(2, atraccion.getCostoTotal());
			statement.setDouble(3, atraccion.getDuracionAtraccion());
			statement.setInt(4, atraccion.getCupoPersonas());
			statement.setString(5, atraccion.getTipoDeAtraccion().toString());
			int rows = statement.executeUpdate();
			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int delete(Atracciones atraccion) {
		try {
			String sql = "DELETE FROM atracciones WHERE nombre_atraccion LIKE ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, atraccion.getNombreAtraccion());
			int rows = statement.executeUpdate();
			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int update(Atracciones atraccion) {
		try {
			String sql = "UPDATE atracciones SET cupo_personas = ? WHERE nombre_atraccion LIKE ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, atraccion.getCupoPersonas());
			statement.setString(2, atraccion.getNombreAtraccion());
			int rows = statement.executeUpdate();
			conn.close();
			return rows;

		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}
	
	private Atracciones toAtraccion(ResultSet results) throws SQLException {
		TipoAtraccion prefe= TipoAtraccion.valueOf(results.getString(5));
		return new Atracciones(results.getString(1), results.getDouble(2), 
				results.getDouble(3), results.getInt(4),prefe);
	}

	public Atracciones findByName(String name) {
		try {
			String sql = "SELECT * FROM atracciones WHERE nombre_atraccion like ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, name);
			ResultSet results = statement.executeQuery();
			Atracciones nuevaAtraccion = null;
			if (results.next()) {
				
				nuevaAtraccion = toAtraccion(results);
			}
			
			return nuevaAtraccion;
			
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}
	
	
	
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

