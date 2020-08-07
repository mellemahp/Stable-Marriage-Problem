package com.github.mellemahp.data_collection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import lombok.CustomLog;

@CustomLog
public class BufferPoller {
    private final BlockingQueue<DataContainer> dataBus; 

    public BufferPoller(BlockingQueue<DataContainer> dataBusRef) {
        dataBus = dataBusRef;
    }

    public List<Integer> pollForData() { 
        List<Integer> resultsCodeList = new ArrayList<>();

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
        return resultsCodeList;
    }
}