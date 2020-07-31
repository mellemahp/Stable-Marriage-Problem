package com.github.mellemahp.distribution;

import org.apache.commons.math3.distribution.BetaDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.RealDistribution;
import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomGenerator;

import java.util.Map;

import com.github.mellemahp.configuration.DistributionConfig;


public class DistributionBuilder {
    private RandomGenerator randomGenerator;
    private DistributionConfig distributionConfig;

    public DistributionBuilder() {
        this.randomGenerator = new JDKRandomGenerator();
    }

    public DistributionBuilder with(DistributionConfig distributionConfig) {
        this.distributionConfig = distributionConfig;

        return this;
    }

    public RealDistribution build() {
        RealDistribution realDistribution = null;

        DistributionType distributionType = this.distributionConfig.getDistributionType();
        Map<String, Double> distributionProperties = this.distributionConfig.getProperties();
        switch (distributionType) {
            case BETA:
                double alpha = distributionProperties.get("alpha");
                double beta = distributionProperties.get("beta");
                realDistribution = new BetaDistribution(this.randomGenerator, alpha, beta);
                break;
            case NORMAL:
                double mean = distributionProperties.get("mean");
                double standardDeviation = distributionProperties.get("standardDeviation");
                realDistribution = new NormalDistribution(this.randomGenerator, mean, standardDeviation);
                break;
        }

        return realDistribution;
    }
}