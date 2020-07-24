package com.eliasnogueira.credit.test;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.port;
import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.config.RestAssuredConfig.newConfig;

import com.aventstack.extentreports.testng.listener.ExtentITestListenerClassAdapter;
import com.eliasnogueira.credit.config.Configuration;
import com.eliasnogueira.credit.config.ConfigurationManager;
import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.path.json.config.JsonPathConfig.NumberReturnType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

@Listeners({ExtentITestListenerClassAdapter.class})
public abstract class BaseAPI {

    protected Configuration configuration;

    @BeforeClass(alwaysRun = true)
    public void before() {
        configuration = ConfigurationManager.getConfiguration();

        baseURI = configuration.baseURI();
        basePath = configuration.basePath();
        port = configuration.port();

        // solve the problem with big decimal assertions
        RestAssured.config = newConfig().
            jsonConfig(jsonConfig().numberReturnType(NumberReturnType.BIG_DECIMAL)).
            sslConfig(new SSLConfig().allowAllHostnames());

        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
