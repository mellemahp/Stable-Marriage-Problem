package com.github.mellemahp.simulation;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

import lombok.CustomLog;

@CustomLog
public class SimulationRunner {
    public static void runSimulations(List<Simulator> sims) {
        // Runs simulations in parallel
        final int parallelism = 4;
        ForkJoinPool forkJoinPool = null;
        try {
            forkJoinPool = new ForkJoinPool(parallelism);
            forkJoinPool.submit(
                    () -> sims.parallelStream()
                            .map(Simulator::run)
                            .collect(Collectors.toList()))
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            if (forkJoinPool != null) {
                forkJoinPool.shutdown();
            }
        }
    }

    public static void main(String[] args) {

        log.info("Simulation Runner Starting");
        List<String> files = Arrays.asList(args);

        // Concurrently loads simulation files
        log.info("Loading Configuration files...");
        List<Simulator> sims = SimulationLoader.loadSimulations(files);
        log.info(sims.size() + " simulations found. Starting...");

        // Run simulations in parallel
        runSimulations(sims);
        log.info("Run complete.");
    }
}
