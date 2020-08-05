package com.github.mellemahp.wrappers;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

import lombok.CustomLog;

@CustomLog
public class ForkJoinScope {
    private final int parallelism;
    private ForkJoinPool forkJoinPool = null;

    public ForkJoinScope(int parallelismLevel) {
        parallelism = parallelismLevel;
    }

    public <T> void runInScope(Callable<T> fxn) {
        try {
            this.forkJoinPool = new ForkJoinPool(parallelism);
            this.forkJoinPool.submit(fxn).get();
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
