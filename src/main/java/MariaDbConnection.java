import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MariaDbConnection {

	private static final String URL = "jdbc:mariadb://localhost:3306/";
	private static final String USER = "appuser";
	private static final String PASSWORD = "password";

	private static Connection conn;

	public static Connection getConnection() throws SQLException {
		if (conn == null) {
			try {
				Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				conn.prepareStatement("USE localization_app;").execute();
				return conn;
			} catch (SQLException e) {
				System.err.println(e.getMessage());
				return null;
			}
		} else {
			return conn;
		}
	}
}
