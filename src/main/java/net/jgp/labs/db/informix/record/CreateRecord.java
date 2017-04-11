package net.jgp.labs.db.informix.record;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.jgp.labs.db.informix.utils.ConnectionManager;
import net.jgp.labs.db.informix.utils.PrettyFormatter;

public class CreateRecord {
	private static Logger log = LoggerFactory.getLogger(PrettyFormatter.class);

	public static void main(String[] args) {
		log.info("Welcome to create a record - {}", new Date());
		CreateRecord cr = new CreateRecord();
		cr.executeDbOperation("INSERT INTO manufact VALUES ('SNT', 'Santa Claus', '365')");
	}

	public boolean executeDbOperation(String sqlStatement) {
		Statement statement = ConnectionManager.getStatement();
		try {
			statement.executeUpdate(sqlStatement);
		} catch (SQLException e) {
			log.error("Could not execute the following SQL statement '{}', got: {}", sqlStatement, e.getMessage(), e);
			return false;
		}
		return true;
	}
}
