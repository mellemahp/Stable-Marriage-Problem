package com.github.mellemahp.wrappers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

import lombok.CustomLog;

@CustomLog
public class ForkJoinScope<T> {
    private final int parallelism;
    private final ForkJoinPool forkJoinPool;
    private final List<Callable<T>> taskList = new ArrayList<>();

    public ForkJoinScope(int parallelismLevel) {
        parallelism = parallelismLevel;
        forkJoinPool = new ForkJoinPool(parallelismLevel);
    }

    public void addTask(Callable<T> task) { 
        taskList.add(task);
    }

    public void clear() {
        taskList.clear();
    }

    public void executeTasks() { 
        try {
            for (Callable<T> task: taskList) {
                this.forkJoinPool.submit(task).get();
            }
        } catch (InterruptedException | ExecutionException e) {
            log.warning("Error encountered while executing simulations in parallel");
            throw new RuntimeException(e);
        } finally {
            if (forkJoinPool != null) {
                forkJoinPool.shutdown();
            }
        }
    }
}
