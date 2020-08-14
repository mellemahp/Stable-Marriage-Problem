package com.github.mellemahp.data_collection;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
    public PreparedStatement toPreparedStatement() {
        throw poison();
    }

    @Override
    public DataContainer withConnection(Connection connection) {
        throw poison();
    }
}