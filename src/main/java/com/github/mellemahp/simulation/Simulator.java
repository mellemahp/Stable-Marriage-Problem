package com.github.mellemahp.simulation;

import com.github.mellemahp.distribution.DistributionBuilder;
import com.github.mellemahp.person.Person;
import com.github.mellemahp.person.PersonList;
import com.github.mellemahp.person.Suitee;
import com.github.mellemahp.person.SuiteeSupplier;
import com.github.mellemahp.person.Suitor;
import com.github.mellemahp.person.SuitorSupplier;

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
            simulationConfig.getNumberOfSuitors(),
            suitorDistribution,
            preferenceDistribution
        );
        this.suitors.with(new SuitorSupplier()).build();

        this.suitees = new PersonList<>(
            simulationConfig.getNumberOfSuitees(),
            suiteeDistribution,
            preferenceDistribution
        );
        this.suitees.with(new SuiteeSupplier()).build();

        // Initialize preference rankings for suitors and suitees
        this.suitors.initializePreferenceList(this.suitees);
        this.suitees.initializePreferenceList(this.suitors);
    }
    
    public void run() {
        System.out.println("Suitors:");
        System.out.println(this.suitors);
        System.out.println("Suitees:");
        System.out.println(this.suitees);

        int epochs = 0;
        while (this.suitors.hasUnpairedPerson()) {
            this.suitors.forEach(Suitor::propose);
            epochs++;
        }
        System.out.println("=====================================");
        System.out.println("Completed in " + epochs + " epochs.");
        System.out.println("=====================================");
        printResults();
    }

    public void printResults() {
        // TODO: this is placeholder. Please write better way to report data
        this.suitors.forEach(suitor -> {
            Person suitee = suitor.getCurrentPartner();
            System.out.println(String.format("%s -> %s", suitor, suitee));
            System.out.println(suitor.getPreferenceRankingString());
        });
        System.out.println("*************");
        this.suitees.forEach(suitee -> {
            Person suitor = suitee.getCurrentPartner();
            System.out.println(String.format("%s -> %s", suitee, suitor));
            System.out.println(suitee.getPreferenceRankingString());
        });
    }
}