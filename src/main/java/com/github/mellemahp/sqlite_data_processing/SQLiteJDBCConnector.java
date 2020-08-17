package com.github.mellemahp.sqlite_data_processing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import lombok.CustomLog;

@CustomLog
public abstract class SQLiteJDBCConnector {
   private static final String CLASS_LOADER = "org.sqlite.JDBC";
   private final String connectionString;

   public SQLiteJDBCConnector(String dataBasePath) {
      loadSQLDriver();
      connectionString = String.format("jdbc:sqlite:%s", dataBasePath);
   }

   public void createDBIfNotExists() { 
      try (Connection connection = DriverManager.getConnection(connectionString)) {
         buildAllTables(connection);
      } catch (SQLException e) {
         log.severe("Unable to connect to database " + connectionString + " " + e.getMessage());
      }
   }

   protected abstract void buildAllTables(Connection connection) throws SQLException;

   public void executeInConnectionContext(SQLiteExecutable sqlExecutor) {
      try (Connection connection = DriverManager.getConnection(connectionString)){
         sqlExecutor.execute(connection);
      } catch (SQLException e) {
         log.severe("Unable to connect to database " + connectionString + " " + e.getMessage());
      }
   }

   private void loadSQLDriver() {
      try {
         Class.forName(CLASS_LOADER);
      } catch (ClassNotFoundException e) {
         log.info("Unable to find class loader: " + CLASS_LOADER);
      }
   }
}