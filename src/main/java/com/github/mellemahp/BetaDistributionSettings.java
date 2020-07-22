package com.github.mellemahp;

public class BetaDistributionSettings extends DistributionSettings{
    private double alpha;
    private double beta;

    public BetaDistributionSettings(double alpha, double beta) {
        this.distributionType = DistributionType.BETA;
        this.alpha = alpha;
        this.beta = beta;
    }

    public double getAlpha() {
        return alpha;
    }

    public double getBeta() {
        return beta;
    }
}