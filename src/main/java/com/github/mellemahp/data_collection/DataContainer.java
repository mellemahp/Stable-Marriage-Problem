package com.github.mellemahp.data_collection;

public interface DataContainer {
    public SQLStatement toSQLStatement();

    public String getSimulationId();
}