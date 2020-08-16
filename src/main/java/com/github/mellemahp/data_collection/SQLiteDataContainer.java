package com.github.mellemahp.data_collection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.fasterxml.jackson.core.JsonProcessingException;

public abstract class SQLiteDataContainer implements SQLiteSerializable {
    @PrimarySQLKey
    @SQLiteField(type = SQLiteTypes.INTEGER)
    protected final Integer primaryKey;

    protected Connection connection;
    protected String sqlStatement;

    protected SQLiteDataContainer(Integer key) {
        primaryKey = key;
    }

    public PreparedStatement toPreparedStatement()
            throws SQLException, IllegalAccessException, JsonProcessingException {
        PreparedStatement preparedStatement = this.connection.prepareStatement(sqlStatement);
        this.fillPreparedStatement(preparedStatement);

        return preparedStatement;
    }

    public SQLiteDataContainer withConnection(Connection connection) {
        this.connection = connection;

        return this;
    }
}