package com.github.mellemahp.wrappers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

import lombok.CustomLog;

@CustomLog
public class ForkJoinScope {
    private final int parallelism;
    private ForkJoinPool forkJoinPool = null;

    public ForkJoinScope(int parallelismLevel) {
        parallelism = parallelismLevel;
    }

    public <T> void runInScope(List<Callable<T>> fxns) {
        try {
            List<ForkJoinTask<T>> tasks = new ArrayList<>();
            this.forkJoinPool = new ForkJoinPool(parallelism);
            for (Callable<T> fxn: fxns) {
                tasks.add(
                    this.forkJoinPool.submit(fxn)
                );
            }
            for (ForkJoinTask<T> task: tasks) {
                task.get();
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
