package net.jgp.labs.db.informix.record;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.informix.jdbc.IfmxStatement;

import net.jgp.labs.db.informix.utils.ConnectionManager;
import net.jgp.labs.db.informix.utils.PrettyFormatter;

public class CreateRecord {
	private static transient Logger log = LoggerFactory.getLogger(PrettyFormatter.class);

	PreparedStatement insertNewCustomer = null;

	public static void main(String[] args) {
		log.info("Welcome to create a record - {}", new Date());
		boolean status;
		CreateRecord cr = new CreateRecord();
		// status = cr.executeDbOperation("INSERT INTO manufact VALUES ('SNT',
		// 'Santa Claus', '365')");

		int id = cr.insertNewCustomer("Jean Georges", "Perrin", "JGP.net", "510 Meadowmont Village Circle", "Suite 314",
				"Chapel Hill", "NC", "27517", "919-123-1234");
		if (id > 0) {
			System.out.println("Wonderful, a new customer is in your database! Its unique identifier is '" + id + "'.");
		}
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

	/**
	 * List of fields in the "customer" table: customer_num, fname, lname,
	 * company CHAR 20 NONE 5 address1 CHAR 20 NONE 6 address2 CHAR 20 NONE 7
	 * city CHAR 15 NONE 8 state CHAR 2 NONE 9 zipcode CHAR 5 NONE 10 phone
	 * 
	 * @param fname
	 * @param lname
	 * @param company
	 * @param address1
	 * @param address2
	 * @param city
	 * @param state
	 * @param zipcode
	 * @param phone
	 * @return
	 */
	public synchronized int insertNewCustomer(String fname, String lname, String company, String address1,
			String address2, String city, String state, String zipcode, String phone) {
		String sql = "INSERT INTO customer (customer_num, fname, lname, "
				+ "company, address1, address2, city, state, zipcode, phone) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		if (this.insertNewCustomer == null) {
			try {
				this.insertNewCustomer = ConnectionManager.getConnection().prepareStatement(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return -1;
			}
		}

		try {
			this.insertNewCustomer.setInt(1, 0);
			this.insertNewCustomer.setString(2, fname);
			this.insertNewCustomer.setString(3, lname);
			this.insertNewCustomer.setString(4, company);
			this.insertNewCustomer.setString(5, address1);
			this.insertNewCustomer.setString(6, address2);
			this.insertNewCustomer.setString(7, city);
			this.insertNewCustomer.setString(8, state);
			this.insertNewCustomer.setString(9, zipcode);
			this.insertNewCustomer.setString(10, phone);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}

		int rowCount;
		try {
			rowCount = this.insertNewCustomer.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		log.info("{} row(s) affected", rowCount);

		// ResultSet generatedKeys;
		// try {
		// generatedKeys = this.insertNewCustomer.getGeneratedKeys();
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// return -1;
		// }
		// PrettyFormatter pf = new PrettyFormatter();
		// pf.set(generatedKeys);
		// pf.show();

		int id = -1;
		// try {
		// if (generatedKeys.next()) {
		// id = generatedKeys.getInt(1);
		// }
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// return -1;
		// }

		IfmxStatement ifxps = (IfmxStatement) this.insertNewCustomer;
		try {
			id = ifxps.getSerial();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}

		return id;
	}
}
