package com.github.mellemahp.simulation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.CustomLog;

@CustomLog
public class SimulationRunner {
    public static List<Integer> runSimulations(List<Simulator> sims) {
        return sims.parallelStream()
            .map(Simulator::run)
            .collect(Collectors.toUnmodifiableList());
    }

    public static void main(String[] args) {

        log.info("Simulation Runner Starting");
        List<String> files = Arrays.asList(args);
        
        // Concurrently loads simulation files
        log.info("Loading Configuration files...");
        List<Simulator> sims = SimulationLoader.loadSimulations(files);
        log.info(sims.size() + " simulations found. Starting...");

        // Runs simulations in parallel
        List<Integer> _output = runSimulations(sims);
        log.info("Run complete.");
    }
}
