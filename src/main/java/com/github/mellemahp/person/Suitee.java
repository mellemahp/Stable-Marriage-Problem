package com.github.mellemahp.person;

import org.apache.commons.math3.distribution.RealDistribution;

public class Suitee extends Person {
    public Suitee(double objectiveAttractivenessScore, RealDistribution preferenceDistribution) {
        super(objectiveAttractivenessScore, preferenceDistribution);
    }

    public ProposalAnswer reviewProposal(Suitor newSuitor) {
        if (newSuitorHasHigherPreferenceRanking(newSuitor)) {
            this.breakUp();
            this.currentPartner = newSuitor;
            return ProposalAnswer.ACCEPT;
        }

        return ProposalAnswer.REJECT;
    }

    private boolean newSuitorHasHigherPreferenceRanking(Suitor newSuitor) {
        if (this.currentPartner == null) {
            return true;
        }

        double newSuitorScore = this.preferenceRanking.getPreferenceScore(newSuitor);
        double currentSuitorScore = this.preferenceRanking.getPreferenceScore(this.currentPartner);
        return newSuitorScore > currentSuitorScore;
    }

    public int getPreferenceIndex(Suitor suitor) {
        int preferenceIndex = this.preferenceRanking.getPreferenceIndexOfPerson(suitor);
        if (preferenceIndex == -1) {
            throw new IndexOutOfBoundsException();
        }

        return preferenceIndex;
    }
}
