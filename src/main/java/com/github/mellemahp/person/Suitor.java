package com.github.mellemahp.person;

import org.apache.commons.math3.distribution.RealDistribution;

public class Suitor extends Person {
    public Suitor(double objectiveAttractivenessScore, RealDistribution preferenceDistribution) {
        super(objectiveAttractivenessScore, preferenceDistribution);
    }

    public void propose() {
        Suitee currentSuitee = (Suitee) this.preferenceRanking.getPerson(this.preferenceIndex);
        ProposalAnswer answer = currentSuitee.reviewProposal(this);
        
        switch (answer) {
            case ACCEPT:
                this.currentPartner = currentSuitee;
                break;
            case REJECT:
                this.preferenceIndex++;
                break;
        }
    }

}