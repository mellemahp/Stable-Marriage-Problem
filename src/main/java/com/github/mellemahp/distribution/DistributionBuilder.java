package com.github.mellemahp.distribution;

import org.apache.commons.math3.distribution.BetaDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.RealDistribution;
import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomGenerator;

import java.util.Map;

import com.github.mellemahp.configuration.DistributionConfig;

public class DistributionBuilder {
    private final RandomGenerator randomGenerator = new JDKRandomGenerator();
    private DistributionConfig distributionConfig;

    
    /** Sets distribution config to use for constructing distributions
     * @param distributionConfig
     * @return DistributionBuilder
     */
    public DistributionBuilder with(DistributionConfig distributionConfig) {
        this.distributionConfig = distributionConfig;
        return this;
    }

    
    /** Builds a new distribution
     * @return RealDistribution
     */
    public RealDistribution build() {
        RealDistribution realDistribution = null;

        DistributionType distributionType = this.distributionConfig.getDistributionType();
        Map<String, Double> distributionProperties = this.distributionConfig.getProperties();
        switch (distributionType) {
        case BETA:
            double alpha = distributionProperties.get("alpha");
            double beta = distributionProperties.get("beta");
            realDistribution = new BetaDistribution(randomGenerator, alpha, beta);
            break;
        case NORMAL:
            double mean = distributionProperties.get("mean");
            double standardDeviation = distributionProperties.get("standardDeviation");
            realDistribution = new NormalDistribution(randomGenerator, mean, standardDeviation);
            break;
        }

        return realDistribution;
    }
}