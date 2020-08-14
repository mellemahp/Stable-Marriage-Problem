package com.github.mellemahp.data_collection;

import java.util.UUID;

import lombok.NonNull;

public class EpochDataContainer implements DataContainer {
    private final Integer epoch;
    private final UUID simulationID;

    public EpochDataContainer(@NonNull Integer epochNum,
            @NonNull UUID simID) {
        epoch = epochNum;
        simulationID = simID;
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
}