package com.github.mellemahp.data_collection;

public class TestDataContainer implements DataContainer {

    public TestDataContainer() { 
        // GARBAGE
    }

    @Override
    public String getSimulationId() {
        return "YAY";
    }

    @Override
    public SQLStatement toSQLStatement() {
        // TODO Auto-generated method stub
        return null;
    }
    
}