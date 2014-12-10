package DataDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
	private static String url = "";

	private static String databaseName = "";

	private static String userName = "";

	private static String password = "";

	private static String serverName = "";

	private static String port = "";

	private static Connection conn = null;

	public static Connection Connect() {
		Config();
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(url, userName, password);
		}
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	public static void Config() {
        serverName = "PHUCANH";
        port = "1433";
        databaseName = "AppStore";
        userName = "sa";
        password = "123456";

        url = "jdbc:sqlserver://" + serverName + ":" + port + ";databaseName=" + databaseName;

	}
}
