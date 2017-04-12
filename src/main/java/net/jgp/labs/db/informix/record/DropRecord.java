package net.jgp.labs.db.informix.record;

import java.sql.SQLException;
import java.sql.Statement;

import net.jgp.labs.db.informix.utils.ConnectionManager;

public class DropRecord {
	public static void main(String[] args) {
		DropRecord cr = new DropRecord();
		cr.executeDbOperation("DELETE FROM manufact WHERE manu_code = 'SNT'");
		cr.executeDbOperation("DELETE FROM customer WHERE lname = 'Perrin'");
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
