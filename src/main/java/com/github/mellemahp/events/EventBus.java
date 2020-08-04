package com.github.mellemahp.events;

import java.util.Map;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventBus {
    private final Map<Integer, List<Event>> eventsEpochMap = new HashMap<>();
    @Getter
    private int currentEpoch;

    public EventBus() {
        eventsEpochMap.put(0, new ArrayList<>());
        this.currentEpoch = 0;
    }

    
    /** Count the number of instances of the specified type are in the current epoch 
     *  event queue
     * 
     * @param event
     * @return int
     */
    public int countEventsCurrentEpoch(Event event) {
        return countEvents(event, this.currentEpoch);
    }

    
    /** Count the number of instances of the specified type are in the specified
     *  epochs event queue
     * 
     * @param event
     * @param epoch
     * @return int
     */
    public int countEvents(Event event, int epoch) {
        return (int) this.eventsEpochMap.get(epoch)
                .stream()
                .filter(e -> e == event)
                .count();
    }

    public void incrementEpoch() {
        this.currentEpoch++;
        this.eventsEpochMap.put(
                this.currentEpoch,
                new ArrayList<>());
    }

    
    /** Adds a new event to the current epoch's event queue
     * @param event
     */
    public void putEvent(Event event) {
        this.eventsEpochMap.get(this.currentEpoch).add(event);
    }
}