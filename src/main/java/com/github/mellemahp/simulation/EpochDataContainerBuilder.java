package com.github.mellemahp.simulation;

import java.util.UUID;

import com.github.mellemahp.data_collection.EpochDataContainer;

public class EpochDataContainerBuilder implements DataContainerBuilder {
    private Integer epoch;
    private final UUID simulationID;

    public EpochDataContainerBuilder(UUID simID) {
        simulationID = simID;
    }

    public EpochDataContainer build() {
        EpochDataContainer dataContainer = new EpochDataContainer(this.epoch, this.simulationID);
        this.clear();
        return dataContainer;
    }

    public EpochDataContainerBuilder withEpoch(int epoch) {
        this.epoch = epoch;
        return this;
    }

    private void clear() {
        this.epoch = null;
    }
}
