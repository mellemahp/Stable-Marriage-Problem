package com.github.mellemahp.simulation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.github.mellemahp.configuration.SimulationConfig;
import org.yaml.snakeyaml.Yaml;

import lombok.CustomLog;

@CustomLog
public class SimulationRunner {
    private static Yaml yaml = new Yaml();
    private static FileInputStream extractFile(File file) {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<Simulator> loadSimulations(List<String> filePaths) { 
        return filePaths.stream()
            .map(File::new)
            .map(SimulationRunner::extractFile)
            .map(fileStream -> yaml.loadAs(fileStream, SimulationConfig.class))
            .map(Simulator::new)
            .collect(Collectors.toList());
    }

    private static List<Integer> runSimulations(List<Simulator> sims) {
        return sims.parallelStream()
            .map(Simulator::run)
            .collect(Collectors.toUnmodifiableList());
    }

    public static void main(String[] args) {

        log.info("Simulation Runner Starting");
        List<String> files = Arrays.asList(args);
        
        // Concurrently loads simulation files
        log.info("Loading Configuration files...");
        List<Simulator> sims = loadSimulations(files);
        log.info(sims.size() + " simulations found. Starting...");

        // Runs simulations in parallel
        List<Integer> _output = runSimulations(sims);
        log.info("Run complete.");
    }
}
