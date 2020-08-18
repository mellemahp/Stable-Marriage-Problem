package com.github.mellemahp.simulation;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

import com.github.mellemahp.events.EventBus;
import com.github.mellemahp.sqlite_data_processing.SQLiteSerializable;
 
public abstract class Simulation implements Callable<Integer> {
    protected final BlockingQueue<SQLiteSerializable> dataBus;
    protected final EventBus eventBus = new EventBus();
    protected final UUID simulationID = UUID.randomUUID();
    protected final SimulationTimer timer = new SimulationTimer();

    public Simulation(BlockingQueue<SQLiteSerializable> dataBusRef) { 
        dataBus = dataBusRef;
    }

    @Override
    public abstract Integer call() throws InterruptedException;
}
