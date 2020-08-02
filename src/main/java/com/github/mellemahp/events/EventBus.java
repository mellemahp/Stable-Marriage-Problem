package com.github.mellemahp.events;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventBus {
    private Map<Integer, List<Event>> eventsEpochMap;
    private int currentEpoch;

    public EventBus() {
        this.eventsEpochMap = new HashMap<>();
        this.eventsEpochMap.put(0, new ArrayList<>());
        this.currentEpoch = 0;
    }

    public int countEventsCurrentEpoch(Event event) {
        return countEvents(event, this.currentEpoch);
    }

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

    public void putEvent(Event event) {
        this.eventsEpochMap.get(this.currentEpoch).add(event);
    }

    public int getCurrentEpoch() {
        return this.currentEpoch;
    }
}