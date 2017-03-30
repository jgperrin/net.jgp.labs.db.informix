package net.jgp.labs.db.informix;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

	/**
	 * Hostname using IPv6.
	 */
	private String hostname = "[::1]";

	private int port = 33378;

	private String user = "informix";

	private String password = "in4mix";

	private String database = "stores_demo";

	private String informixServer = "lo_informix1210";

	private static ConnectionManager instance = null;

	public static Connection getConnection() {
		ConnectionManager cm = ConnectionManager.getInstance();
		return cm.getConnection0();
	}

	private Connection getConnection0() {
		Connection connect;
		try {
			Class.forName("com.informix.jdbc.IfxDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		// Setup the connection with the DB
		String jdbcUrl = "jdbc:informix-sqli://" + hostname + ":" + port + "/" + database + ":INFORMIXSERVER="
				+ informixServer;
		try {
			connect = DriverManager.getConnection(jdbcUrl, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return connect;
	}

	private static ConnectionManager getInstance() {
		if (instance == null) {
			instance = new ConnectionManager();
		}
		return instance;
	}

}
