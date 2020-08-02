package com.github.mellemahp.person;

import java.lang.StackWalker.Option;
import java.util.Optional;

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
            
            int prefPartnerIndexBound = (preferedPartner.currentPartner != null)
                ? preferedPartner.getPreferenceIndex() 
                : preferedPartner.getPreferenceListSize();

            for (int j = 0; j < prefPartnerIndexBound; j++) {
                int rankingInPrefPartnerPrefList = preferedPartner.preferenceRanking.getPreferenceIndexOfPerson(this);
                if (rankingInPrefPartnerPrefList < prefPartnerIndexBound) { 
                    return true;
                }
            }
        }

        return false;

        // Iterate over all possible partners that are higher up in the preference ranking
/*         for (int i = 0; i < this.preferenceIndex; i++) {
            Person preferredPartner = this.preferenceRanking.getPerson(i);

            // Ranking preferredPartner's partner
            int rankPartnerOfPreferredPartner = preferredPartner.getPreferenceIndex();

            // Ranking of this Person in preferredPartner's preference ranking
            int rankThisPerson = preferredPartner.preferenceRanking.getPreferenceIndexOfPerson(this);
            
            if (rankThisPerson < rankPartnerOfPreferredPartner) {
                // The preferredPartner also would rather be with this Person
                return true;
            }
        } */

        //return false;
    }
}