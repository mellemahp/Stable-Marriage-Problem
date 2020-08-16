package com.github.mellemahp.stable_marriage_problem;

import java.util.concurrent.BlockingQueue;

import com.github.mellemahp.configuration.SimulationConfig;
import com.github.mellemahp.simulation.Simulation;
import com.github.mellemahp.simulation.SimulationFactory;
import com.github.mellemahp.sqlite_data_processing.SQLiteDataContainer;

public class StableMarriageSimulationFactory extends SimulationFactory {

    public StableMarriageSimulationFactory(BlockingQueue<SQLiteDataContainer> bus) {
        super(bus);
    }

    protected Simulation newSimulation(SimulationConfig config) {
        return new StableMarriageSimulation(config, dataBus);
    }
}