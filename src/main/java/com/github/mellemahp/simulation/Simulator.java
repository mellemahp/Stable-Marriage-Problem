package com.github.mellemahp.simulation;

import com.github.mellemahp.configuration.SimulationConfig;
import com.github.mellemahp.distribution.DistributionBuilder;
import com.github.mellemahp.events.Event;
import com.github.mellemahp.events.EventBus;
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
    private EventBus bus;
    private int epochChangeThreshold;
    private int maxEpochs;

    public Simulator(SimulationConfig simulationConfig) {
        // Create new event bus
        this.bus = new EventBus();

        // set simulation stopping conditions
        this.epochChangeThreshold = simulationConfig.getStoppingConditionsConfig()
                .getEpochChangeThreshold();
        this.maxEpochs = simulationConfig.getStoppingConditionsConfig()
                .getMaxEpochs();

        // Make distributions for Suitor, Suitee and Preference
        DistributionBuilder distributionBuilder = new DistributionBuilder();

        RealDistribution suitorDistribution = distributionBuilder
                .with(simulationConfig.getSuitorConfig().getDistribution())
                .build();
        RealDistribution suiteeDistribution = distributionBuilder
                .with(simulationConfig.getSuitorConfig().getDistribution())
                .build();
        RealDistribution preferenceDistribution = distributionBuilder
                .with(simulationConfig.getPreferenceConfig().getDistribution())
                .build();

        // Build list of suitors and suitees
        this.suitors = new PersonList<>(
                simulationConfig.getSuitorConfig().getNumberOfPeople(),
                suitorDistribution,
                preferenceDistribution);
        this.suitors.with(new SuitorSupplier())
                .build();

        this.suitees = new PersonList<>(
                simulationConfig.getSuiteeConfig().getNumberOfPeople(),
                suiteeDistribution,
                preferenceDistribution);
        this.suitees.with(new SuiteeSupplier())
                .build();

        // Initialize preference rankings for suitors and suitees
        this.suitors.initializePreferenceList(this.suitees);
        this.suitees.initializePreferenceList(this.suitors);
    }

    public void run() {
        int epochsWithoutChange = 0;
        while (epochsWithoutChange < this.epochChangeThreshold
                && bus.getCurrentEpoch() < this.maxEpochs) {
            this.suitors.forEach(suitor -> suitor.propose(bus));
            if (bus.countEventsCurrentEpoch(Event.NEW_PARTNER) != 0) {
                epochsWithoutChange = 0;
            } else {
                epochsWithoutChange++;
            }
            bus.incrementEpoch();
        }
        String longSep = "=====================================";
        System.out.println(longSep);
        System.out.println("Completed in " + bus.getCurrentEpoch() + " epochs.");
        System.out.println(longSep);
        System.out.println("Stable configuration: " + isStablePairing());
        System.out.println(longSep);
    }

    public boolean isStablePairing() {
        // NOTE: this can be parallelized
        for (Suitor suitor : this.suitors) {
            if (suitor.hasBetterPartnerOption()) {
                return false;
            }
        }
        return true;
    }

    public void printResults() {
        // TODO: this is placeholder. Please write better way to report data
        System.out.println("+++++++++++++++++++++");
        this.suitors.forEach(suitor -> {
            Person suitee = suitor.getCurrentPartner();
            System.out.println(String.format("%s -> %s", suitor, suitee));
            System.out.println(suitor.getPreferenceRankingString());
        });
        System.out.println("*********");
        this.suitees.forEach(suitee -> {
            Person suitor = suitee.getCurrentPartner();
            System.out.println(String.format("%s -> %s", suitee, suitor));
            System.out.println(suitee.getPreferenceRankingString());
        });
    }
}
