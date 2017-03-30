package net.jgp.labs.db.informix;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

	public static Connection getConnection() {
		Connection connect;
		try {
			Class.forName("com.informix.jdbc.IfxDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		// Setup the connection with the DB
//		String jdbcUrl = "jdbc:informix-sqli://localhost:33379/stores_demo:INFORMIXSERVER=ol_informix1210;user=informix;password=in4mix";
		String jdbcUrl = "jdbc:informix-sqli://[::1]:33378/stores_demo:INFORMIXSERVER=lo_informix1210;user=informix;password=in4mix";
		try {
			connect = DriverManager.getConnection(jdbcUrl);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return connect;
	}

}
