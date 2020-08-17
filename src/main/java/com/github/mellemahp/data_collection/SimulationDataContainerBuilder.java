package com.github.mellemahp.data_collection;

import java.util.UUID;

public class SimulationDataContainerBuilder implements DataContainerBuilder {
    private final UUID simulationID;
    private Double runTimeInSeconds;
    private Boolean isStable;
    private Integer numberOfEpochs;

    public SimulationDataContainerBuilder(UUID simulationID) {
        this.simulationID = simulationID;
    }

    @Override
    public SimulationDataContainer build() {
        return new SimulationDataContainer(
            simulationID,
            runTimeInSeconds,
            isStable,
            numberOfEpochs
        );
    }
    
    public SimulationDataContainerBuilder withRunTimeInSeconds(double runTimeInSeconds) {
        this.runTimeInSeconds = runTimeInSeconds;
        return this;
    }

    public SimulationDataContainerBuilder withIsStable(boolean isStable) {
        this.isStable = isStable;
        return this;
    }

    public SimulationDataContainerBuilder withNumberOfEpochs(int numberOfEpochs) {
        this.numberOfEpochs = numberOfEpochs;
        return this;
    }
}