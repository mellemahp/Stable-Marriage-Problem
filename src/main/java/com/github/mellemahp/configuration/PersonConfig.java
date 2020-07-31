package com.github.mellemahp.configuration;

public class PersonConfig {
    private int numberOfPeople;
    private DistributionConfig distribution;

    public int getNumberOfPeople() {
        return this.numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public DistributionConfig getDistribution() {
        return this.distribution;
    }

    public void setDistribution(DistributionConfig distribution) {
        this.distribution = distribution;
    }
}