package com.github.mellemahp.data_collection;

import java.util.UUID;

public class PoisonPill extends SQLiteDataContainer {
    public final UUID simulationId;

    public PoisonPill(UUID simId) {
        super(null);
        simulationId = simId;
    }

    public UUID getSimulationID() {
        return this.simulationId;
    }
}