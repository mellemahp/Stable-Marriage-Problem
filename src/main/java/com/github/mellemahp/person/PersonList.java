package com.github.mellemahp.person;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.github.mellemahp.events.EventBus;

import org.apache.commons.math3.distribution.RealDistribution;

import lombok.NonNull;

public class PersonList<T extends Person> implements Iterable<T> {
    private int numberOfPersons;
    private RealDistribution objectiveAttractivenessDistribution;
    private RealDistribution preferenceDistribution;
    private PersonSupplier<T> personSupplier;
    private List<T> listOfPersons;
    private EventBus bus;

    public PersonList(int numberOfPersons,
            @NonNull RealDistribution objectiveAttractivenessDistribution,
            @NonNull RealDistribution preferenceDistribution,
            @NonNull EventBus bus) {
        this.numberOfPersons = numberOfPersons;
        this.objectiveAttractivenessDistribution = objectiveAttractivenessDistribution;
        this.preferenceDistribution = preferenceDistribution;
        this.bus = bus;
    }

    public void build() {
        List<T> listOfPersonsTemp = new ArrayList<>();

        for (int i = 0; i < this.numberOfPersons; i++) {
            double score = this.objectiveAttractivenessDistribution.sample();
            listOfPersonsTemp.add(
                    this.personSupplier.withScore(score)
                            .withBus(this.bus)
                            .withPreferenceDistribution(this.preferenceDistribution)
                            .get());
        }

        this.listOfPersons = listOfPersonsTemp;
    }

    public PersonList<T> with(@NonNull PersonSupplier<T> personSupplier) {
        this.personSupplier = personSupplier;
        return this;
    }

    @Override
    public Iterator<T> iterator() {
        return this.listOfPersons.iterator();
    }

    public <E extends Person> void initializePreferenceList(@NonNull PersonList<E> otherPersonList) {
        this.forEach(person -> person.initializePreferences(otherPersonList));
    }

    public boolean hasUnpairedPerson() {
        return this.listOfPersons.stream().anyMatch(person -> person.getCurrentPartner() == null);
    }

    @Override
    public String toString() {
        return this.listOfPersons.stream()
                .map(Person::hashCode)
                .map(String::valueOf)
                .map(s -> s.substring(0, 4)).collect(Collectors.joining(" | "));
    }
}
