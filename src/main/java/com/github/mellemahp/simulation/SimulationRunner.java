package com.github.mellemahp.simulation;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.github.mellemahp.data_collection.BufferPoller;
import com.github.mellemahp.sqlite_data_processing.SQLiteDataContainer;
import com.github.mellemahp.sqlite_data_processing.SQLiteWriter;
import com.github.mellemahp.wrappers.ForkJoinScope;

import lombok.CustomLog;

@CustomLog
public class SimulationRunner {
    private static ForkJoinScope<Integer> parallelExecutionScope = new ForkJoinScope<>(4);
    private static final int BUFFER_SIZE = 100;
    private static final int BATCH_SIZE = 100;
    private static final BlockingQueue<SQLiteDataContainer> dataBus = new ArrayBlockingQueue<>(BUFFER_SIZE);

    public static void main(String[] args) {

        log.info("Simulation Runner Starting");
        List<String> files = Arrays.asList(args);

        // Concurrently loads simulation files
        log.info("Loading Configuration files...");
        SimulationFactory simulationFactory = new SimulationFactory(dataBus);
        List<Simulator> simulations = simulationFactory.buildSimulations(files);
        
        // Build database and ensure all schemas are created 
        SQLiteSimulationConnector dbConnector = new SQLiteSimulationConnector("test.db");
        dbConnector.createDBIfNotExists();

        int numberOfSimulations = simulations.size();
        SQLiteWriter writer = new SQLiteWriter(dbConnector, BATCH_SIZE);
        BufferPoller poller = new BufferPoller(dataBus, numberOfSimulations, writer);

        log.info(numberOfSimulations + " simulations found. Loading parallel execution context...");
        parallelExecutionScope.addTask(poller);
        for (Simulator sim: simulations) { 
            parallelExecutionScope.addTask(sim);
        }
        
        log.info("Starting parallel run of simulations...");
        parallelExecutionScope.executeTasks();
        log.info("Run complete.");
    }
}
