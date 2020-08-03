package com.github.mellemahp.configuration;

import com.github.mellemahp.distribution.DistributionType;

import java.util.Map;

import lombok.Setter;
import lombok.Getter;

public class DistributionConfig {
    @Getter
    @Setter
    private DistributionType distributionType;
    @Getter
    @Setter
    private Map<String, Double> properties;
}