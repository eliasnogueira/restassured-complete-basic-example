package com.eliasnogueira.credit.config;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:conf/${environment}.properties"})
public interface Configuration extends Config {

    @Key("api.base.path")
    String basePath();

    @Key("api.base.uri")
    String baseURI();

    @Key("api.port")
    int port();

    @Key("api.health.context")
    String health();
}
