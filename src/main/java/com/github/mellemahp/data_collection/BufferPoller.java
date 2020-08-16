package com.github.mellemahp.data_collection;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

import com.github.mellemahp.sqlite_data_processing.SQLiteDataContainer;
import com.github.mellemahp.sqlite_data_processing.SQLiteWriter;

import lombok.CustomLog;

@CustomLog
public class BufferPoller implements Callable<Integer> {
    private BlockingQueue<SQLiteDataContainer> dataBus; 
    private final PoisonVial poisonVial;
    private final SQLiteWriter writer;

    public BufferPoller(BlockingQueue<SQLiteDataContainer> dataBusRef, int capacity, SQLiteWriter sqliteWriter) {
        this.dataBus = dataBusRef;
        this.poisonVial = new PoisonVial(capacity);
        writer = sqliteWriter;
    }

    @Override
    public Integer call() { 
        log.info("Polling for data");
        pollTillPoisoned();
        log.info("Closing poller.");

        return 0;
    }

    private void pollForData() { 
        SQLiteDataContainer result = this.dataBus.poll();
        if (result == null) { 
            return;
        }

        if (result instanceof PoisonPill) { 
            log.info("Got Poison pill from Simulation");
            this.poisonVial.addPoisonPill(result);
        } else {
            this.writer.add(result);
        }
    }

    private void pollTillPoisoned() { 
        while (this.poisonVial.isNotFull()) {
            pollForData();
        }

        log.info("Flushing buffer...");
        this.writer.flushBuffer();
        log.info("Done flushing.");
    }
}