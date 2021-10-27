package dao;

public class DAOFactory {

	public static UserDAO getUserDAO() {
		return new UserDAO();
	}

	public static AtraccionesDAO getAtraccionesDAO() {
		return new AtraccionesDAO();
	}
	public static PromocionesDAO getPromocionesDAO() {
		return new PromocionesDAO();
	}
	
	public static ItinerarioDAO getItinerariosDAO() {
		return new ItinerarioDAO();
	}
}