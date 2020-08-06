package com.github.mellemahp.person;

import com.github.mellemahp.events.EventBus;

import org.apache.commons.math3.distribution.RealDistribution;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@EqualsAndHashCode
public class Person {
    @EqualsAndHashCode.Exclude
    protected final EventBus bus;
    @Getter
    protected final PreferenceRanking preferenceRanking = new PreferenceRanking();
    @Getter
    @EqualsAndHashCode.Exclude
    protected int preferenceIndex = 0;
    @Getter
    @EqualsAndHashCode.Exclude
    protected Person currentPartner;
    @Getter
    protected double objectiveAttractivenessScore;
    @EqualsAndHashCode.Exclude
    protected RealDistribution preferenceDistribution;

    public Person(double objectiveAttractivenessScore,
            @NonNull RealDistribution preferenceDistribution,
            @NonNull EventBus eventBus) {
        this.objectiveAttractivenessScore = objectiveAttractivenessScore;
        this.preferenceDistribution = preferenceDistribution;
        bus = eventBus;
    }

    public <T extends Person> void initializePreferences(@NonNull PersonList<T> personList) {
        for (T person : personList) {
            double firstImpressionScore = person.objectiveAttractivenessScore + this.preferenceDistribution.sample();
            preferenceRanking.add(new Preference(person, firstImpressionScore));
        }
    }

    protected void breakUp() {
        if (this.currentPartner != null) {
            this.currentPartner.endRelationshipWith(this);
            this.endRelationshipWith(this.currentPartner);
        }
    }

    protected void endRelationshipWith(@NonNull Person person) {
        if (!person.equals(this.currentPartner)) {
            throw new IllegalArgumentException(String.format("%s is not the current partner", person));
        }
        this.currentPartner = null;
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

    public int getIndexToSearchForBetterPartnerBelow() {
        return (this.currentPartner != null)
                ? this.getPreferenceIndex()
                : this.getPreferenceListSize();
    }

    public int getPreferenceIndexOfPerson(Person person) {
        return this.preferenceRanking.getPreferenceIndexOfPerson(person);
    }

    public Person getPersonByPrefenceRanking(int index) {
        return this.preferenceRanking.getPerson(index);
    }

    public boolean hasBetterPartnerOption() {
        int indexUpperBound = getIndexToSearchForBetterPartnerBelow();

        for (int i = 0; i < indexUpperBound; i++) {
            Person preferedPartner = this.preferenceRanking.getPerson(i);
            int prefPartnerIndexUpperBound = preferedPartner.getIndexToSearchForBetterPartnerBelow();
            int prefPartnerThisPersonRank = preferedPartner.getPreferenceIndexOfPerson(this);

            if (prefPartnerThisPersonRank < prefPartnerIndexUpperBound) {
                return true;
            }
        }
        return false;
    }
}