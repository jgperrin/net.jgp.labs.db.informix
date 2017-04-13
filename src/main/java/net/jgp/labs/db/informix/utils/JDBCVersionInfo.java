package net.jgp.labs.db.informix.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCVersionInfo {

	public static void main(String[] args) {

		try {
			Class.forName("com.informix.jdbc.IfxDriver");
		} catch (ClassNotFoundException e) {
			System.err.println("Could not access JDBC driver's class: " + e.getMessage());
			return;
		}

		String hostname = "[::1]";
		int port = 33378;
		String user = "informix";
		String password = "in4mix";
		String database = "stores_demo";
		String informixServer = "lo_informix1210";
		Connection connection;

		String jdbcUrl = "jdbc:informix-sqli://" + hostname + ":" + port + "/" + database + ":INFORMIXSERVER="
				+ informixServer;
		try {
			connection = DriverManager.getConnection(jdbcUrl, user, password);
		} catch (SQLException e) {
			System.err.println("Could not get a connection to the database: " + e.getMessage());
			return;
		}

		DatabaseMetaData dbmd;
		try {
			dbmd = connection.getMetaData();
		} catch (SQLException e) {
			System.err.println("An error occured while accessing metadata: " + e.getMessage());
			return;
		}

		try {
			System.out.println("Driver name ............ " + dbmd.getDriverName());
			System.out.println("Driver version ......... " + dbmd.getDriverVersion());
			System.out.println("Driver major version ... " + dbmd.getDriverMajorVersion());
			System.out.println("Driver minor version ... " + dbmd.getDriverMinorVersion());
			System.out.println("Database name .......... " + dbmd.getDatabaseProductName());
			System.out.println("Database version ....... " + dbmd.getDatabaseProductVersion());
		} catch (SQLException e) {
			System.err.println("An error occured while extracting metadata: " + e.getMessage());
		}
	}
}
