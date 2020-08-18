package com.github.mellemahp.data_collection;

import java.util.Random;
import java.util.UUID;

import com.github.mellemahp.sqlite_data_processing.SQLiteSerializable;
import com.github.mellemahp.sqlite_data_processing.SQLiteTypes;
import com.github.mellemahp.sqlite_data_processing.annotations.PrimarySQLKey;
import com.github.mellemahp.sqlite_data_processing.annotations.SQLiteField;

import lombok.Builder;

@Builder
public class SimulationDataContainer implements SQLiteSerializable {
    @PrimarySQLKey
    @SQLiteField(type = SQLiteTypes.INTEGER)
    public Integer primaryKey;
    @SQLiteField(type=SQLiteTypes.TEXT, nonNull = true)
    public UUID simulationID;
    @SQLiteField(type=SQLiteTypes.REAL, nonNull = true)
    public double runTimeInSeconds;
    @SQLiteField(type=SQLiteTypes.TEXT, nonNull = true, json = true)
    public boolean isStable;
    @SQLiteField(type=SQLiteTypes.INTEGER, nonNull = true)
    public int numberOfEpochs;

    public static class SimulationDataContainerBuilder {
        public SimulationDataContainerBuilder simulationID(UUID simulationID) {
            this.primaryKey = simulationID.hashCode() + new Random().nextInt(Integer.MAX_VALUE);
            this.simulationID = simulationID;
            return this;
        }
    }
}