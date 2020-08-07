package com.github.mellemahp.data_collection;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

import lombok.CustomLog;

@CustomLog
public class BufferPoller implements Callable<Integer> {
    private final BlockingQueue<DataContainer> dataBus; 

    public BufferPoller(BlockingQueue<DataContainer> dataBusRef) {
        dataBus = dataBusRef;
    }

    @Override
    public Integer call() { 
        return pollForData();
    }

    public Integer pollForData() { 
        int i = 0;
        while (i < 100 || !dataBus.isEmpty()) { 
            DataContainer res = dataBus.poll();
            i++;
            if (res == null) { 
                log.info("NO data. Sad");
            } else { 
                log.info("GOT " + res.getN1());
                log.info("Buffer Size: " + dataBus.size());
            }
        }
        return 0;
    }
}