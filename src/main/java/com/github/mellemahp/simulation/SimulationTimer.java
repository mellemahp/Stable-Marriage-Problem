package com.github.mellemahp.simulation;

public class SimulationTimer {
    private Long startTime;
    private Long runTimeInNano;

    public void start() {
        this.startTime = System.nanoTime();
    }

    public void end() {
        if (this.startTime == null) {
            throw new IllegalStateException();
        }
        long endTime = System.nanoTime();
        this.runTimeInNano = endTime - this.startTime;
        this.startTime = null;
    }

    public double getRunTimeInSeconds() {
        return this.runTimeInNano / Math.pow(10, 9);
    }
}