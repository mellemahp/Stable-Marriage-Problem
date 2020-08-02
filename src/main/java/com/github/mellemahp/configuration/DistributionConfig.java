package com.github.mellemahp.configuration;

import com.github.mellemahp.distribution.DistributionType;

import java.util.Map;

public class DistributionConfig {
    private DistributionType distributionType;
    private Map<String, Double> properties;

    public DistributionType getDistributionType() {
        return this.distributionType;
    }

    public void setDistributionType(DistributionType distributionType) {
        this.distributionType = distributionType;
    }

    public Map<String, Double> getProperties() {
        return this.properties;
    }

    public void setProperties(Map<String, Double> properties) {
        this.properties = properties;
    }
}