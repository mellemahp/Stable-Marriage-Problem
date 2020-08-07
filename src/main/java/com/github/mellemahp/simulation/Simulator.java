package com.github.mellemahp.simulation;

import java.util.concurrent.BlockingQueue;

import com.github.mellemahp.data_collection.DataContainer;
import com.github.mellemahp.events.EventBus;

public abstract class Simulator {
    protected final BlockingQueue<DataContainer> dataBus;
    protected final EventBus eventBus = new EventBus();
    protected static final String LONGSEP = "=====================================";

    public Simulator(BlockingQueue<DataContainer> dataBusRef) { 
        dataBus = dataBusRef;
    }

    public abstract int run();
    public abstract void printResults();
}
