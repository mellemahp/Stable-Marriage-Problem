package com.github.mellemahp.data_collection;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

import lombok.CustomLog;

@CustomLog
public class BufferPoller implements Callable<Integer> {
    private final BlockingQueue<DataContainer> dataBus; 
    private final PoisonVial poisonVial;

    public BufferPoller(BlockingQueue<DataContainer> dataBusRef, int capacity) {
        this.dataBus = dataBusRef;
        this.poisonVial = new PoisonVial(capacity);
    }

    @Override
    public Integer call() { 
        log.info("Polling for data");
        pollTillPoisoned();
        log.info("Poison Vial full. Closing poller...");

        return 0;
    }

    private void pollForData() { 
        DataContainer result = dataBus.poll();
        
        if (result == null) { 
            log.info("NO data. Sad");
        } else if (result instanceof PoisonPill) { 
            log.info("Got Poison pill from Simulation: " + result.getSimulationId());
            this.poisonVial.addPoisonPill(result);
        } else {
            log.info("GOT " + result.getData());
            log.info("Buffer Size: " + dataBus.size());
        }
    }

    private void pollTillPoisoned() { 
        while (this.poisonVial.isNotFull()) {
            pollForData();
        }
    }
}