package com.github.mellemahp.data_collection;

import java.util.Map;
import java.util.UUID;

import com.github.mellemahp.sqlite_data_processing.SQLiteDataContainer;
import com.github.mellemahp.sqlite_data_processing.SQLiteTypes;
import com.github.mellemahp.sqlite_data_processing.annotations.SQLiteField;

import lombok.NonNull;

public class EpochDataContainer extends SQLiteDataContainer {
    @SQLiteField(type=SQLiteTypes.INTEGER, nonNull=true)
    public final Integer epoch;
    @SQLiteField(type=SQLiteTypes.TEXT, nonNull=true)
    public final UUID simulationID;
    @SQLiteField(type=SQLiteTypes.TEXT, nonNull=true, json=true)
    public final Map<Integer, Integer> suitorPairings;
    @SQLiteField(type=SQLiteTypes.TEXT, nonNull=true, json=true)
    public final Map<Integer, Integer> suiteePairings;
    @SQLiteField(type=SQLiteTypes.INTEGER, nonNull=true)
    public final Integer numberOfNewPairings;

    public EpochDataContainer(
            @NonNull Integer epochNum,
            @NonNull UUID simID,
            @NonNull Map<Integer, Integer> suitorPairingsMap,
            @NonNull Map<Integer, Integer> suiteePairingsMap,
            @NonNull Integer numNewPairings) {

        super(simID.hashCode() + epochNum);

        epoch = epochNum;
        simulationID = simID;
        suitorPairings = suitorPairingsMap;
        suiteePairings = suiteePairingsMap;
        numberOfNewPairings = numNewPairings;
    }

    public UUID getSimulationID() {
        return this.simulationID;
    }
}