package com.github.mellemahp.configuration;

public class StoppingConditionsConfig {
    private int maxEpochs;
    private int epochChangeThreshold;

    public void setMaxEpochs(int maxEpochs) {
        this.maxEpochs = maxEpochs;
    }

    public int getMaxEpochs() {
        return this.maxEpochs;
    }

    public int getEpochChangeThreshold() {
        return this.epochChangeThreshold;
    }

    public void setEpochChangeThreshold(int epochChangeThreshold) {
        this.epochChangeThreshold = epochChangeThreshold;
    }
}