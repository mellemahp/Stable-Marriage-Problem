package com.github.mellemahp.data_collection;

public class TestDataContainer implements DataContainer {

    public TestDataContainer() { 
        // GARBAGE
    }

    @Override
    public int getData() {
        return 1;
    }

    @Override
    public String getSimulationId() {
        return "YAY";
    }
    
}