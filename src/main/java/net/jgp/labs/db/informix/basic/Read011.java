package net.jgp.labs.db.informix.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Read011 {
  private static Logger log = LoggerFactory.getLogger(
      Read011.class);

  public static void main(String[] args) {
    Read011 rr = new Read011();
    rr.executeDbOperation(
        "SELECT * FROM items ORDER BY item_num");
  }

  /**
   * This function really only works with the 'items' table, as the display
   * assumes you have only those columns...
   * 
   * @param query
   * @return
   */
  public boolean executeDbOperation(String query) {
    try {
      Class.forName("com.informix.jdbc.IfxDriver");
    } catch (ClassNotFoundException e) {
      log.error("Could not access JDBC driver's class: {}",
          e.getMessage(), e);
      return false;
    }

    String hostname = "[::1]";
    int port = 33378;
    String user = "informix";
    String password = "in4mix";
    String database = "stores_demo";
    String informixServer = "lo_informix1210";

    String jdbcUrl = "jdbc:informix-sqli://" + hostname
        + ":" + port + "/" + database + ":INFORMIXSERVER="
        + informixServer;

    Connection connection;
    try {
      connection = DriverManager.getConnection(jdbcUrl,
          user, password);
    } catch (SQLException e) {
      log.error(
          "Could not get a connection to the database: {}",
          e.getMessage(), e);
      return false;
    }

    Statement statement;
    try {
      statement = connection.createStatement();
    } catch (SQLException e) {
      log.error("Could not create a statement: {}", e
          .getMessage(), e);
      close(connection);
      return false;
    }

    ResultSet resultSet;
    try {
      resultSet = statement.executeQuery(query);
    } catch (SQLException e) {
      log.error("Could not get a result set: {}", e
          .getMessage(), e);
      close(statement);
      close(connection);
      return false;
    }

    String lineSep =
        "+--------+---------+---------+---------+--------+-----------+";
    System.out.println(lineSep);
    System.out.println(
        "|item_num|order_num|stock_num|manu_code|quantity|total_price|");
    System.out.println(lineSep);
    try {
      int itemNum = resultSet.getInt("item_num");
      int orderNum = resultSet.getInt("order_num");
      int stockNum = resultSet.getInt("stock_num");
      String manufacturerCode = resultSet.getString(
          "manu_code");
      int quantity = resultSet.getInt("quantity");
      double totalPrice = resultSet.getDouble(
          "total_price");

      // Display values
      System.out.printf("|%8d|%9d|%9d|%-9s|%8d|%11.2f|%n",
          itemNum, orderNum, stockNum, manufacturerCode,
          quantity, totalPrice);

    } catch (SQLException e) {
      log.error("Error while browsing result set: {}", e
          .getMessage(), e);
      close(resultSet);
      close(statement);
      close(connection);
      return false;
    }

    System.out.println(lineSep);

    close(resultSet);
    close(statement);
    close(connection);

    return true;
  }

  private void close(Statement statement) {
    try {
      statement.close();
    } catch (SQLException e) {
      log.warn("Could not close the statement: {}", e
          .getMessage(), e);
    }
  }

  private void close(ResultSet resultSet) {
    try {
      resultSet.close();
    } catch (SQLException e) {
      log.warn("Could not close the result set: {}", e
          .getMessage(), e);
    }
  }

  private void close(Connection connection) {
    try {
      connection.close();
    } catch (SQLException e) {
      log.warn(
          "Could not close the connection to the database: {}",
          e.getMessage(), e);
    }
  }

}
