package com.github.mellemahp.simulation.poisoning;

import java.util.UUID;

import com.github.mellemahp.sqlite_data_processing.SQLiteSerializable;

import lombok.Getter;

public class PoisonPill implements SQLiteSerializable {
    @Getter
    public final UUID simulationId;

    public PoisonPill(UUID simId) {
        simulationId = simId;
    }
}