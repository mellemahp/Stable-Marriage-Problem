package com.github.mellemahp.person;

import com.github.mellemahp.events.Event;
import com.github.mellemahp.events.EventBus;

import org.apache.commons.math3.distribution.RealDistribution;

import lombok.NonNull;

public class Suitor extends Person {
    public Suitor(@NonNull double objectiveAttractivenessScore,
            @NonNull RealDistribution preferenceDistribution) {
        super(objectiveAttractivenessScore, preferenceDistribution);
    }

    public void propose(@NonNull EventBus bus) {
        if (this.currentPartner == null) {
            Suitee currentSuitee = (Suitee) this.preferenceRanking.getPerson(this.preferenceIndex);
            ProposalAnswer answer = currentSuitee.reviewProposal(this);

            if (answer == ProposalAnswer.ACCEPT) {
                bus.putEvent(Event.NEW_PARTNER);
                this.currentPartner = currentSuitee;
            } else {
                this.preferenceIndex++;
            }
        }
    }
}