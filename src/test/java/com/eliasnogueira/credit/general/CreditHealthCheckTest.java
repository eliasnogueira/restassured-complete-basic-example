package com.eliasnogueira.credit.general;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.when;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.is;

import com.eliasnogueira.credit.config.ConfigurationManager;
import com.eliasnogueira.credit.test.BaseAPI;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CreditHealthCheckTest extends BaseAPI {

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        configuration = ConfigurationManager.getConfiguration();
        basePath = configuration.health();
    }

    @AfterMethod
    public void tearDown() {
        basePath = configuration.basePath();
    }

    @Test(groups = "health")
    public void healthCheck() {
        when().
            get("/health").
        then().
            statusCode(SC_OK).
            body("status", is("UP"));
    }
}
