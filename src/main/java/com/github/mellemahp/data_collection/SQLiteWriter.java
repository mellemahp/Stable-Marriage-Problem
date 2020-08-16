package com.github.mellemahp.data_collection;

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
        if (sqlExecutor.getSize() >= this.batchSize) {
            this.flushBuffer();
        }

        sqlExecutor.add(data);
    }
}