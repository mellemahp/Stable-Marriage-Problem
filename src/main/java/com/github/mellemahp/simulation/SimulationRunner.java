package com.github.mellemahp.simulation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.github.mellemahp.wrappers.ForkJoinScope;

import lombok.CustomLog;

@CustomLog
public class SimulationRunner {
    private static ForkJoinScope parallelScope = new ForkJoinScope(4);

    public static void runSimulations(List<Simulator> sims) {
        // Runs simulations in parallel
        parallelScope.runInScope(
                () -> sims.parallelStream()
                        .map(Simulator::run)
                        .collect(Collectors.toList()));
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
