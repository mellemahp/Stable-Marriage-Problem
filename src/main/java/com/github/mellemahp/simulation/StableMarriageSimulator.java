package com.github.mellemahp.simulation;

import java.util.StringJoiner;
import java.util.concurrent.BlockingQueue;

import com.github.mellemahp.configuration.SimulationConfig;
import com.github.mellemahp.data_collection.EpochDataContainer;
import com.github.mellemahp.data_collection.EpochDataContainerBuilder;
import com.github.mellemahp.data_collection.PoisonPill;
import com.github.mellemahp.distribution.DistributionBuilder;
import com.github.mellemahp.events.Event;
import com.github.mellemahp.person.Person;
import com.github.mellemahp.person.PersonList;
import com.github.mellemahp.person.Suitee;
import com.github.mellemahp.person.SuiteeSupplier;
import com.github.mellemahp.person.Suitor;
import com.github.mellemahp.person.SuitorSupplier;
import com.github.mellemahp.sqlite_data_processing.SQLiteDataContainer;

import org.apache.commons.math3.distribution.RealDistribution;

import lombok.CustomLog;
import lombok.NonNull;

@CustomLog
public class StableMarriageSimulator extends Simulator {
    private PersonList<Suitor> suitors;
    private PersonList<Suitee> suitees;
    private int epochChangeThreshold;
    private int maxEpochs;
    private RealDistribution suitorDistribution;
    private RealDistribution suiteeDistribution;
    private RealDistribution preferenceDistribution;
    private final StringJoiner newLineStringJoiner = new StringJoiner("\n");
    private final EpochDataContainerBuilder epochDataContainerBuilder;

    public StableMarriageSimulator(@NonNull SimulationConfig simulationConfig,
            @NonNull BlockingQueue<com.github.mellemahp.sqlite_data_processing.SQLiteDataContainer> dataBusRef) {

        super(dataBusRef);

        epochDataContainerBuilder = new EpochDataContainerBuilder(simulationID);

        setSimulationStoppingConditions(simulationConfig);
        setSimulationDistributions(simulationConfig);

        setSuitorList(simulationConfig);
        setSuiteeList(simulationConfig);

        this.suitors.initializePreferenceList(this.suitees);
        this.suitees.initializePreferenceList(this.suitors);
    }
    
    private void setSimulationStoppingConditions(SimulationConfig simulationConfig) {
        this.epochChangeThreshold = simulationConfig.getStoppingConditionsConfig()
                .getEpochChangeThreshold();
        this.maxEpochs = simulationConfig.getStoppingConditionsConfig()
                .getMaxEpochs();
    }

    private void setSimulationDistributions(SimulationConfig simulationConfig) { 
        DistributionBuilder distributionBuilder = new DistributionBuilder();
        this.suitorDistribution = distributionBuilder
                .with(simulationConfig.getSuitorConfig().getDistribution())
                .build();
        this.suiteeDistribution = distributionBuilder
                .with(simulationConfig.getSuitorConfig().getDistribution())
                .build();
        this.preferenceDistribution = distributionBuilder
                .with(simulationConfig.getPreferenceConfig().getDistribution())
                .build();
    }

    private void setSuitorList(SimulationConfig simulationConfig) { 
        // Build list of suitors and suitees
        this.suitors = new PersonList<>(
                simulationConfig.getSuitorConfig().getNumberOfPeople(),
                this.suitorDistribution,
                this.preferenceDistribution,
                eventBus);
        this.suitors.with(new SuitorSupplier()).build();
    }

    private void setSuiteeList(SimulationConfig simulationConfig) { 
        this.suitees = new PersonList<>(
                simulationConfig.getSuiteeConfig().getNumberOfPeople(),
                this.suiteeDistribution,
                this.preferenceDistribution,
                eventBus);
        this.suitees.with(new SuiteeSupplier()).build();
    }

    @Override
    public int run() throws InterruptedException {
        int epochsWithoutChange = 0;
        while (stoppingConditionNotReached(epochsWithoutChange)) {
            this.suitors.forEach(Suitor::propose);
            int numberOfNewPairings = eventBus.countEventsCurrentEpoch(Event.NEW_PARTNER);
            if (numberOfNewPairings != 0) {
                epochsWithoutChange = 0;
            } else {
                epochsWithoutChange++;
            }
            eventBus.incrementEpoch();
            
            EpochDataContainer epochDataContainer = this.epochDataContainerBuilder
                .withEpoch(eventBus.getCurrentEpoch())
                .withSuitors(this.suitors)
                .withSuitees(this.suitees)
                .withNumberOfNewPairings(numberOfNewPairings)
                .build();

            sendDataWithRetry(epochDataContainer);
        }

        log.info("Sending poison pill and terminating simulation");
        PoisonPill poisonPill = new PoisonPill(this.simulationID);
        sendDataWithRetry(poisonPill);
        log.info(generateSimulationTerminationString());

        return 0;
    }

    private void sendDataWithRetry(SQLiteDataContainer data) throws InterruptedException {
        // TODO: look into offer with timeout
        dataBus.put(data);
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