package com.github.mellemahp.sqlite_data_processing;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteWriter {
    private final SQLStatementExecutor sqlExecutor = new SQLStatementExecutor();
    private int batchSize;

    public SQLiteWriter(int batchSize) {
        this.batchSize = batchSize;
    }

    public void flushBuffer(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()){ 
            statement.execute("BEGIN");
        }
    
        sqlExecutor.execute(connection);
        sqlExecutor.clear();
        try (Statement statement = connection.createStatement()) { 
            statement.execute("COMMIT");
        }
    }

    public void add(SQLiteSerializable data, Connection connection) throws SQLException {
        sqlExecutor.add(data);
        if (sqlExecutor.getSize() >= this.batchSize) {
            this.flushBuffer(connection);
        }
    }
}