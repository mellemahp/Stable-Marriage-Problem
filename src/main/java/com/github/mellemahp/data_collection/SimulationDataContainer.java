package com.github.mellemahp.data_collection;

import java.util.Random;
import java.util.UUID;

import com.github.mellemahp.sqlite_data_processing.SQLiteDataContainer;
import com.github.mellemahp.sqlite_data_processing.SQLiteTypes;
import com.github.mellemahp.sqlite_data_processing.annotations.SQLiteField;

import org.apache.commons.lang3.RandomUtils;

import lombok.NonNull;

public class SimulationDataContainer extends SQLiteDataContainer {
    @SQLiteField(type=SQLiteTypes.TEXT, nonNull = true)
    public final UUID simulationID;
    @SQLiteField(type=SQLiteTypes.REAL, nonNull = true)
    public final double runTimeInSeconds;
    @SQLiteField(type=SQLiteTypes.TEXT, nonNull = true, json = true)
    public final boolean isStable;
    @SQLiteField(type=SQLiteTypes.INTEGER, nonNull = true)
    public final int numberOfEpochs;

    protected SimulationDataContainer(
            @NonNull UUID simulationID,
            double runTimeInSeconds,
            boolean isStable,
            int numberOfEpochs) {
        super(
            simulationID.hashCode()
            + (int) (runTimeInSeconds * Math.pow(10, 9))
        );
        
        this.simulationID = simulationID;
        this.runTimeInSeconds = runTimeInSeconds;
        this.isStable = isStable;
        this.numberOfEpochs = numberOfEpochs;
    }

    public UUID getSimulationID() {
        return this.simulationID;
    }
}