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

    public int getPreferenceListSize() { 
        return this.preferenceRanking.size();
    }

    public boolean hasBetterPartnerOption() {
        int indexBound = (this.currentPartner != null)
            ? this.getPreferenceIndex() 
            : this.getPreferenceListSize();

        for (int i = 0; i < indexBound; i++) { 
            Person preferedPartner = this.preferenceRanking.getPerson(i);
            
            // Rank of the preferred partner's partner
            int prefPartnerIndexBound = (preferedPartner.currentPartner != null)
                ? preferedPartner.getPreferenceIndex() 
                : preferedPartner.getPreferenceListSize();
            
            // Rank of this in the partner's preference list
            int prefPartnerThisPersonRank = preferedPartner.preferenceRanking.getPreferenceIndexOfPerson(this);

            if (prefPartnerThisPersonRank < prefPartnerIndexBound) {
                return true;
            }
        }

        return false;
    }
}