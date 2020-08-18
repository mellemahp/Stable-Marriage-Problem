package com.github.mellemahp.person;

import com.github.mellemahp.simulation.data_handling.EventBus;
import com.github.mellemahp.stable_marriage_problem.simulation.StableMarriageSimulationEvent;

import org.apache.commons.math3.distribution.RealDistribution;

import lombok.NonNull;

public class Suitee extends Person {
    public Suitee(double objectiveAttractivenessScore,
            @NonNull RealDistribution preferenceDistribution,
            @NonNull EventBus bus,
            @NonNull int personID) {
        super(objectiveAttractivenessScore, preferenceDistribution, bus, personID);
    }

    public ProposalAnswer reviewProposal(@NonNull Suitor newSuitor) {
        if (newSuitorHasHigherPreferenceRanking(newSuitor)) {
            this.breakUp();
            bus.putEvent(StableMarriageSimulationEvent.NEW_PARTNER);
            this.currentPartner = newSuitor;

            return ProposalAnswer.ACCEPT;
        }

        return ProposalAnswer.REJECT;
    }

    private boolean newSuitorHasHigherPreferenceRanking(@NonNull Suitor newSuitor) {
        if (this.currentPartner == null) {
            return true;
        }

        double newSuitorScore = this.preferenceRanking.getPreferenceScore(newSuitor);
        double currentSuitorScore = this.preferenceRanking.getPreferenceScore(this.currentPartner);
        return newSuitorScore > currentSuitorScore;
    }

    public int getPreferenceIndex(@NonNull Suitor suitor) {
        int preferenceIndex = this.preferenceRanking.getPreferenceIndexOfPerson(suitor);
        if (preferenceIndex == -1) {
            throw new IndexOutOfBoundsException();
        }

        return preferenceIndex;
    }
}
