package com.github.mellemahp.simulation;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;

import com.github.mellemahp.sqlite_data_processing.SQLiteJDBCConnector;

public class SQLiteSimulationConnector extends SQLiteJDBCConnector {

    public SQLiteSimulationConnector(String dataBasePath) { 
        super(dataBasePath);
    }

    @Override
    protected void buildAllTables(Connection connection) throws SQLException {
        for(SQLiteSimulationTables table : SQLiteSimulationTables.values()) {
            String tableDefinitionString = table.getTableDefinition();
            try (Statement statement = connection.createStatement()) {
                statement.execute(tableDefinitionString);
            }
        }
    }
}