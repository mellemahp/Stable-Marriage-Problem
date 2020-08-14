package com.github.mellemahp.simulation;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

import com.github.mellemahp.data_collection.DataContainer;
import com.github.mellemahp.events.EventBus;
 
public abstract class Simulator implements Callable<Integer> {
    protected final BlockingQueue<DataContainer> dataBus;
    protected final EventBus eventBus = new EventBus();
    protected static final String LONGSEP = "=====================================";
    protected final UUID simulationID = UUID.randomUUID();

    public Simulator(BlockingQueue<DataContainer> dataBusRef) { 
        dataBus = dataBusRef;
    }

    @Override
    public Integer call() { 
        return run();
    }

    public abstract int run();
    public abstract void printResults();
}
