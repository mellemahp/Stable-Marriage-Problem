package com.github.mellemahp.simulation;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

import com.github.mellemahp.events.EventBus;
import com.github.mellemahp.sqlite_data_processing.SQLiteDataContainer;
 
public abstract class Simulator implements Callable<Integer> {
    protected final BlockingQueue<SQLiteDataContainer> dataBus;
    protected final EventBus eventBus = new EventBus();
    protected static final String LONGSEP = "=====================================";
    protected final UUID simulationID = UUID.randomUUID();

    public Simulator(BlockingQueue<SQLiteDataContainer> dataBusRef) { 
        dataBus = dataBusRef;
    }

    @Override
    public Integer call() throws InterruptedException {
        return run();
    }

    public abstract int run() throws InterruptedException;
    public abstract void printResults();
}
