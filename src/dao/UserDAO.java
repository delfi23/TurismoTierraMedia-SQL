package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import jdbc.ConnectionProvider;
import turismotierramedia.TipoAtraccion;
import turismotierramedia.Usuario;


public class UserDAO implements GenericDAO<Usuario> {

	public Usuario findByName(String name) {
		try {

			String sql = "SELECT nombre,dinero,tiempo,tipoAtracciones.tipo as 'preferencia',id_usuario FROM usuarios JOIN tipoAtracciones ON tipoAtracciones.id_tipo = usuarios.preferencia WHERE nombre LIKE ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, name);
			ResultSet results = statement.executeQuery();
			Usuario user = null;

			if (results.next()) {
				user = toUsuario(results);
			}
			return user;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}

	}

	private Usuario toUsuario(ResultSet results) throws SQLException {

		TipoAtraccion prefe = TipoAtraccion.valueOf(results.getString("preferencia"));
		return new Usuario(results.getInt("id_usuario"), results.getString("nombre"), results.getDouble("dinero"),
				results.getDouble("tiempo"), prefe);
	}

	public List<Usuario> findAll() {
		try {
			String sql = "SELECT id_usuario,nombre,dinero,tiempo,tipoAtracciones.tipo FROM usuarios JOIN tipoAtracciones ON tipoAtracciones.id_tipo = usuarios.preferencia";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet results = statement.executeQuery();
			List<Usuario> users = new LinkedList<Usuario>();
			while (results.next()) {
				users.add(toUsuario(results));
			}
			return users;

		} catch (Exception e) {
			throw new MissingDataException(e);
		}

	}

	public int update(Usuario user) {
		try {
			String sql = "UPDATE usuarios SET dinero = ?, tiempo = ? WHERE nombre = ?";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setDouble(1, user.getDineroDisponible());
			statement.setDouble(2, user.getTiempoDisponible());
			statement.setString(3, user.getNombreDeUsuario());

			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}
	
	
	// -- No se usan

	public int countAll() {
		try {
			String sql = "SELECT COUNT(1) AS total FROM usuarios";
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

	public int delete(Usuario user) {
		try {
			String sql = "DELETE FROM usuarios WHERE nombre LIKE ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, user.getNombreDeUsuario());
			int rows = statement.executeUpdate();
			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}

	}

	public int insert(Usuario user) {
		// intento insertar, en caso contrario arroja Exception
		try {
			String sql = "INSERT INTO usuarios (nombre, dinero, tiempo, preferencia, id_usuario) VALUES (?,?,?,?,?)";
			Connection conn = ConnectionProvider.getConnection();

			// Preparo la declaracion para SQL
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, user.getNombreDeUsuario());
			statement.setDouble(2, user.getDineroDisponible());
			statement.setDouble(3, user.getTiempoDisponible());
			statement.setString(4, user.getPreferencia().toString());
			statement.setInt(5, user.getIdUsuario());
			int rows = statement.executeUpdate();
			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}

	}

}