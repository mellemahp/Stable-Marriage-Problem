package com.github.mellemahp.person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.NonNull;

public class PreferenceRanking {
    private List<Preference> preferenceList;
    private Map<Person, Preference> preferenceMap;

    public PreferenceRanking() {
        this.preferenceList = new ArrayList<>();
        this.preferenceMap = new HashMap<>();
    }

    public void add(@NonNull Preference preference) {
        // Add to map
        this.preferenceMap.put(preference.getPerson(), preference);

        // Add to sorted list
        this.preferenceList.add(preference);
        this.preferenceList.sort((p1, p2) -> {
            Double p1Score = p1.getPreferenceScore(0);
            Double p2Score = p2.getPreferenceScore(0);

            return p2Score.compareTo(p1Score);
        });
    }

    public double getPreferenceScore(@NonNull Person person) {
        return this.preferenceMap.get(person).getPreferenceScore(0);
    }

    public Person getPerson(int index) {
        return this.preferenceList.get(index).getPerson();
    }

    public int getPreferenceIndexOfPerson(@NonNull Person person) {
        // NOTE: There is no equals implementation for Person, so it will use the memory
        // address when using equals. We will need to implement equals for the Person class.
        return IntStream.range(0, this.preferenceList.size())
                .filter(i -> this.preferenceList.get(i).getPerson().equals(person))
                .findFirst()
                .getAsInt();
    }

    @Override
    public String toString() {
        return "[" + this.preferenceList
                .stream()
                .map(Preference::getPerson)
                .map(Person::hashCode)
                .map(String::valueOf)
                .map(s -> s.substring(0, 4))
                .collect(Collectors.joining(", ")) + "]";
    }

    public int size() {
        return this.preferenceList.size();
    }

}