package com.github.mellemahp;

public class NormalDistributionSettings extends DistributionSettings {
    private double mean;
    private double standardDeviation;

    public NormalDistributionSettings(double mean, double standardDeviation) {
        this.distributionType = DistributionType.NORMAL;
        this.mean = mean;
        this.standardDeviation = standardDeviation;
    }

    public double getMean() {
        return mean;
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }
}