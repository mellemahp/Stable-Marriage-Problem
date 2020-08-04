package com.github.mellemahp.person;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NonNull;

public class Preference {
    @Getter
    private Person person;
    private final List<Double> preferenceScore = new ArrayList<>();

    public Preference(@NonNull Person person,
            @NonNull double firstImpressionScore) {
        this.person = person;
        this.preferenceScore.add(firstImpressionScore);
    }

    public double getPreferenceScore(@NonNull int index) {
        return this.preferenceScore.get(index);
    }
}
