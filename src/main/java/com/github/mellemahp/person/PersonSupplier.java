package com.github.mellemahp.person;

import java.util.function.Supplier;

import org.apache.commons.math3.distribution.RealDistribution;

public abstract class PersonSupplier<T extends Person> implements Supplier<T> {
    protected double score;
    protected RealDistribution preferenceDistribution;

    public PersonSupplier<T> withScore(double score) {
        this.score = score;
        return this;
    }

    public PersonSupplier<T> withPreferenceDistribution(RealDistribution preferenceDistribution) {
        this.preferenceDistribution = preferenceDistribution;
        return this;
    }
}