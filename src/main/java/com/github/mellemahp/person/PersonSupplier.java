package com.github.mellemahp.person;

import java.util.function.Supplier;

import com.github.mellemahp.events.EventBus;

import org.apache.commons.math3.distribution.RealDistribution;

import lombok.NonNull;

public abstract class PersonSupplier<T extends Person> implements Supplier<T> {
    protected double score;
    protected RealDistribution preferenceDistribution;
    protected EventBus bus;
    protected int personID;

    public PersonSupplier<T> withScore(double score) {
        this.score = score;
        return this;
    }

    public PersonSupplier<T> withBus(EventBus bus) { 
        this.bus = bus; 
        return this;
    }

    public PersonSupplier<T> withPreferenceDistribution(@NonNull RealDistribution preferenceDistribution) {
        this.preferenceDistribution = preferenceDistribution;
        return this;
    }

    public PersonSupplier<T> withPersonID(int personID) {
        this.personID = personID;
        return this;
    }
}