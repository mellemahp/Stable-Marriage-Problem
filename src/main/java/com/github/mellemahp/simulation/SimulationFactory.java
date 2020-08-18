package com.github.mellemahp.simulation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

import org.yaml.snakeyaml.Yaml;

import com.github.mellemahp.configuration.SimulationConfig;
import com.github.mellemahp.sqlite_data_processing.SQLiteSerializable;


public abstract class SimulationFactory {
    protected BlockingQueue<SQLiteSerializable> dataBus;
    protected static Yaml yaml = new Yaml();

    public SimulationFactory(BlockingQueue<SQLiteSerializable> bus) {
        this.dataBus = bus;
    }

    public List<Simulation> buildSimulations(List<String> filePaths) {
        return filePaths.stream()
                .map(File::new)
                .map(SimulationFactory::extractFile)
                .map(fileStream -> yaml.loadAs(fileStream, SimulationConfig.class))
                .map(this::newSimulation)
                .collect(Collectors.toList());
    }

    protected static FileInputStream extractFile(File file) {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected abstract Simulation newSimulation(SimulationConfig config);
}