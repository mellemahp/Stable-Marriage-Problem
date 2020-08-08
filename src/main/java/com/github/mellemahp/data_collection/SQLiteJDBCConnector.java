package com.github.mellemahp.data_collection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import lombok.CustomLog;
import lombok.NonNull;

@CustomLog
public class SQLiteJDBCConnector {
   private static final String CLASS_LOADER = "org.sqlite.JDBC";
   private final String connectionString;

   private void connectionContext(SQLStatementExecutor sqlToExecute) { 
      Connection connection = null;
      try {
         connection = DriverManager.getConnection(connectionString);
         SQLStatementContext statementContext = new SQLStatementContext(connection);
         statementContext.runInScopedContext(sqlToExecute);
      } catch (SQLException e) { 
         log.severe("Unable to connect to database " + connectionString + " " + e.getMessage());
      } finally { 
         closeDBConnection(connection);
      }
   }

   private void closeDBConnection(Connection connection) {
      if (connection != null) {
         try { 
            connection.close();
         } catch(SQLException e) { 
            log.severe("Error closing db connection " + e.getMessage());
         }
      }
   }

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
}