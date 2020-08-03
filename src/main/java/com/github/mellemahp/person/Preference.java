package com.github.mellemahp.person;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class Preference {
    @Getter
    private Person person;
    private final List<Double> preferenceScore = new ArrayList<>();

    public Preference(Person person, double firstImpressionScore) {
        this.person = person;
        this.preferenceScore.add(firstImpressionScore);
    }

    public double getPreferenceScore(int index) {
        return this.preferenceScore.get(index);
    }
}
