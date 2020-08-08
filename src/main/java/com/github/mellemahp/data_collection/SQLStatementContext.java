package com.github.mellemahp.data_collection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import lombok.CustomLog;

@CustomLog
public class SQLStatementContext {
    private Connection connection; 

    public SQLStatementContext(Connection connection) { 
        this.connection = connection;
    }

    public void runInScopedContext(SQLStatementExecutor executor) { 
        Statement statement = null;
        try { 
            statement = connection.createStatement();
            executor.execute(statement);
        } catch (SQLException e) { 
            log.info("Error encountered when trying to run SQL statement" + e.getMessage());
        } finally { 
            closeStatement(statement);
        }
    }

    private void closeStatement(Statement statement) { 
        if (statement != null) { 
            try { 
                statement.close();
            } catch(SQLException e) { 
                log.severe(e.getMessage());
            }      
        }
    }
}