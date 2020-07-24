package com.eliasnogueira.credit.specs;

import com.eliasnogueira.credit.config.Configuration;
import com.eliasnogueira.credit.config.ConfigurationManager;
import com.eliasnogueira.credit.test.BaseAPI;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class InitialStateSpecs extends BaseAPI {

    public static RequestSpecification set() {
        Configuration configuration = ConfigurationManager.getConfiguration();

        return new RequestSpecBuilder().
            setBaseUri(configuration.baseURI()).
            setBasePath(configuration.basePath()).
            setPort(configuration.port()).
            build();
    }
}
