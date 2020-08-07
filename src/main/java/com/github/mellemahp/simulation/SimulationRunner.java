package com.github.mellemahp.simulation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import com.github.mellemahp.data_collection.BufferPoller;
import com.github.mellemahp.data_collection.DataContainer;
import com.github.mellemahp.wrappers.ForkJoinScope;

import lombok.CustomLog;

@CustomLog
public class SimulationRunner {
    private static ForkJoinScope parallelScope = new ForkJoinScope(4);
    private static final int BUFFER_SIZE = 20;
    private static final BlockingQueue<DataContainer> dataBus = new ArrayBlockingQueue<>(BUFFER_SIZE);
    private static final BufferPoller poller = new BufferPoller(dataBus);

    public static Callable<List<Integer>> getStartSimulationsInParallelTask(List<Simulator> sims) {
        return () -> sims.parallelStream()
                .map(Simulator::run)
                .collect(Collectors.toList());
    }

    public static Callable<List<Integer>> getStartPollDataBusTask() {
        return () -> poller.pollForData();
    }

    public static void runSimulations(List<Simulator> sims) {
        // Runs simulations in parallel
        List<Callable<List<Integer>>> tasks = new ArrayList<>();
        tasks.add(getStartSimulationsInParallelTask(sims));
        tasks.add(getStartPollDataBusTask());        
        parallelScope.runInScope(tasks);
    }

    public static void main(String[] args) {

        log.info("Simulation Runner Starting");
        List<String> files = Arrays.asList(args);

        // Concurrently loads simulation files
        log.info("Loading Configuration files...");
        SimulationFactory simulationFactory = new SimulationFactory(dataBus);
        List<Simulator> sims = simulationFactory.buildSimulations(files);

        log.info(sims.size() + " simulations found. Starting...");

        // Run simulations in parallel
        runSimulations(sims);
        log.info("Run complete.");
    }
}
