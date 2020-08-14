package com.github.mellemahp.data_collection;

public class PoisonPill implements DataContainer {
    public final String simulationId;

    public PoisonPill(String simId) { 
        this.simulationId = simId;
    }

    @Override 
    public String getSimulationId() { 
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