package com.eliasnogueira.credit.config;

import org.aeonbits.owner.ConfigCache;
import org.aeonbits.owner.ConfigFactory;

public class ConfigurationManager {

    private static final String ENVIRONMENT = "environment";

    public static Configuration getConfiguration() {
        setEnvironment();
        return ConfigCache.getOrCreate(Configuration.class);
    }

    private static void setEnvironment() {
        String environment = System.getProperty(ENVIRONMENT);
        String actualEnv = environment == null ? "dev" : environment;

        System.setProperty(ENVIRONMENT, actualEnv);
        ConfigFactory.setProperty(ENVIRONMENT, actualEnv);
    }
}
