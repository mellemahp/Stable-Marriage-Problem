package com.github.mellemahp.simulation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import org.yaml.snakeyaml.Yaml;

import com.github.mellemahp.configuration.SimulationConfig;

public class SimulationLoader {

    private static Yaml yaml = new Yaml();

    private SimulationLoader() {
        throw new IllegalStateException("Utility class");
    }

    public static FileInputStream extractFile(File file) {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Simulator> loadSimulations(List<String> filePaths) { 
        return filePaths.stream()
            .map(File::new)
            .map(SimulationLoader::extractFile)
            .map(fileStream -> yaml.loadAs(fileStream, SimulationConfig.class))
            .map(StableMarriageSimulator::new)
            .collect(Collectors.toList());
    }
}