package com.github.mellemahp.sqlite_data_processing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.CustomLog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@CustomLog
public class SQLStatementExecutor {
    private final List<SQLiteSerializable> dataContainerBuffer = new ArrayList<>();
    private final Map<Class<? extends SQLiteSerializable>, PreparedStatement> statementMap = new HashMap<>();

    public void add(SQLiteSerializable data) {
        dataContainerBuffer.add(data);
    }

    public void clear() {
        dataContainerBuffer.clear();
    }

    public int getSize() {
        return dataContainerBuffer.size();
    }

    private PreparedStatement getPreparedStatementOrCreate(SQLiteSerializable data,
            Connection connection) throws SQLException {
        Class<? extends SQLiteSerializable> cls = data.getClass();
        PreparedStatement preparedStatement = this.statementMap.get(cls);
        if (preparedStatement == null) {
            preparedStatement = data.getPreparedStatement(connection);
            this.statementMap.put(cls, preparedStatement);
        }
        return preparedStatement;
    }

    public void execute(Connection connection) throws SQLException {
        for (SQLiteSerializable data : dataContainerBuffer) {
            try {
                PreparedStatement preparedStatement = getPreparedStatementOrCreate(data, connection);
                data.fillPreparedStatement(preparedStatement);
                preparedStatement.addBatch();
                preparedStatement.clearParameters();
            } catch (IllegalAccessException | JsonProcessingException  e) {
                log.warning(e.getMessage());
                e.printStackTrace();
            }
        }
        for (PreparedStatement statement : this.statementMap.values()) { 
            statement.executeBatch();
        }
        this.statementMap.clear();
    }
}