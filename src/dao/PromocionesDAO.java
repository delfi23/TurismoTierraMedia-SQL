package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import jdbc.ConnectionProvider;
import turismotierramedia.*;
import dao.*;

public class PromocionesDAO implements GenericDAO<Producto> {

	public List<Producto> findAll() {

		try {
			// Lee todas las Atracciones
			// String sql = "SELECT * FROM promociones";
			String sql = "SELECT tipo_promo, nombre_promo, tipoAtracciones.tipo, incluye1,incluye2,gratis,porcentaje_desc,descuento_absoluto FROM promociones JOIN tipoAtracciones ON tipoAtracciones.id_tipo = promociones.tipo_atraccion";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet results = statement.executeQuery();

			List<Producto> promociones = new LinkedList<Producto>();

			while (results.next()) {
				
				promociones.add(toPromocion(results));
								

			}
			return promociones;

		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public Producto findByName(String name) {
		try {
			String sql="SELECT tipo_promo,nombre_promo,tipoAtracciones.tipo, incluye1,incluye2,gratis,porcentaje_desc,descuento_absoluto FROM promociones JOIN tipoAtracciones ON tipoAtracciones.id_tipo = promociones.tipo_atraccion WHERE nombre_promo LIKE ?";
			//String sql = "SELECT * FROM promociones WHERE nombre_promo like ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, name);
			ResultSet results = statement.executeQuery();
			Producto nuevaPromo = null;

			if (results.next()) {

				nuevaPromo = toPromocion(results);
			}

			return nuevaPromo;

		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	private Producto toPromocion(ResultSet results) throws SQLException {
		//obtengo el tipo de promo
		String tipoPromocion = String.valueOf(results.getString(1));
		//obtengo el nombre de la promo
		String nombrePromo = String.valueOf(results.getString(2));
		//Obtengo el tipo de atraccion de la promocion
		TipoAtraccion tipoAtraccion = TipoAtraccion.valueOf(results.getString(3));
		// creo el objeto para consultar las atracciones dentro de la promo
		AtraccionesDAO atr = DAOFactory.getAtraccionesDAO();
		// creo el array donde iran las atracciones de la promo
		ArrayList<Atracciones> atracIncluidas = new ArrayList<>();
		//declaro el elemento a devolver (la promo)
		Producto promocion = null;

		
		
		/* Agrega al array de atracciones aquellas incluidas en la Promo
		 * las columnas 4 y 5 son las atracciones incluidas
		 * si no son nulas se agregan a la lista atracIncluidas*/
		if (results.getString(4) != null) {
			
			atracIncluidas.add(atr.findByName(results.getString(4)));
		}
		if (results.getString(5) != null) {
			
			atracIncluidas.add(atr.findByName(results.getString(5)));
		}
		
		//creo las promociones deendiendo su tipo (dado por el dato tipoPromocion)

		if (tipoPromocion.equalsIgnoreCase("Por")) {

			/*
			 * PromoPorcentual la creo mandando el array atracciones en primer parametro y
			 * el dato del porcentaje de descuento como segundo mas nombre y tipo
			 */

			promocion = new PromoPorcentaje(atracIncluidas, results.getInt(5), nombrePromo, tipoAtraccion);
			
			

		} else if (tipoPromocion.equalsIgnoreCase("AxB")) {

			/*
			 * PromoPorcentual la creo mandando el array atracciones en primer parametro y
			 * la atraccion que es gratuita en segunobdo mas nombre y tipo
			 */
			Atracciones ataxb = atr.findByName(results.getString(6));

			promocion = new PromoAxB(atracIncluidas, ataxb, nombrePromo, tipoAtraccion);
			

		} else if (tipoPromocion.equalsIgnoreCase("Abs")) {
			/*
			 * PromoPorcentual la creo mandando el array atracciones en primer parametro y
			 * el dato del costo final obtenido del archivo mas nombre y tipo
			 */
			promocion = new PromoAbsoluta(atracIncluidas, results.getDouble(8), nombrePromo, tipoAtraccion);
			

		}

		// Agrego la promo a la lista de productos

		return promocion;
	}

	@Override
	public int countAll() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(Producto t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Producto t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Producto t) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * esto lo anulo de momento <<<-------------->>
	 * 
	 * public List<Producto> findAll() { try { String sql =
	 * "SELECT * FROM promociones"; Connection conn =
	 * ConnectionProvider.getConnection(); PreparedStatement statement =
	 * conn.prepareStatement(sql); ResultSet results = statement.executeQuery();
	 * List<Producto> promos = new LinkedList<Producto>(); while (results.next()) {
	 * promos.add(toPromocion(results)); } return promos;
	 * 
	 * } catch (Exception e) { throw new MissingDataException(e); } }
	 * 
	 * public int countAll() { try { String sql =
	 * "SELECT COUNT(1) AS total FROM promociones"; Connection conn =
	 * ConnectionProvider.getConnection(); PreparedStatement statement =
	 * conn.prepareStatement(sql); ResultSet results = statement.executeQuery();
	 * results.next(); int total = results.getInt("total"); return total; } catch
	 * (Exception e) { throw new MissingDataException(e); } }
	 * 
	 * public int insert(PromoAbsoluta promo) { try { String sql =
	 * "INSERT INTO promociones (nombre_promo, tipo_atraccion, incluye1, incluye2, descuento_absoluto) VALUES (?,?,?,?,?)"
	 * ; Connection conn = ConnectionProvider.getConnection();
	 * 
	 * // Preparo la declaracion para SQL PreparedStatement statement =
	 * conn.prepareStatement(sql); statement.setString(1,
	 * promo.getNombreProducto()); statement.setString(2,
	 * promo.getTipoDeAtraccion().toString()); statement.setString(3,
	 * promo.getAtracciones().get(0).getNombreAtraccion()); statement.setString(4,
	 * promo.getAtracciones().get(1).getNombreAtraccion()); statement.setDouble(5,
	 * promo.getPrecioDescuento());
	 * 
	 * int rows = statement.executeUpdate(); return rows; } catch (Exception e) {
	 * throw new MissingDataException(e); } }
	 * 
	 * public int insert(PromoPorcentaje promo) { try { String sql =
	 * "INSERT INTO promociones (nombre_promo, tipo_atraccion, incluye1, incluye2, porcentaje_descuento) VALUES (?,?,?,?,?)"
	 * ; Connection conn = ConnectionProvider.getConnection();
	 * 
	 * // Preparo la declaracion para SQL PreparedStatement statement =
	 * conn.prepareStatement(sql); statement.setString(1,
	 * promo.getNombreProducto()); statement.setString(2,
	 * promo.getTipoDeAtraccion().toString()); statement.setString(3,
	 * promo.getAtracciones().get(0).getNombreAtraccion()); statement.setString(4,
	 * promo.getAtracciones().get(1).getNombreAtraccion()); statement.setDouble(5,
	 * promo.getPorcentajeDescuento());
	 * 
	 * int rows = statement.executeUpdate(); return rows; } catch (Exception e) {
	 * throw new MissingDataException(e); } }
	 * 
	 * public int insert(PromoAxB promo) { try { String sql =
	 * "INSERT INTO promociones (nombre_promo, tipo_atraccion, incluye1, incluye2, gratis) VALUES (?,?,?,?,?)"
	 * ; Connection conn = ConnectionProvider.getConnection();
	 * 
	 * // Preparo la declaracion para SQL PreparedStatement statement =
	 * conn.prepareStatement(sql); statement.setString(1,
	 * promo.getNombreProducto()); statement.setString(2,
	 * promo.getTipoDeAtraccion().toString()); statement.setString(3,
	 * promo.getAtracciones().get(0).getNombreAtraccion()); statement.setString(4,
	 * promo.getAtracciones().get(1).getNombreAtraccion()); statement.setString(5,
	 * promo.getAtraccionGratuita().getNombreAtraccion());
	 * 
	 * int rows = statement.executeUpdate(); return rows; } catch (Exception e) {
	 * throw new MissingDataException(e); } }
	 * 
	 * public int delete(Producto promo) { try { String sql =
	 * "DELETE FROM promociones WHERE nombre_promo LIKE ?"; Connection conn =
	 * ConnectionProvider.getConnection(); PreparedStatement statement =
	 * conn.prepareStatement(sql); statement.setString(1,
	 * promo.getNombreProducto()); int rows = statement.executeUpdate(); return
	 * rows; } catch (Exception e) { throw new MissingDataException(e); } }
	 * 
	 * public int update(Producto promo) { return 0; }
	 * 
	 * private Producto toPromocion(ResultSet results) throws SQLException {
	 * 
	 * 
	 * if (results.getString(1).equals("AxB")) {
	 * 
	 * System.out.println("encontre a x b");
	 * System.out.println(results.getString(1));
	 * System.out.println(results.getString(2));
	 * System.out.println(results.getString(3));
	 * 
	 * 
	 * ArrayList<Atracciones> atr = new ArrayList<Atracciones>(); AtraccionesDAO
	 * dao1= new AtraccionesDAO();
	 * 
	 * Atracciones atr1 = new Atracciones(results.getString(4));
	 * 
	 * System.out.println(atr1);
	 * 
	 * Atracciones atr2 = dao1.findByName(results.getString(5));
	 * 
	 * System.out.println(atr2);
	 * 
	 * atr.add(results.getString(4)); atr.add(results.getString(5));
	 * 
	 * Atracciones atraccion = new Atracciones(nombreAtraccion, costo,
	 * tiempoAtraccion, cupos, tipo);
	 * 
	 * 
	 * //Atracciones atrG= dao1.findByName(results.getString(6));
	 * 
	 * Producto axb = new PromoAxB(atr, atrG,
	 * results.getString(2),results.getString(3));
	 * 
	 * return axb;
	 * 
	 * }
	 * 
	 * if (results.getString(1).equals("Por")) { ArrayList<Atracciones> atr = new
	 * ArrayList<Atracciones>(); AtraccionesDAO dao1= new AtraccionesDAO();
	 * Atracciones atr1= dao1.findByName(results.getString(4)); Atracciones atr2=
	 * dao1.findByName(results.getString(5)); atr.add(atr1); atr.add(atr2);
	 * 
	 * Producto por = new PromoPorcentaje(atr, results.getDouble(7),
	 * results.getString(2),results.getString(3)); return por; }
	 * 
	 * if (results.getString(1).equals("Abs")) { ArrayList<Atracciones> atr = new
	 * ArrayList<Atracciones>(); AtraccionesDAO dao1= new AtraccionesDAO();
	 * Atracciones atr1= dao1.findByName(results.getString(4)); Atracciones atr2=
	 * dao1.findByName(results.getString(5)); atr.add(atr1); atr.add(atr2);
	 * 
	 * Producto abs = new PromoAbsoluta(atr, results.getDouble(8),
	 * results.getString(2),results.getString(3)); return abs; }
	 * 
	 * System.out.println("no te entiendo nada..."); return null;
	 * 
	 * }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * public int insert(Producto t) { // TODO Auto-generated method stub return 0;
	 * }
	 */

}