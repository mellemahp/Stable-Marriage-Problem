package com.github.mellemahp.data_collection;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.CustomLog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@CustomLog
public class SQLStatementExecutor {
    private final List<SQLiteDataContainer> dataContainerBuffer;

    public SQLStatementExecutor() {
        dataContainerBuffer = new ArrayList<>();
    }

    public void add(SQLiteDataContainer data) {
        dataContainerBuffer.add(data);
    }

    public void clear() {
        dataContainerBuffer.clear();
    }

    public int getSize() {
        return dataContainerBuffer.size();
    }

    public void execute(Connection connection) throws SQLException {
        SQLiteDataContainer dataContainer = dataContainerBuffer.get(0);
        PreparedStatement preparedStatement = dataContainer.getPreparedStatement(connection);
        
        for (SQLiteDataContainer data : dataContainerBuffer) {
            try {
                data.fillPreparedStatement(preparedStatement);
                preparedStatement.addBatch();
            } catch (IllegalAccessException | JsonProcessingException | SQLException e) {
                log.warning(e.getMessage());
                e.printStackTrace();
            }
        }
        preparedStatement.executeBatch();
    }
}