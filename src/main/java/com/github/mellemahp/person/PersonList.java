package com.github.mellemahp.person;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.math3.distribution.RealDistribution;

public class PersonList<T extends Person> implements Iterable<T> {
    private int numberOfPersons;
    private RealDistribution objectiveAttractivenessDistribution;
    private RealDistribution preferenceDistribution;

    private PersonSupplier<T> personSupplier;

    private List<T> listOfPersons;

    public PersonList(int numberOfPersons, RealDistribution objectiveAttractivenessDistribution, RealDistribution preferenceDistribution) {
        this.numberOfPersons = numberOfPersons;
        this.objectiveAttractivenessDistribution = objectiveAttractivenessDistribution;
        this.preferenceDistribution = preferenceDistribution;
    }
    
    public void build() {
        List<T> listOfPersons = new ArrayList<>();

        for (int i = 0; i < this.numberOfPersons; i++) {
            double score = this.objectiveAttractivenessDistribution.sample();
            listOfPersons.add(
                this.personSupplier.setScore(score)
                    .setPreferenceDistribution(preferenceDistribution)
                    .get()
            );
        }

        this.listOfPersons = listOfPersons;
    }

    public PersonList<T> with(PersonSupplier<T> personSupplier) {
        this.personSupplier = personSupplier;
        return this;
    }
    
    @Override
    public Iterator<T> iterator() {
        return this.listOfPersons.iterator();
    }

    public <E extends Person> void initializePreferenceList(PersonList<E> otherPersonList) {
        this.forEach(person -> person.initializePreferences(otherPersonList));
    }

    public boolean hasUnpairedPerson() {
        return this.listOfPersons.stream()
            .anyMatch(person -> person.getCurrentPartner() == null);
    }

    @Override
    public String toString() {
        return this.listOfPersons
            .stream()
            .map(suitor -> suitor.hashCode())
            .map(String::valueOf)
            .map(s -> s.substring(0, 4))
            .collect(Collectors.joining(" | "));
    }
}