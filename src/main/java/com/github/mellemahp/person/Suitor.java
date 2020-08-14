package com.github.mellemahp.person;

import com.github.mellemahp.events.Event;
import com.github.mellemahp.events.EventBus;

import org.apache.commons.math3.distribution.RealDistribution;

import lombok.NonNull;

public class Suitor extends Person {
    public Suitor(double objectiveAttractivenessScore,
            @NonNull RealDistribution preferenceDistribution,
            @NonNull EventBus bus,
            @NonNull int personID) {
        super(objectiveAttractivenessScore, preferenceDistribution, bus, personID);
    }

    public void propose() {
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