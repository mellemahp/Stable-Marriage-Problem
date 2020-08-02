package com.github.mellemahp.person;

import java.util.ArrayList;
import java.util.List;

public class Preference {
    private Person person;
    private List<Double> preferenceScore;

    public Preference(Person person, double firstImpressionScore) {
        this.person = person;
        this.preferenceScore = new ArrayList<>();
        this.preferenceScore.add(firstImpressionScore);
    }

    public double getPreferenceScore(int index) {
        return this.preferenceScore.get(index);
    }

    public Person getPerson() {
        return this.person;
    }
}
