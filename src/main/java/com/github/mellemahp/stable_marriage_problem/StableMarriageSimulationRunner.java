package com.github.mellemahp.stable_marriage_problem;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.github.mellemahp.simulation.SimulationRunner;
import com.github.mellemahp.sqlite_data_processing.SQLiteDataContainer;
import com.github.mellemahp.wrappers.ForkJoinScope;


public class StableMarriageSimulationRunner extends SimulationRunner {
    private static final int BUFFER_SIZE = 10000;
    private static final int BATCH_SIZE = 1000;
    private static final int NUM_THREADS = 4;
    private static final String DB_NAME = "test.db";
    private static final BlockingQueue<SQLiteDataContainer> DATA_BUS = new ArrayBlockingQueue<>(BUFFER_SIZE);

    public StableMarriageSimulationRunner() {
        super(
            new ForkJoinScope<>(NUM_THREADS),
            DATA_BUS, 
            new StableMarriageSimulationFactory(DATA_BUS),
            new StableMarriageSimulationSQLiteConnector(DB_NAME), 
            BATCH_SIZE
        );
    }

    public static void main(String[] args) {
        StableMarriageSimulationRunner runner = new StableMarriageSimulationRunner();
        runner.run(args);
    }
}
