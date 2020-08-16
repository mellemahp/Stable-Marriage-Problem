package com.github.mellemahp.data_collection;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.github.mellemahp.person.PersonList;
import com.github.mellemahp.person.Suitee;
import com.github.mellemahp.person.Suitor;

public class EpochDataContainerBuilder implements DataContainerBuilder {
    private final UUID simulationID;
    private Integer epoch;
    private final Map<Integer, Integer> suitorPairings = new HashMap<>();
    private final Map<Integer, Integer> suiteePairings = new HashMap<>();
    private Integer numberOfNewPairings;
    private static final int NO_PARTNER = -1;

    public EpochDataContainerBuilder(UUID simID) {
        simulationID = simID;
    }

    public EpochDataContainer build() {
        Map<Integer, Integer> suitorPairingsCopy = new HashMap<>(this.suitorPairings);
        Map<Integer, Integer> suiteePairingsCopy = new HashMap<>(this.suiteePairings);

        EpochDataContainer dataContainer = new EpochDataContainer(
            this.epoch,
            this.simulationID,
            suitorPairingsCopy,
            suiteePairingsCopy,
            this.numberOfNewPairings
        );
        this.clear();
        return dataContainer;
    }

    public EpochDataContainerBuilder withEpoch(int epoch) {
        this.epoch = epoch;
        return this;
    }

    public EpochDataContainerBuilder withSuitors(PersonList<Suitor> suitors) {
        for (Suitor suitor : suitors) {
            this.suitorPairings.put(
                suitor.getPersonID(),
                suitor.getCurrentPartner() != null
                    ? suitor.getCurrentPartner().getPersonID()
                    : NO_PARTNER
            );
        }
        return this;
    }

    public EpochDataContainerBuilder withSuitees(PersonList<Suitee> suitees) {
        for (Suitee suitee : suitees) {
            this.suiteePairings.put(
                suitee.getPersonID(),
                suitee.getCurrentPartner() != null
                    ? suitee.getCurrentPartner().getPersonID()
                    : NO_PARTNER
            );
        }
        return this;
    }

    public EpochDataContainerBuilder withNumberOfNewPairings(int numberOfNewPairings) {
        this.numberOfNewPairings = numberOfNewPairings;
        return this;
    }

    private void clear() {
        this.epoch = null;
        this.suitorPairings.clear();
        this.suiteePairings.clear();
        this.numberOfNewPairings = null;
    }
}
