package com.eliasnogueira.credit.client;

import static io.restassured.RestAssured.given;

import com.eliasnogueira.credit.specs.RestrictionsSpecs;

public class RestrictionsClient {

    public void queryRestrictionAndReturnNotFound() {
        given().
            spec(RestrictionsSpecs.cpfWithoutRestrictionRequestSpec()).
        when().
            get("/restrictions/{cpf}").
        then().
            spec(RestrictionsSpecs.notFoundResponse());
    }
}
