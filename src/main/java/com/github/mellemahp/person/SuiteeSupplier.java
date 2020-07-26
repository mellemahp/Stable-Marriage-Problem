package com.github.mellemahp.person;

public class SuiteeSupplier extends PersonSupplier<Suitee> {

    @Override
    public Suitee get() {
        return new Suitee(this.score, this.preferenceDistribution);
    }
    
}