package com.github.mellemahp.data_collection;

public class PoisonVial {
    private DataContainer[] poisonPillArray;
    private int capacity;
    private int nextIndex;

    public PoisonVial(int capacity) { 
        this.capacity = capacity;
        this.nextIndex = 0;
        this.poisonPillArray = new DataContainer[capacity];
    }

    public void addPoisonPill(DataContainer pill) { 
        if (pill instanceof PoisonPill) {
            this.poisonPillArray[nextIndex] = pill;
            this.nextIndex++;
        } else { 
            throw new IllegalArgumentException("Data Container is not a poison pill");
        }
    }

    public boolean isNotFull() { 
        return this.nextIndex != capacity;
    }
}