package com.github.mellemahp.simulation;

import java.util.StringJoiner;
import java.util.concurrent.BlockingQueue;

import com.github.mellemahp.configuration.SimulationConfig;
import com.github.mellemahp.data_collection.DataContainer;
import com.github.mellemahp.distribution.DistributionBuilder;
import com.github.mellemahp.events.Event;
import com.github.mellemahp.person.Person;
import com.github.mellemahp.person.PersonList;
import com.github.mellemahp.person.Suitee;
import com.github.mellemahp.person.SuiteeSupplier;
import com.github.mellemahp.person.Suitor;
import com.github.mellemahp.person.SuitorSupplier;
import org.apache.commons.math3.distribution.RealDistribution;

import lombok.CustomLog;
import lombok.NonNull;

@CustomLog
public class StableMarriageSimulator extends Simulator {
    private PersonList<Suitor> suitors;
    private PersonList<Suitee> suitees;
    private int epochChangeThreshold;
    private int maxEpochs;
    private final StringJoiner newLineStringJoiner = new StringJoiner("\n");

    public StableMarriageSimulator(@NonNull SimulationConfig simulationConfig,
            @NonNull BlockingQueue<DataContainer> dataBusRef) {

        super(dataBusRef);

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
                preferenceDistribution,
                eventBus);
        this.suitors.with(new SuitorSupplier()).build();

        this.suitees = new PersonList<>(
                simulationConfig.getSuiteeConfig().getNumberOfPeople(),
                suiteeDistribution,
                preferenceDistribution,
                eventBus);
        this.suitees.with(new SuiteeSupplier()).build();

        // Initialize preference rankings for suitors and suitees
        this.suitors.initializePreferenceList(this.suitees);
        this.suitees.initializePreferenceList(this.suitors);
    }

    @Override
    public int run() {
        int epochsWithoutChange = 0;
        while (stoppingConditionNotReached(epochsWithoutChange)) {
            this.suitors.forEach(Suitor::propose);
            if (eventBus.countEventsCurrentEpoch(Event.NEW_PARTNER) != 0) {
                epochsWithoutChange = 0;
            } else {
                epochsWithoutChange++;
            }
            eventBus.incrementEpoch();
            DataContainer epochData = new DataContainer(1,2);

            int retries = 0;
            while(retries < 5) { 
                try { 
                    boolean queueAcceptedData = dataBus.offer(epochData);
                    if (queueAcceptedData) { 
                        break;
                    } else { 
                        retries++;
                        Thread.sleep(1);
                    }
                } catch (InterruptedException e) { 
                    log.info("SADNESS");
                }
            }
        }
        log.info(generateSimulationTerminationString());

        return 0;
    }

    private boolean stoppingConditionNotReached(int epochsWithoutChange) {
        return epochsWithoutChange < this.epochChangeThreshold
                && eventBus.getCurrentEpoch() < this.maxEpochs;
    }

    private String generateSimulationTerminationString() {
        newLineStringJoiner.add("");
        newLineStringJoiner.add(LONGSEP);
        newLineStringJoiner.add("Completed in " + eventBus.getCurrentEpoch() + " epochs.");
        newLineStringJoiner.add(LONGSEP);
        newLineStringJoiner.add("Stable configuration: " + isStablePairing());
        newLineStringJoiner.add(LONGSEP);
        return newLineStringJoiner.toString();
    }

    public boolean isStablePairing() {
        for (Suitor suitor : this.suitors) {
            if (suitor.hasBetterPartnerOption()) {
                return false;
            }
        }
        return true;
    }

    @Override
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