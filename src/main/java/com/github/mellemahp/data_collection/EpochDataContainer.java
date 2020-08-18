package com.github.mellemahp.data_collection;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

import com.github.mellemahp.sqlite_data_processing.SQLiteSerializable;
import com.github.mellemahp.sqlite_data_processing.SQLiteTypes;
import com.github.mellemahp.sqlite_data_processing.annotations.PrimarySQLKey;
import com.github.mellemahp.sqlite_data_processing.annotations.SQLiteField;

import lombok.Builder;
import lombok.Getter;

@Builder
public class EpochDataContainer implements SQLiteSerializable {
    @PrimarySQLKey
    @SQLiteField(type = SQLiteTypes.INTEGER)
    public Integer primaryKey;
    @SQLiteField(type=SQLiteTypes.TEXT, nonNull=true)
    @Getter
    public UUID simulationID;
    @SQLiteField(type=SQLiteTypes.INTEGER, nonNull=true)
    public Integer epoch;
    @SQLiteField(type=SQLiteTypes.TEXT, nonNull=true, json=true)
    public Map<Integer, Integer> suitorPairings;
    @SQLiteField(type=SQLiteTypes.TEXT, nonNull=true, json=true)
    public Map<Integer, Integer> suiteePairings;
    @SQLiteField(type=SQLiteTypes.INTEGER, nonNull=true)
    public Integer numberOfNewPairings;

    public static class EpochDataContainerBuilder {
        public EpochDataContainerBuilder simulationID(UUID simulationID) {
            this.primaryKey = simulationID.hashCode() + new Random().nextInt(Integer.MAX_VALUE);
            this.simulationID = simulationID;
            return this;
        }
    }
}