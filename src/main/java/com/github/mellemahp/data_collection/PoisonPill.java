package com.github.mellemahp.data_collection;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

    private RuntimeException poison() {
        return new UnsupportedOperationException("Poison");
    }

    @Override
    public PreparedStatement toPreparedStatement() {
        throw poison();
    }

    @Override
    public PoisonPill withConnection(Connection connection) {
        throw poison();
    }
}