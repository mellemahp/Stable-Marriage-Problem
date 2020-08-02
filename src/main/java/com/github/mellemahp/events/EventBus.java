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

    public int count_events_current_epoch(Event event) { 
        return count_events(event, this.currentEpoch);
    }

    public int count_events(Event event, int epoch) {
        return (int) this.eventsEpochMap.get(epoch)
            .stream()
            .filter(e -> e == event)
            .count();
    }

    public void increment_epoch() { 
        this.currentEpoch++;
        this.eventsEpochMap.put(
            this.currentEpoch, 
            new ArrayList<Event>()
        );
    }

    public void put_event(Event event) {
        this.eventsEpochMap.get(this.currentEpoch).add(event);
    }

    public int getCurrentEpoch() { 
        return this.currentEpoch;
    }
}