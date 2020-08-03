package com.github.mellemahp.configuration;

import lombok.Getter;
import lombok.Setter;

public class PersonConfig {
    @Getter
    @Setter
    private int numberOfPeople;
    @Getter
    @Setter
    private DistributionConfig distribution;
}