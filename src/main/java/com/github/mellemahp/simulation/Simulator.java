package com.github.mellemahp.simulation;

import com.github.mellemahp.distribution.DistributionBuilder;
import com.github.mellemahp.person.Person;
import com.github.mellemahp.person.PersonList;
import com.github.mellemahp.person.Suitee;
import com.github.mellemahp.person.Suitor;

import org.apache.commons.math3.distribution.RealDistribution;

public class Simulator {
    private PersonList<Suitor> suitors;
    private PersonList<Suitee> suitees;

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
        this.suitors = new PersonList<>(
            Suitor.class,
            simulationConfig.getNumberOfSuitors(),
            suitorDistribution,
            preferenceDistribution
        );
        this.suitees = new PersonList<>(
            Suitee.class,
            simulationConfig.getNumberOfSuitees(),
            suiteeDistribution,
            preferenceDistribution
        );

        // Initialize preference rankings for suitors and suitees
        this.suitors.initializePreferenceList(this.suitees);
        this.suitees.initializePreferenceList(this.suitors);
    }
    
    public void run() {
        System.out.println(this.suitors.getPersonList().get(0).getClass());

        // while (this.suitors.hasUnpairedPerson()) {
        //     for (int i = 0; i < this.suitors.getPersonList().size(); i++) {
        //         System.out.println(this.suitors.getPersonList().get(i).getClass());
        //     }
        // }
    }

    public void printResults() {
        // TODO: this is placeholder. Please write better way to report data

        this.suitors.forEach(person -> {
            Person partner = person.getCurrentPartner();
            System.out.println(partner);
        });
    }
}