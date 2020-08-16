package com.github.mellemahp.simulation;

import com.github.mellemahp.data_collection.SQLiteDataContainer;

public interface DataContainerBuilder {
    public SQLiteDataContainer build();
}