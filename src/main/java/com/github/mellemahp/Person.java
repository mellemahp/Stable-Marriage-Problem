package com.github.mellemahp;

import org.apache.commons.math3.distribution.RealDistribution;

public abstract class Person {
    private Person currentPartner;
    private PreferenceRanking preferenceRanking;
    private int preferenceIndex = 0;
    private double objectiveAttractivenessScore;
    private RealDistribution preferenceDistribution;
    
    public Person(double objectiveAttractivenessScore, RealDistribution preferenceDistribution) {
        this.objectiveAttractivenessScore = objectiveAttractivenessScore;
        this.preferenceDistribution = preferenceDistribution;
        this.preferenceRanking = new PreferenceRanking();
    }

    public abstract void propose();

    public abstract void reviewProposal();

    public int getPreferenceIndex() {
        return this.preferenceIndex;
    }

    public <T extends Person> void initializePreferences(PersonList<T> personList) {
        for (Person person : personList) {
            double firstImpressionScore = person.objectiveAttractivenessScore + this.preferenceDistribution.sample();
            this.preferenceRanking.add(
                new Preference(person, firstImpressionScore)
            );
        }
    }

    public Person getCurrentPartner() {
        return this.currentPartner;
    }
}