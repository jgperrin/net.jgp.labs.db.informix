package net.jgp.labs.db.informix.record;

import java.sql.SQLException;
import java.sql.Statement;

import net.jgp.labs.db.informix.utils.ConnectionManager;

public class CreateRecord {
	public static void main(String[] args) {
		CreateRecord cr = new CreateRecord();
		cr.executeDbOperation("INSERT INTO manufact VALUES ('SNT', 'Santa Claus', '365')");
	}

	public boolean executeDbOperation(String sqlStatement) {
		Statement statement = ConnectionManager.getStatement();
		try {
			statement.executeUpdate(sqlStatement);
		} catch (SQLException e) {
			System.out.println(
					"Could not execute the following SQL statement '" + sqlStatement + "', got: " + e.getMessage());
			return false;
		}
		return true;
	}
}
