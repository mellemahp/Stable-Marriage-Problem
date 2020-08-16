package com.github.mellemahp.data_collection;

import java.util.Map;
import java.util.UUID;

import lombok.NonNull;

public class EpochDataContainer extends SQLiteDataContainer {
    @SQLiteField(type=SQLiteTypes.INTEGER, nonNull=true)
    protected final Integer epoch;
    @SQLiteField(type=SQLiteTypes.TEXT, nonNull=true)
    protected final UUID simulationID;
    @SQLiteField(type=SQLiteTypes.TEXT, nonNull=true, json=true)
    protected final Map<Integer, Integer> suitorPairings;
    @SQLiteField(type=SQLiteTypes.TEXT, nonNull=true, json=true)
    protected final Map<Integer, Integer> suiteePairings;
    @SQLiteField(type=SQLiteTypes.INTEGER, nonNull=true)
    protected final Integer numberOfNewPairings;

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

    @Override
    public String toString() {
        return this.epoch.toString() + "\t" + this.numberOfNewPairings;
    }
}