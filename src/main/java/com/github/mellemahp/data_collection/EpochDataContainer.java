package com.github.mellemahp.data_collection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;
import lombok.NonNull;

public class EpochDataContainer implements DataContainer {
    @PrimarySQLKey
    @SQLiteField(type=SQLiteTypes.INTEGER, nonNull=true)
    private final Integer primaryKey;
    @SQLiteField(type=SQLiteTypes.INTEGER, nonNull=true)
    private final Integer epoch;
    @SQLiteField(type=SQLiteTypes.TEXT, nonNull=true)
    private final UUID simulationID;
    @SQLiteField(type=SQLiteTypes.TEXT, nonNull=true, json=true)
    private final Map<Integer, Integer> suitorPairings;
    @SQLiteField(type=SQLiteTypes.TEXT, nonNull=true, json=true)
    private final Map<Integer, Integer> suiteePairings;
    @SQLiteField(type=SQLiteTypes.INTEGER, nonNull=true)
    private final Integer numberOfNewPairings;

    private final String sqlStatement;
    private Connection connection;
   
    public EpochDataContainer(
            @NonNull Integer epochNum,
            @NonNull UUID simID,
            @NonNull Map<Integer, Integer> suitorPairingsMap,
            @NonNull Map<Integer, Integer> suiteePairingsMap,
            @NonNull Integer numNewPairings) {
        primaryKey = simID.hashCode() + epochNum;
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
        return this;
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
        statement.setBlob(parameterIndex, x);
        return statement;
    }
}