package jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {
	
	private static String url = "jdbc:sqlite:TierraMedia.db";
	private static String url2 ="jdbc:sqlite:TierraMedia2.db";
	private static Connection connection;
	
	public static Connection getConnection() throws SQLException {
		if (connection == null) {
			connection = DriverManager.getConnection (url2);
		}
		return connection;
	}

}

