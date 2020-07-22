package com.github.mellemahp;

import com.github.mellemahp.distribution.DistributionBuilder;

import org.apache.commons.math3.distribution.RealDistribution;

public class Simulator {
    private PersonList suitors;
    private PersonList suitees;

    public Simulator(SimulationConfig simulationConfig) {
        // Make distributions for Suitor, Suitee and Preference
        DistributionBuilder distributionBuilder = new DistributionBuilder();
        
        RealDistribution suitorDistribution = distributionBuilder
            .with(simulationConfig.getSuitorDistribution())
            .build();
        RealDistribution suiteeDistribution = distributionBuilder
            .with(simulationConfig.getSuiteeDistribution())
            .build();
        RealDistribution preferenceDistribution = distributionBuilder
            .with(simulationConfig.getPreferenceDistribution())
            .build();
        
        // Build list of suitors and suitees
        this.suitors = new PersonList(
            simulationConfig.getNumberOfSuitors(),
            suitorDistribution,
            preferenceDistribution
        );
        this.suitees = new PersonList(
            simulationConfig.getNumberOfSuitees(),
            suiteeDistribution,
            preferenceDistribution
        );

        // Initialize preference rankings for suitors and suitees
        this.suitors.initializePreferenceList(this.suitees);
        this.suitees.initializePreferenceList(this.suitors);
    }
    
    public void run() {
        while (this.suitors.hasUnpairedPerson()) {
            this.suitors.forEach(person -> {
                Suitor suitor = (Suitor) person;
                suitor.propose();
            });
        }
    }

    public void printResults() {
        // TODO: this is placeholder. Please write better way to report data

        this.suitors.forEach(person -> {
            Person partner = person.getCurrentPartner();
            System.out.println(partner);
        });
    }
}