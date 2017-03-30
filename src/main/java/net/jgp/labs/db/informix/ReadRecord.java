package net.jgp.labs.db.informix;

public class ReadRecord {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
ReadRecord rr = new ReadRecord();
rr.executeDbOperation("SELECT * FROM items ORDER BY item_num");
	}

	private boolean executeDbOperation(String query) {
		// TODO Auto-generated method stub
		return true;
	}

}
