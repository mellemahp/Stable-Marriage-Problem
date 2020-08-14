package com.github.mellemahp.data_collection;

public class EpochDataContainer implements DataContainer {
    private int epoch;

    public EpochDataContainer(int epoch) {
        this.epoch = epoch;
    }

    @Override
    public String getSimulationId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SQLStatement toSQLStatement() {
        // TODO Auto-generated method stub
        return null;
    }
}