package com.github.mellemahp.stable_marriage_problem.sqlite;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;

import com.github.mellemahp.sqlite_data_processing.SQLiteJDBCConnector;

public class StableMarriageSimulationSQLiteConnector extends SQLiteJDBCConnector {

    public StableMarriageSimulationSQLiteConnector(String dataBasePath) { 
        super(dataBasePath);
    }

    @Override
    protected void buildAllTables(Connection connection) throws SQLException {
        for(StableMarriageSimulationSQLiteTable table : StableMarriageSimulationSQLiteTable.values()) {
            String tableDefinitionString = table.getTableDefinition();
            try (Statement statement = connection.createStatement()) {
                statement.execute(tableDefinitionString);
            }
        }
    }
}