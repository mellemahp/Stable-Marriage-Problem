package com.github.mellemahp.simulation;

import com.github.mellemahp.events.EventBus;

public abstract class Simulator {
    protected final EventBus bus = new EventBus();
    protected static final String LONGSEP = "=====================================";

    public abstract int run();
    public abstract void printResults();
}
