package com.github.mellemahp.data_collection;

import java.util.UUID;

public interface DataContainer {
    public SQLStatement toSQLStatement();

    public UUID getSimulationID();
}