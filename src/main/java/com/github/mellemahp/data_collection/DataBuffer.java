package com.github.mellemahp.data_collection;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.github.mellemahp.sqlite_data_processing.SQLiteDataContainer;

public class DataBuffer {
    public final BlockingQueue<SQLiteDataContainer> queue;
    public final int size;

    public DataBuffer(int bufferSize) { 
        size = bufferSize;
        queue = new ArrayBlockingQueue<>(bufferSize);
    }

}