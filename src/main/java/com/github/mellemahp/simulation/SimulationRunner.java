package com.github.mellemahp.simulation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.yaml.snakeyaml.Yaml;

public class SimulationRunner {
    public static void main(String[] args) throws FileNotFoundException {
        String filename = "example.yaml"; // args[0];

        InputStream configFile = new FileInputStream(new File(filename));
        Yaml yaml = new Yaml();
        SimulationConfig simulationConfig = yaml.load(configFile);
        //Simulator simulator = new Simulator(simulationConfig);
        //simulator.run();
        // simulator.printResults();
    }
}