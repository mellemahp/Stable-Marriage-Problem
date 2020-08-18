package com.github.mellemahp.stable_marriage_problem.simulation;

import java.util.concurrent.BlockingQueue;

import com.github.mellemahp.configuration.SimulationConfig;
import com.github.mellemahp.simulation.Simulation;
import com.github.mellemahp.simulation.SimulationFactory;
import com.github.mellemahp.sqlite_data_processing.SQLiteSerializable;

public class StableMarriageSimulationFactory extends SimulationFactory {

    public StableMarriageSimulationFactory(BlockingQueue<SQLiteSerializable> bus) {
        super(bus);
    }

    protected Simulation newSimulation(SimulationConfig config) {
        return new StableMarriageSimulation(config, dataBus);
    }
}