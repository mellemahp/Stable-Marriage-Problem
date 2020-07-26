package com.github.mellemahp.person;

public class SuitorSupplier extends PersonSupplier<Suitor> {

    @Override
    public Suitor get() {
        return new Suitor(this.score, this.preferenceDistribution);
    }
    
}