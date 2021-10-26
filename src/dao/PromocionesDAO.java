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
			String sql = "SELECT tipo_promo, nombre_promo, tipoAtracciones.tipo, incluye1,incluye2,gratis,porcentaje_desc,descuento_absoluto,id_promocion FROM promociones JOIN tipoAtracciones ON tipoAtracciones.id_tipo = promociones.tipo_atraccion";
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
		String tipoPromocion = String.valueOf(results.getString("tipo_promo"));
		//obtengo el nombre de la promo
		String nombrePromo = String.valueOf(results.getString("nombre_promo"));
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

			promocion = new PromoPorcentaje(results.getInt("id_promocion"),atracIncluidas, results.getDouble(7), nombrePromo, tipoAtraccion);
			
			

		} else if (tipoPromocion.equalsIgnoreCase("AxB")) {

			/*
			 * PromoPorcentual la creo mandando el array atracciones en primer parametro y
			 * la atraccion que es gratuita en segunobdo mas nombre y tipo
			 */
			Atracciones ataxb = atr.findByName(results.getString(6));

			promocion = new PromoAxB(results.getInt("id_promocion"),atracIncluidas, ataxb, nombrePromo, tipoAtraccion);
			

		} else if (tipoPromocion.equalsIgnoreCase("Abs")) {
			/*
			 * PromoPorcentual la creo mandando el array atracciones en primer parametro y
			 * el dato del costo final obtenido del archivo mas nombre y tipo
			 */
			promocion = new PromoAbsoluta(results.getInt("id_promocion"),atracIncluidas, results.getDouble(8), nombrePromo, tipoAtraccion);
			

		}

		// Agrego la promo a la lista de productos

		return promocion;
	}

	
	// -- No se usan
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
}