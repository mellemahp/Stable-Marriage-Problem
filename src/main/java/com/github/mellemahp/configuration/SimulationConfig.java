package com.github.mellemahp.configuration;

public class SimulationConfig {
    private StoppingConditionsConfig stoppingConditionsConfig;
    private PersonConfig suitorConfig;
    private PersonConfig suiteeConfig;
    private PreferenceConfig preferenceConfig;

    public void setSuitorConfig(PersonConfig suitorConfig) { 
        this.suitorConfig = suitorConfig;
    }

    public PersonConfig getSuitorConfig() { 
        return this.suitorConfig;
    }

    public void setSuiteeConfig(PersonConfig suiteeConfig) { 
        this.suiteeConfig = suiteeConfig;
    }

    public PersonConfig getSuiteeConfig() { 
        return this.suiteeConfig;
    }

    public void setPreferenceConfig(PreferenceConfig preferenceConfig) { 
        this.preferenceConfig = preferenceConfig;
    }

    public PreferenceConfig getPreferenceConfig() { 
        return this.preferenceConfig;
    }

    public void setStoppingConditionsConfig(StoppingConditionsConfig stoppingConditionsConfig) { 
        this.stoppingConditionsConfig = stoppingConditionsConfig;
    }
    
    public StoppingConditionsConfig getStoppingConditionsConfig() { 
        return this.stoppingConditionsConfig;
    }

}