package com.github.mellemahp.data_collection;

public enum SQLiteSimulationTables implements SQLiteTableDefinition {
    EPOCH(EpochDataContainer.class);

    private final Class<? extends SQLiteSerializable> cls;

    private SQLiteSimulationTables(Class<? extends SQLiteSerializable> clazz) {
        cls = clazz;
    }

    @Override
    public String getTableDefinition() {
        return this.generateTableDefinition(cls);
    }
}