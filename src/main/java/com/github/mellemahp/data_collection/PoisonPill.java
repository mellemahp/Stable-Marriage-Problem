package com.github.mellemahp.data_collection;

import java.util.UUID;

public class PoisonPill implements DataContainer {
    public final UUID simulationId;

    public PoisonPill(UUID simId) { 
        simulationId = simId;
    }

    @Override 
    public UUID getSimulationID() { 
        return this.simulationId;
    }

    private RuntimeException poison() {
        return new UnsupportedOperationException("Poison");
    }

    @Override
    public SQLStatement toSQLStatement() {
        // TODO: figure out return poison
        return null;
    }
}