package net.jgp.labs.db.informix;

import java.sql.PreparedStatement;

public class CreateRecord {
	public boolean dboperation(String query) {
		
		PreparedStatement preparedStatement = ConnectionManager.getConnection()
                .prepareStatement("insert into  feedback.comments values (default, ?, ?, ?, ? , ?, ?)");
		
		return true;
	}
}
