package com.github.mellemahp.sqlite_data_processing;

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
        // TODO: batch execution of prepared statements
        for (SQLiteDataContainer data : dataContainerBuffer) {
            try {
                PreparedStatement preparedStatement = data.getPreparedStatement(connection);
                data.fillPreparedStatement(preparedStatement);
                preparedStatement.execute();
            } catch (IllegalAccessException | JsonProcessingException | SQLException e) {
                log.warning(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}