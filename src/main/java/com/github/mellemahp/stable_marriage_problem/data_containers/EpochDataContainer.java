package com.github.mellemahp.stable_marriage_problem.data_containers;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

import com.github.mellemahp.sqlite_data_processing.SQLiteSerializable;
import com.github.mellemahp.sqlite_data_processing.SQLiteType;
import com.github.mellemahp.sqlite_data_processing.annotations.PrimarySQLKey;
import com.github.mellemahp.sqlite_data_processing.annotations.SQLiteField;

import lombok.Builder;
import lombok.Getter;

@Builder
public class EpochDataContainer implements SQLiteSerializable {
    @PrimarySQLKey
    @SQLiteField(type = SQLiteType.INTEGER)
    public Integer primaryKey;
    @SQLiteField(type=SQLiteType.TEXT, nonNull=true)
    @Getter
    public UUID simulationID;
    @SQLiteField(type=SQLiteType.INTEGER, nonNull=true)
    public Integer epoch;
    @SQLiteField(type=SQLiteType.TEXT, nonNull=true, json=true)
    public Map<Integer, Integer> suitorPairings;
    @SQLiteField(type=SQLiteType.TEXT, nonNull=true, json=true)
    public Map<Integer, Integer> suiteePairings;
    @SQLiteField(type=SQLiteType.INTEGER, nonNull=true)
    public Integer numberOfNewPairings;

    public static class EpochDataContainerBuilder {
        public EpochDataContainerBuilder simulationID(UUID simulationID) {
            this.primaryKey = simulationID.hashCode() + new Random().nextInt(Integer.MAX_VALUE);
            this.simulationID = simulationID;
            return this;
        }
    }
}