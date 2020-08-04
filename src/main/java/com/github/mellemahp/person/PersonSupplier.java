package com.github.mellemahp.person;

import java.util.function.Supplier;

import org.apache.commons.math3.distribution.RealDistribution;

import lombok.NonNull;

public abstract class PersonSupplier<T extends Person> implements Supplier<T> {
    protected double score;
    protected RealDistribution preferenceDistribution;

    public PersonSupplier<T> withScore(@NonNull double score) {
        this.score = score;
        return this;
    }

    public PersonSupplier<T> withPreferenceDistribution(@NonNull RealDistribution preferenceDistribution) {
        this.preferenceDistribution = preferenceDistribution;
        return this;
    }
}