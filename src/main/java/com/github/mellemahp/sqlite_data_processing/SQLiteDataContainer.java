package com.github.mellemahp.sqlite_data_processing;

import com.github.mellemahp.sqlite_data_processing.annotations.PrimarySQLKey;
import com.github.mellemahp.sqlite_data_processing.annotations.SQLiteField;

public abstract class SQLiteDataContainer implements SQLiteSerializable {
    @PrimarySQLKey
    @SQLiteField(type = SQLiteTypes.INTEGER)
    protected final Integer primaryKey;

    protected SQLiteDataContainer(Integer key) {
        primaryKey = key;
    }
}