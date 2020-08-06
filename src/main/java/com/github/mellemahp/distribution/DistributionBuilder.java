package com.github.mellemahp.distribution;

import org.apache.commons.math3.distribution.RealDistribution;
import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomGenerator;

import lombok.NonNull;

import java.util.Map;

import com.github.mellemahp.configuration.DistributionConfig;

public class DistributionBuilder {
    private final RandomGenerator randomGenerator = new JDKRandomGenerator();
    private DistributionConfig distributionConfig;

    public DistributionBuilder with(@NonNull DistributionConfig distributionConfig) {
        this.distributionConfig = distributionConfig;
        return this;
    }

    public RealDistribution build() {
        DistributionType distributionType = this.distributionConfig.getDistributionType();
        Map<String, Double> distributionProperties = this.distributionConfig.getProperties();
        return distributionType.createDistribution(randomGenerator, distributionProperties);
    }
}