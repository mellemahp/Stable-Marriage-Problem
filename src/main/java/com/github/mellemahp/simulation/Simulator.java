package com.github.mellemahp.simulation;

import java.util.Date;
import java.util.StringJoiner;
import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
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
    private static Logger log = null;
    static {
        log = Logger.getLogger(SimulationRunner.class.getSimpleName());
        log.setUseParentHandlers(false);

        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter() {
            private static final String format = "[%1$tF %1$tT] [%2$-5s] %3$s %n";

            @Override
            public synchronized String format(LogRecord lr) {
                return String.format(format,
                    new Date(lr.getMillis()),
                    lr.getLevel().getLocalizedName(),
                    lr.getMessage()
                );
            }
        });
        log.addHandler(handler);
    }

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

        StringJoiner stringJoiner = new StringJoiner("\n");
        stringJoiner.add("");
        stringJoiner.add(longSep);
        stringJoiner.add("Completed in " + bus.getCurrentEpoch() + " epochs.");
        stringJoiner.add(longSep);
        stringJoiner.add("Stable configuration: " + isStablePairing());
        stringJoiner.add(longSep);

        String logStatement = stringJoiner.toString();
        log.info(logStatement);
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
        log.info("+++++++++++++++++++++");
        this.suitors.forEach(suitor -> {
            Person suitee = suitor.getCurrentPartner();
            log.info(String.format("%s -> %s", suitor, suitee));
            log.info(suitor.getPreferenceRankingString());
        });
        log.info("*********");
        this.suitees.forEach(suitee -> {
            Person suitor = suitee.getCurrentPartner();
            log.info(String.format("%s -> %s", suitee, suitor));
            log.info(suitee.getPreferenceRankingString());
        });
    }
}
