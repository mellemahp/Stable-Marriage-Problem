package com.github.mellemahp.simulation;

import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.util.Map;

import com.github.mellemahp.distribution.BetaDistributionSettings;
import com.github.mellemahp.distribution.DistributionSettings;
import com.github.mellemahp.distribution.NormalDistributionSettings;

import org.yaml.snakeyaml.Yaml;

public class SimulationConfig {
    private int numberOfSuitors;
    private int numberOfSuitees;
    private DistributionSettings suitorDistribution;
    private DistributionSettings suiteeDistribution;
    private DistributionSettings preferenceDistribution;

    public SimulationConfig(InputStream configFileStream) {
        Yaml yaml = new Yaml();
        Map<String, Map<String, Object>> configs = yaml.load(configFileStream);
        Map<String, Object> suiteeConfigs = configs.get("SuiteeConfigs"); 
        Map<String, Object> suitorConfigs = configs.get("SuiteeConfigs"); 
        Map<String, Object> preferenceConfigs = configs.get("PreferenceConfigs");
        
        this.numberOfSuitors = (int) suitorConfigs.get("numberOfSuitors");
        this.numberOfSuitees = (int) suiteeConfigs.get("numberOfSuitees");
        this.suiteeDistribution = createDistributionSettingsFromConfig((Map<String, Object>) suiteeConfigs.get("suiteeDistribution"));
        this.preferenceDistribution = createDistributionSettingsFromConfig((Map<String, Object>) suitorConfigs.get("suitorDistribution"));
        this.suitorDistribution = createDistributionSettingsFromConfig((Map<String, Object>) preferenceConfigs.get("preferenceDistribution"));
    }

    private DistributionSettings createDistributionSettingsFromConfig(Map<String, Object> distributionConfig) throws InvalidParameterException { 
        String distType = (String) distributionConfig.get("DistributionType"); 
        switch(distType){
            case "Normal":
                return new NormalDistributionSettings(
                    (double) distributionConfig.get("mean"), 
                    (double) distributionConfig.get("standardDeviation"));
            case "Beta":
                return new BetaDistributionSettings(
                    (double) distributionConfig.get("alpha"), 
                    (double) distributionConfig.get("beta"));
            default: 
                throw new InvalidParameterException(String.format("Distribution type {} not valid", distType));
        }
    }

    public int getNumberOfSuitors() {
        return numberOfSuitors;
    }

    public int getNumberOfSuitees() {
        return numberOfSuitees;
    }

    public DistributionSettings getSuitorDistribution() {
        return suitorDistribution;
    }

    public DistributionSettings getSuiteeDistribution() {
        return suiteeDistribution;
    }

    public DistributionSettings getPreferenceDistribution() {
        return preferenceDistribution;
    }
}
