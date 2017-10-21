package net.jgp.labs.db.informix.record;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.jgp.labs.db.informix.utils.ConnectionManager;

public class UpdateRecord {
	private static transient Logger log = LoggerFactory.getLogger(UpdateRecord.class);

	public static void main(String[] args) {

		ReadRecord rr = new ReadRecord();
		rr.executeDbOperation("SELECT * FROM manufact ORDER BY manu_code");

		// Update lead time of SNT supplier based on current day.
		UpdateRecord ur = new UpdateRecord();
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime nextXmas = LocalDateTime.of(now.getYear(), Month.DECEMBER, 25, 0, 0);
		Duration leadTime = Duration.between(now, nextXmas);

		ur.executeDbOperation("UPDATE manufact SET lead_time='" + leadTime.toDays() + "' WHERE manu_code='SNT'");

		rr.executeDbOperation("SELECT * FROM manufact ORDER BY manu_code");
	}

	public boolean executeDbOperation(String sqlStatement) {
		Statement statement = ConnectionManager.getStatement();
		try {
			statement.executeUpdate(sqlStatement);
		} catch (SQLException e) {
			log.error("Could not execute the following SQL statement '{}', got: ", sqlStatement, e.getMessage(), e);
			return false;
		}
		return true;
	}
}
