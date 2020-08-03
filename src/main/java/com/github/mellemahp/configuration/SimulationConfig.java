package com.github.mellemahp.configuration;

import lombok.Getter;
import lombok.Setter;

public class SimulationConfig {
    @Getter
    @Setter
    private StoppingConditionsConfig stoppingConditionsConfig;
    @Getter
    @Setter
    private PersonConfig suitorConfig;
    @Getter
    @Setter
    private PersonConfig suiteeConfig;
    @Getter
    @Setter
    private PreferenceConfig preferenceConfig;
}