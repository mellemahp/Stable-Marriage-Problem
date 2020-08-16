package com.github.mellemahp.data_collection;


public abstract class SQLiteDataContainer implements SQLiteSerializable {
    @PrimarySQLKey
    @SQLiteField(type = SQLiteTypes.INTEGER)
    protected final Integer primaryKey;

    protected SQLiteDataContainer(Integer key) {
        primaryKey = key;
    }
}