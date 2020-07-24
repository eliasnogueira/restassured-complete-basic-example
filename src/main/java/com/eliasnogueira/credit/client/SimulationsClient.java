package com.eliasnogueira.credit.client;

import static io.restassured.RestAssured.given;

import com.eliasnogueira.credit.specs.SimulationsSpecs;

public class SimulationsClient {

    public void submitSuccessfulSimulation() {
        given().
            spec(SimulationsSpecs.postValidSimulation()).
        when().
            post("/simulations").
        then().
            spec(SimulationsSpecs.createdSimulation());
    }
}
