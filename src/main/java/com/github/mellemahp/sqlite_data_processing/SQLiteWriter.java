package com.github.mellemahp.sqlite_data_processing;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLiteWriter {
    private final SQLStatementExecutor sqlExecutor = new SQLStatementExecutor();
    private int batchSize;

    public SQLiteWriter(int batchSize) {
        this.batchSize = batchSize;
    }

    public void flushBuffer(Connection connection) throws SQLException {
        sqlExecutor.execute(connection);
        sqlExecutor.clear();
    }

    public void add(SQLiteDataContainer data, Connection connection) throws SQLException {
        sqlExecutor.add(data);
        if (sqlExecutor.getSize() >= this.batchSize) {
            this.flushBuffer(connection);
        }
    }
}