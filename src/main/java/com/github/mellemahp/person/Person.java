package com.github.mellemahp.person;

import org.apache.commons.math3.distribution.RealDistribution;

public class Person {
    protected Person currentPartner;
    public PreferenceRanking preferenceRanking;
    protected int preferenceIndex = 0;
    protected double objectiveAttractivenessScore;
    protected RealDistribution preferenceDistribution;
    
    public Person(double objectiveAttractivenessScore, RealDistribution preferenceDistribution) {
        this.objectiveAttractivenessScore = objectiveAttractivenessScore;
        this.preferenceDistribution = preferenceDistribution;
        this.preferenceRanking = new PreferenceRanking();
    }

    public int getPreferenceIndex() {
        return this.preferenceIndex;
    }

    public <T extends Person> void initializePreferences(PersonList<T> personList) {
        for (T person : personList) {
            double firstImpressionScore = person.objectiveAttractivenessScore + this.preferenceDistribution.sample();
            this.preferenceRanking.add(
                new Preference(person, firstImpressionScore)
            );
        }
    }

    public Person getCurrentPartner() {
        return this.currentPartner;
    }

    protected void breakUp() {
        if (this.currentPartner != null) {
            this.currentPartner.currentPartner = null;
            this.currentPartner = null;
        }
    }

    @Override
    public String toString() {
        return String.format("%s: %.4s", getClass().getSimpleName(), this.hashCode());
    }

    public String getPreferenceRankingString() {
        return this.preferenceRanking.toString();
    }

    public boolean hasBetterPartnerOption() {
        if (this.currentPartner == null) {
            // TODO: Need to check if all potential partners have no currentPartner
            //
            // If at least one potential partner has no current partner,
            // then return true
            // else, return false
            // For now, it's safe to return true for the stable marriage problem

            return true;
        }

        // Iterate over all possible partners that are higher up in the preference ranking
        for (int i = 0; i < this.preferenceIndex; i++) {
            Person preferredPartner = this.preferenceRanking.getPerson(i);

            // Ranking preferredPartner's partner

            int rankPartnerOfPreferredPartner = preferredPartner.getPreferenceIndex();

            // Ranking of this Person in preferredPartner's preference ranking
            int rankThisPerson = preferredPartner.preferenceRanking.getPreferenceIndexOfPerson(this);
            
            if (rankThisPerson < rankPartnerOfPreferredPartner) {
                // The preferredPartner also would rather be with this Person
                return true;
            }
        }

        return false;
    }
}