package jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {
	
	//private static String url1 = "jdbc:sqlite:TierraMedia.db"; //DB sin ID
	private static String url2 ="jdbc:sqlite:TierraMedia2.db"; //DB con ID
	private static String url3 ="jdbc:sqlite:TierraMediaTest.db"; //DB para tests
	private static Connection connection;
	
	public static Connection getConnection() throws SQLException {
		if (connection == null) {
			connection = DriverManager.getConnection (url2);
		}
		return connection;
	}

}

