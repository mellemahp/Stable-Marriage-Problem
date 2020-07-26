package com.github.mellemahp.simulation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class SimulationRunner {
    public static void main(String[] args) throws FileNotFoundException {
        String filename = "example.yaml"; // args[0];

        InputStream config = new FileInputStream(new File(filename));
        SimulationConfig simulationConfig = new SimulationConfig(config);
        Simulator simulator = new Simulator(simulationConfig);
        simulator.run();
        // simulator.printResults();
    }
}