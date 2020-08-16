package com.github.mellemahp.data_collection;

import lombok.CustomLog;

@CustomLog
public class SQLiteWriter {
    private SQLiteJDBCConnector connector;
    private final SQLStatementExecutor sqlExecutor = new SQLStatementExecutor();
    private int batchSize;

    public SQLiteWriter(SQLiteJDBCConnector connector, int batchSize) {
        this.connector = connector;
        this.batchSize = batchSize;
    }

    public void flushBuffer() {
        this.connector.executeInConnectionContext(sqlExecutor);
        sqlExecutor.clear();
    }

    public void add(SQLiteDataContainer data) {
        if (data instanceof PoisonPill) {
            throw new IllegalArgumentException();
        }

        sqlExecutor.add(data);
        if (sqlExecutor.getSize() >= this.batchSize) {
            this.flushBuffer();
        }
    }
}