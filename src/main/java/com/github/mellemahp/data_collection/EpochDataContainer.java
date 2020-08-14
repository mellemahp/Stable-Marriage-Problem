package com.github.mellemahp.data_collection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;
import lombok.NonNull;

public class EpochDataContainer implements DataContainer {
    @SQLiteField
    private final Integer epoch;
    @SQLiteField
    private final UUID simulationID;
    @SQLiteField
    private final Map<Integer, Integer> suitorPairings;
    @SQLiteField
    private final Map<Integer, Integer> suiteePairings;
    @SQLiteField
    private final Integer numberOfNewPairings;
    private final String sqlStatement;

    private Connection connection;
   
    public EpochDataContainer(
            @NonNull Integer epochNum,
            @NonNull UUID simID,
            @NonNull Map<Integer, Integer> suitorPairingsMap,
            @NonNull Map<Integer, Integer> suiteePairingsMap,
            @NonNull Integer numNewPairings) {
        epoch = epochNum;
        simulationID = simID;
        suitorPairings = suitorPairingsMap;
        suiteePairings = suiteePairingsMap;
        numberOfNewPairings = numNewPairings;
        sqlStatement = this.getSqlStatement();
    }

    @Override
    public UUID getSimulationID() {
        return this.simulationID;
    }

    @Override
    public String toString() {
        return this.epoch.toString() + "\t" + this.numberOfNewPairings + "\t" + this.suitorPairings + "\t"
                + this.suiteePairings;
    }

    @Override
    public DataContainer withConnection(Connection connection) {
        this.connection = connection;
        return null;
    }

    @Override
    public PreparedStatement toPreparedStatement() {
        PreparedStatement statement = null;
        try {
            statement = this.connection.prepareStatement(sqlStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        this.connection = null;
        
        return statement;
    }
}