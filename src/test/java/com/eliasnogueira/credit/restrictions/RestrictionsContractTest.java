package com.eliasnogueira.credit.restrictions;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import com.eliasnogueira.credit.data.factory.RestrictionDataFactory;
import com.eliasnogueira.credit.test.BaseAPI;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RestrictionsContractTest extends BaseAPI {

    private RestrictionDataFactory restrictionDataFactory;

    @BeforeClass(alwaysRun = true)
    public void setup() {
        restrictionDataFactory = new RestrictionDataFactory();
    }

    @Test(groups = "contract")
    public void contractOnV1() {
        given().
            pathParam("cpf", restrictionDataFactory.cpfWithRestriction()).
        when().
            get("/restrictions/{cpf}").
        then().
            body(matchesJsonSchemaInClasspath("schemas/restrictions_v1_schema.json"));
    }

    /*
     * We are overriding the basePath to v2 in the test first line
     *
     * You can enable the test (setting true) and see the errors because the response body is
     * different on v2
     */
    @Test(groups = "contract", enabled = false)
    public void contractOnV2() {
        basePath = "/api/v2";

        given().
            pathParam("cpf", restrictionDataFactory.cpfWithRestriction()).
        when().
            get("/restrictions/{cpf}").
        then().
            body(matchesJsonSchemaInClasspath("schemas/restrictions_v1_schema.json"));
    }
}
