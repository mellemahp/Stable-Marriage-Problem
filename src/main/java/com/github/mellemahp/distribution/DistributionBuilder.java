package com.github.mellemahp.distribution;

import org.apache.commons.math3.distribution.BetaDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.RealDistribution;
import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomGenerator;

public class DistributionBuilder {
    private RandomGenerator randomGenerator;
    private DistributionSettings distributionSettings;

    public DistributionBuilder() {
        this.randomGenerator = new JDKRandomGenerator();
    }

    public DistributionBuilder with(DistributionSettings distributionSettings) {
        this.distributionSettings = distributionSettings;

        return this;
    }

    public RealDistribution build() {
        RealDistribution realDistribution = null;

        DistributionType distributionType = this.distributionSettings.getDistributionType();
        switch (distributionType) {
            case BETA:
                BetaDistributionSettings betaDistributionSettings = (BetaDistributionSettings) this.distributionSettings;
                double alpha = betaDistributionSettings.getAlpha();
                double beta = betaDistributionSettings.getBeta();
                realDistribution = new BetaDistribution(this.randomGenerator, alpha, beta);
                break;
            case NORMAL:
                NormalDistributionSettings normalDistributionSettings = (NormalDistributionSettings) this.distributionSettings;
                double mean = normalDistributionSettings.getMean();
                double standardDeviation = normalDistributionSettings.getStandardDeviation();
                realDistribution = new NormalDistribution(this.randomGenerator, mean, standardDeviation);
                break;
        }

        return realDistribution;
    }
}