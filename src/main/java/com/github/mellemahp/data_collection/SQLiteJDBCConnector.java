package com.github.mellemahp.data_collection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import lombok.CustomLog;

@CustomLog
public class SQLiteJDBCConnector {
   private static final String CLASS_LOADER = "org.sqlite.JDBC";
   private final String connectionString;

   private void loadSQLDriver() {
      try {
         Class.forName(CLASS_LOADER);
      } catch (ClassNotFoundException e) {
         log.info("Unable to find class loader: " + CLASS_LOADER);
      }
   }

   public SQLiteJDBCConnector(String dataBasePath) {
      loadSQLDriver();
      connectionString = String.format("jdbc:sqlite:%s", dataBasePath);
   }

   public void createDBIfNotExists() { 
      SQLStatementExecutor dbCreator = new SQLStatementExecutor();
      // TODO MAKE THIS BUILD THE DB
   }

   public void executeInConnectionContext(SQLStatementExecutor sqlExecutor) {
      try (Connection connection = DriverManager.getConnection(connectionString)){
         sqlExecutor.execute(connection);
      } catch (SQLException e) {
         log.severe("Unable to connect to database " + connectionString + " " + e.getMessage());
      }
   }
}