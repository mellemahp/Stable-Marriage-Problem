package com.github.mellemahp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.math3.distribution.RealDistribution;

public class PersonList implements Iterable<Person> {
    private List<Person> listOfPersons;

    public PersonList(int numberOfPersons, RealDistribution objectiveAttractivenessDistribution, RealDistribution preferenceDistribution) {
        this.listOfPersons = this.generateListOfPersons(numberOfPersons, objectiveAttractivenessDistribution, preferenceDistribution);
    }

    
    private List<Person> generateListOfPersons(int numberOfPersons, RealDistribution objectiveAttractivenessDistribution, RealDistribution preferenceDistribution) {
        List<Person> listOfPersons = new ArrayList<>();

        for (int i = 0; i < numberOfPersons; i++) {
            double objectiveAttractivenessScore = objectiveAttractivenessDistribution.sample();

            listOfPersons.add(new Person(objectiveAttractivenessScore, preferenceDistribution));
        }

        return listOfPersons;
    }

    @Override
    public Iterator<Person> iterator() {
        return this.listOfPersons.iterator();
    }
    
}