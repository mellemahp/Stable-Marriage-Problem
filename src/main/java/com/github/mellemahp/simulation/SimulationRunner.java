package com.github.mellemahp.simulation;

public class SimulationRunner {
    public static void main(String[] args) {
        SimulationConfig simulationConfig = new SimulationConfig();

        Simulator simulator = new Simulator(simulationConfig);
        simulator.run();
        simulator.printResults();
    }
}