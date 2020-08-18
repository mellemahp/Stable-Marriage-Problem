package com.github.mellemahp.person;

import com.github.mellemahp.simulation.data_handling.EventBus;
import com.github.mellemahp.stable_marriage_problem.simulation.StableMarriageSimulationEvent;

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
                bus.putEvent(StableMarriageSimulationEvent.NEW_PARTNER);
                this.currentPartner = currentSuitee;
            } else {
                this.preferenceIndex++;
            }
        }
    }
}