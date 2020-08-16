package com.github.mellemahp.data_collection;

import com.github.mellemahp.sqlite_data_processing.SQLiteDataContainer;

public class PoisonVial {
    private SQLiteDataContainer[] poisonPillArray;
    private int capacity;
    private int nextIndex;

    public PoisonVial(int capacity) { 
        this.capacity = capacity;
        this.nextIndex = 0;
        this.poisonPillArray = new SQLiteDataContainer[capacity];
    }

    public void addPoisonPill(SQLiteDataContainer pill) { 
        if (pill instanceof PoisonPill) {
            this.poisonPillArray[nextIndex] = pill;
            this.nextIndex++;
        } else { 
            throw new IllegalArgumentException("Data Container is not a poison pill");
        }
    }

    public boolean isNotFull() { 
        return this.nextIndex < capacity;
    }

    public int getSize() {
        return this.nextIndex;
    }
}