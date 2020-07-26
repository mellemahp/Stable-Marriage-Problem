package com.github.mellemahp.person;

import org.apache.commons.math3.distribution.RealDistribution;

public class Person {
    protected Person currentPartner;
    protected PreferenceRanking preferenceRanking;
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
}