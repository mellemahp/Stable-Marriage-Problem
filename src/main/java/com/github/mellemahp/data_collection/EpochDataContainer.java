package com.github.mellemahp.data_collection;

import java.util.Map;
import java.util.UUID;

import lombok.NonNull;

public class EpochDataContainer implements DataContainer {
    private final Integer epoch;
    private final UUID simulationID;
    private final Map<Integer, Integer> suitorPairings;
    private final Map<Integer, Integer> suiteePairings;
    private final Integer numberOfNewPairings;

    public EpochDataContainer(
            @NonNull Integer epochNum,
            @NonNull UUID simID,
            @NonNull Map<Integer, Integer> suitorPairingsMap,
            @NonNull Map<Integer, Integer> suiteePairingsMap,
            @NonNull Integer numNewPairings
        ) {
        epoch = epochNum;
        simulationID = simID;
        suitorPairings = suitorPairingsMap;
        suiteePairings = suiteePairingsMap;
        numberOfNewPairings = numNewPairings;
    }

    @Override
    public UUID getSimulationID() {
        return this.simulationID;
    }

    @Override
    public SQLStatement toSQLStatement() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String toString() {
        return this.epoch.toString() + "\t" + this.numberOfNewPairings + "\t" + this.suitorPairings + "\t" + this.suiteePairings;
    }
}