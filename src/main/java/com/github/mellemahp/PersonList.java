package com.github.mellemahp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

import org.apache.commons.math3.distribution.RealDistribution;

public class PersonList<T extends Person> implements Iterable<T> {
    private List<T> listOfPersons;

    public PersonList(int numberOfPersons, RealDistribution objectiveAttractivenessDistribution, RealDistribution preferenceDistribution) {
        this.listOfPersons = this.generateListOfPersons(numberOfPersons, objectiveAttractivenessDistribution, preferenceDistribution);
    }

    
    private List<T> generateListOfPersons(int numberOfPersons, RealDistribution objectiveAttractivenessDistribution, RealDistribution preferenceDistribution) {
        List<T> listOfPersons = new ArrayList<>();

        for (int i = 0; i < numberOfPersons; i++) {
            double objectiveAttractivenessScore = objectiveAttractivenessDistribution.sample();

            // TODO: This is currently not valid! We cannot create an instance of T. Furthermore,
            // we cannot just construct and instance of type Person because Person is an abstract class
            // Need to think about how to generate a Suitor/Suitee without specifying its type.
            // listOfPersons.add(new T(objectiveAttractivenessScore, preferenceDistribution));
        }

        return listOfPersons;
    }

    @Override
    public Iterator<T> iterator() {
        // TODO Auto-generated method stub
        return null;
    }
    
}