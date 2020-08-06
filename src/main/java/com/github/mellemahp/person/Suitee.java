package com.github.mellemahp.person;

import com.github.mellemahp.events.Event;
import com.github.mellemahp.events.EventBus;

import org.apache.commons.math3.distribution.RealDistribution;

import lombok.NonNull;

public class Suitee extends Person {
    public Suitee(double objectiveAttractivenessScore,
            @NonNull RealDistribution preferenceDistribution,
            @NonNull EventBus bus) {
        super(objectiveAttractivenessScore, preferenceDistribution, bus);
    }

    public ProposalAnswer reviewProposal(@NonNull Suitor newSuitor) {
        if (newSuitorHasHigherPreferenceRanking(newSuitor)) {
            this.breakUp();
            bus.putEvent(Event.NEW_PARTNER);
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
