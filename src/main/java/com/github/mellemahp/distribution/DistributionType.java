package com.github.mellemahp.distribution;

import java.util.Map;

import org.apache.commons.math3.distribution.BetaDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.RealDistribution;
import org.apache.commons.math3.random.RandomGenerator;

public enum DistributionType {
    BETA {
        @Override
        public BetaDistribution createDistribution(RandomGenerator randomGenerator,
                Map<String, Double> distributionProperties) {
            double alpha = distributionProperties.get("alpha");
            double beta = distributionProperties.get("beta");
            return new BetaDistribution(randomGenerator, alpha, beta);
        }
    },
    NORMAL {
        @Override
        public NormalDistribution createDistribution(RandomGenerator randomGenerator,
                Map<String, Double> distributionProperties) {
            double mean = distributionProperties.get("mean");
            double standardDeviation = distributionProperties.get("standardDeviation");
            return new NormalDistribution(randomGenerator, mean, standardDeviation);
        }
    };

    public abstract RealDistribution createDistribution(RandomGenerator randomGenerator,
            Map<String, Double> distributionProperties);
}
