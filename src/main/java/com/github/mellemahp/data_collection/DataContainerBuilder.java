package com.github.mellemahp.data_collection;

import com.github.mellemahp.sqlite_data_processing.SQLiteDataContainer;

public interface DataContainerBuilder {
    public SQLiteDataContainer build();
}