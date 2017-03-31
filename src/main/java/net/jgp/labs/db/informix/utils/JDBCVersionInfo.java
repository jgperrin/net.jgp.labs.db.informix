package net.jgp.labs.db.informix.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class JDBCVersionInfo {

	public static void main(String[] args) {
		Connection connection = ConnectionManager.getConnection();
		if (connection == null) {
			System.out.println("Could not get a connection to the database.");
			return;
		}
		DatabaseMetaData dbmd;
		try {
			dbmd = connection.getMetaData();
		} catch (SQLException e) {
			System.out.println("An error occured while accessing metadata: " + e.getMessage());
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
			System.out.println("An error occured while extracting metadata: " + e.getMessage());
		}

	}

}
