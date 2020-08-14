package com.github.mellemahp.data_collection;

import java.util.ArrayList;
import java.util.List;

import lombok.CustomLog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@CustomLog
public class SQLStatementExecutor {
    private final List<DataContainer> dataBuffer;

    public SQLStatementExecutor() {
        dataBuffer = new ArrayList<>();
    }

    public void add(DataContainer data) {
        dataBuffer.add(data);
    }

    public void clear() {
        dataBuffer.clear();
    }

    public void execute(Connection connection) {
        for (DataContainer dataObj : dataBuffer) {
            try {
                PreparedStatement preparedStatement = dataObj.withConnection(connection)
                        .toPreparedStatement();
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                log.info(e.getMessage());
            }
        }
    }
}