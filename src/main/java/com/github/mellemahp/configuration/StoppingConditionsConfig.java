package com.github.mellemahp.configuration;

import lombok.Getter;
import lombok.Setter;

public class StoppingConditionsConfig {
    @Getter
    @Setter
    private int maxEpochs;
    @Getter
    @Setter
    private int epochChangeThreshold;
}