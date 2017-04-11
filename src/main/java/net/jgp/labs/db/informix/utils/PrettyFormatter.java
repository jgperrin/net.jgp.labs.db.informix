package net.jgp.labs.db.informix.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrettyFormatter {
	private static Logger log = LoggerFactory.getLogger(PrettyFormatter.class);

	private ResultSet resultSet;
	private boolean updated = false;
	private StringBuffer sb;

	public PrettyFormatter() {
		this.sb = new StringBuffer();
	}

	public void set(ResultSet resultSet) {
		this.resultSet = resultSet;
		this.updated = true;
	}

	public void show() {
		if (updated) {
			if (updateBuffer() == false) {
				return;
			}
		}
		System.out.println(sb.toString());
	}

	private boolean updateBuffer() {
		ResultSetMetaData rsmd;
		try {
			rsmd = resultSet.getMetaData();
		} catch (SQLException e) {
			log.error("Could not extract metadata from result set: {}", e.getMessage(), e);
			return false;
		}

		int columnCount;
		try {
			columnCount = rsmd.getColumnCount();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		List<PrettyFormatterColumn> columns = new ArrayList<>();
		PrettyFormatterColumn pfc;
		try {
			for (int i = 1; i <= columnCount; i++) {
				pfc = new PrettyFormatterColumn();
				pfc.setHeading(rsmd.getColumnName(i));
				pfc.setType(rsmd.getColumnType(i));
				pfc.setTypeName(rsmd.getColumnTypeName(i));
				pfc.setColumnWidth(rsmd.getColumnDisplaySize(i));
				columns.add(pfc);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		// Formatting
		String columnHeading = "";
		String rowSeparator = "";
		for (int i = 0; i < columnCount; i++) {
			pfc = columns.get(i);
			columnHeading = columnHeading + "|" + pfc.getColumnName();
			rowSeparator = rowSeparator + "+" + pfc.getDashes();
		}

		columnHeading += "|";
		rowSeparator += "+";

		sb.append(rowSeparator);
		sb.append('\n');
		sb.append(columnHeading);
		sb.append('\n');
		sb.append(rowSeparator);
		sb.append('\n');
		
		try {
			while (resultSet.next()) {
				// Retrieve by column name
				sb.append('|');
				for (int i = 0; i < columnCount; i++) {
					pfc = columns.get(i);
					sb.append(pfc.getFormattedValue(resultSet));
					sb.append('|');
				}
				sb.append('\n');
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sb.append(rowSeparator);
		sb.append('\n');
		return true;
	}

}
