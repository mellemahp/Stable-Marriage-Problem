package com.github.mellemahp.simulation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

import org.yaml.snakeyaml.Yaml;

import com.github.mellemahp.configuration.SimulationConfig;
import com.github.mellemahp.data_collection.DataContainer;

public class SimulationFactory {
    private BlockingQueue<DataContainer> dataBus;
    private static Yaml yaml = new Yaml();

    public SimulationFactory(BlockingQueue<DataContainer> bus) {
        this.dataBus = bus;
    }

    public static FileInputStream extractFile(File file) {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Simulator> buildSimulations(List<String> filePaths) {
        return filePaths.stream()
                .map(File::new)
                .map(SimulationFactory::extractFile)
                .map(fileStream -> yaml.loadAs(fileStream, SimulationConfig.class))
                .map(config -> new StableMarriageSimulator(config, this.dataBus))
                .collect(Collectors.toList());
    }
}