package com.github.mellemahp.wrappers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;


public class ForkJoinScope<T> {
    private final ForkJoinPool forkJoinPool;
    private final List<Callable<T>> forkJoinTasks = new ArrayList<>();

    public ForkJoinScope(int parallelismLevel) {
        forkJoinPool = new ForkJoinPool(parallelismLevel);
    }

    public void addTask(Callable<T> task) { 
        forkJoinTasks.add(task);
    }

    public void clear() { 
        forkJoinTasks.clear();
    }

    public void executeTasks() { 
        try {
            forkJoinPool.invokeAll(forkJoinTasks);
        } finally {
            forkJoinPool.shutdown();
        }
    }
}
