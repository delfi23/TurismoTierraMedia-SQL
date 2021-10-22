package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import jdbc.ConnectionProvider;
import turismotierramedia.*;

public class ItinerarioDAO implements GenericDAO<Itinerario>{

	public int insert(Itinerario itinerario) {
		
		try {
			//Voy a probar poner usuario y lo que compro
			
			String sql = "INSERT INTO itinerarios (usuario, producto) VALUES (?,?)";
			
			Connection conn = ConnectionProvider.getConnection();

			// Preparo la declaracion para SQL
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setString(1, itinerario.getUsuario());
			statement.setString(2, itinerario.getProducto());
			
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

	@Override
	public int update(Itinerario t) {
		// TODO Auto-generated method stub
		return 0;
	}
}

	


