package com.github.mellemahp.simulation;

import com.github.mellemahp.distribution.DistributionSettings;

public class SimulationConfig {
    private int numberOfSuitors;
    private int numberOfSuitees;
    private DistributionSettings suitorDistribution;
    private DistributionSettings suiteeDistribution;
    private DistributionSettings preferenceDistribution;

    public SimulationConfig() {
        // TODO: Handle reading config file
    }

    public int getNumberOfSuitors() {
        return numberOfSuitors;
    }

    public int getNumberOfSuitees() {
        return numberOfSuitees;
    }

    public DistributionSettings getSuitorDistribution() {
        return suitorDistribution;
    }

    public DistributionSettings getSuiteeDistribution() {
        return suiteeDistribution;
    }

    public DistributionSettings getPreferenceDistribution() {
        return preferenceDistribution;
    }
}
