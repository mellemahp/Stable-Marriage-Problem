package com.github.mellemahp.stable_marriage_problem.sqlite;

import com.github.mellemahp.sqlite_data_processing.SQLiteSerializable;
import com.github.mellemahp.sqlite_data_processing.SQLiteTableDefinition;

import com.github.mellemahp.stable_marriage_problem.data_containers.*;

public enum StableMarriageSimulationSQLiteTable implements SQLiteTableDefinition {
    EPOCH(EpochDataContainer.class),
    SIMULATION(SimulationDataContainer.class);

    private final Class<? extends SQLiteSerializable> cls;

    private StableMarriageSimulationSQLiteTable(
            Class<? extends SQLiteSerializable> clazz){
        cls = clazz;
    }

    @Override
    public String getTableDefinition() {
        return this.generateTableDefinition(cls);
    }
}