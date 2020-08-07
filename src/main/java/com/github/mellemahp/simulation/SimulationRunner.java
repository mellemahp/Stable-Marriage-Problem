package com.github.mellemahp.simulation;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.github.mellemahp.data_collection.BufferPoller;
import com.github.mellemahp.data_collection.DataContainer;
import com.github.mellemahp.wrappers.ForkJoinScope;

import lombok.CustomLog;

@CustomLog
public class SimulationRunner {
    private static ForkJoinScope<Integer> parallelExecutionScope = new ForkJoinScope<>(4);
    private static final int BUFFER_SIZE = 20;
    private static final BlockingQueue<DataContainer> dataBus = new ArrayBlockingQueue<>(BUFFER_SIZE);
    private static final BufferPoller poller = new BufferPoller(dataBus); 

    public static void main(String[] args) {

        log.info("Simulation Runner Starting");
        List<String> files = Arrays.asList(args);

        // Concurrently loads simulation files
        log.info("Loading Configuration files...");
        SimulationFactory simulationFactory = new SimulationFactory(dataBus);
        List<Simulator> simulations = simulationFactory.buildSimulations(files);
 
        log.info(simulations.size() + " simulations found. Loading parallel execution context...");
        parallelExecutionScope.addTask(poller);
        for (Simulator sim: simulations) { 
            parallelExecutionScope.addTask(sim);
        }
        
        log.info("Starting parallel run of simulations...");
        parallelExecutionScope.executeTasks();
        log.info("Run complete.");
    }
}
