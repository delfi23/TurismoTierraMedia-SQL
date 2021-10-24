package dao;
import java.util.List;
import turismotierramedia.Usuario;

public interface UserDAO extends GenericDAO<Usuario> {
	
	public abstract Usuario findByName(String name);
	
	
	
	
}