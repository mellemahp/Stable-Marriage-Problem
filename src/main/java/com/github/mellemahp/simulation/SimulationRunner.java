package com.github.mellemahp.simulation;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import com.github.mellemahp.data_collection.BufferPoller;
import com.github.mellemahp.sqlite_data_processing.SQLiteDataContainer;
import com.github.mellemahp.sqlite_data_processing.SQLiteJDBCConnector;
import com.github.mellemahp.sqlite_data_processing.SQLiteWriter;
import com.github.mellemahp.wrappers.ForkJoinScope;

import lombok.CustomLog;

@CustomLog
public abstract class SimulationRunner {
    protected final ForkJoinScope<Integer> parallelExecutionContext;
    protected final BlockingQueue<SQLiteDataContainer> dataBus;
    protected final SimulationFactory simulationFactory;
    protected final SQLiteJDBCConnector dbConnector;
    protected final Integer writerBatchSize;
    
    protected SQLiteWriter writer;
    protected BufferPoller poller;
    

    public SimulationRunner(
        ForkJoinScope<Integer> parallelExecutionContext,
        BlockingQueue<SQLiteDataContainer> dataBus,
        SimulationFactory simulationFactory,
        SQLiteJDBCConnector dbConnector,
        Integer writerBatchSize
    ) { 
        this.parallelExecutionContext = parallelExecutionContext;
        this.dataBus = dataBus;
        this.simulationFactory = simulationFactory;
        this.dbConnector = dbConnector;
        this.writerBatchSize = writerBatchSize;
    }

    public void run(String[] args) {
        initializeDatabase();
        List<String> files = parseArgs(args);
        List<Simulation> simulations = getSimulations(files);
        initializeWriter(); 
        initializePoller(simulations.size());
        initializeExecutionContext(simulations);
        runExecutionContext();
    }

    public void initializeWriter() {
        this.writer = new SQLiteWriter(this.dbConnector, this.writerBatchSize);
    }

    public void initializePoller(int numberOfSimulations) { 
        this.poller = new BufferPoller(this.dataBus, numberOfSimulations, this.writer);
    }
    
    private void initializeExecutionContext(List<Simulation> simulations) {
        this.parallelExecutionContext.addTask(this.poller);
        for (Simulation sim: simulations) { 
            this.parallelExecutionContext.addTask(sim);
        }
    }

    private List<String> parseArgs(String[] args) { 
        log.info("Simulation Runner Starting");
        return Arrays.asList(args);
    }

    private List<Simulation> getSimulations(List<String> files) {
        log.info("Loading Configuration files...");
        return this.simulationFactory.buildSimulations(files);
    }

    private void initializeDatabase(){
        log.info("Creating Database Connector and ensuring tables exist");
        this.dbConnector.createDBIfNotExists();
    }

    private void runExecutionContext() {
        log.info("Starting parallel execution of simulations...");
        this.parallelExecutionContext.executeTasks();
        log.info("Run complete.");
    }
}
