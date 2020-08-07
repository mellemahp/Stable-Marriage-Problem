package com.github.mellemahp.data_collection;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class DataBuffer {
    public final BlockingQueue<DataContainer> queue;
    public final int size;

    public DataBuffer(int bufferSize) { 
        size = bufferSize;
        queue = new ArrayBlockingQueue<>(bufferSize);
    }

}