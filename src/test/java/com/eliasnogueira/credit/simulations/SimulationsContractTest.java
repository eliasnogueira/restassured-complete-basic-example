package com.eliasnogueira.credit.simulations;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import com.eliasnogueira.credit.data.factory.SimulationDataFactory;
import com.eliasnogueira.credit.test.BaseAPI;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SimulationsContractTest extends BaseAPI {

    private SimulationDataFactory simulationDataFactory;

    @BeforeClass(alwaysRun = true)
    public void setup() {
        simulationDataFactory = new SimulationDataFactory();
    }

    @Test(groups = "contract")
    public void getOneSimulation() {
        String existentCpf = simulationDataFactory.oneExistingSimulation().getCpf();
        given().
            pathParam("cpf", existentCpf).
        when().
            get("/simulations/{cpf}").
        then().
            body(matchesJsonSchemaInClasspath("schemas/simulations_v1_schema.json"));
    }

    @Test(groups = "contract")
    public void simulationNotFound() {
        given().
            pathParam("cpf", simulationDataFactory.notExistentCpf()).
        when().
            get("/simulations/{cpf}").
        then().
            body(matchesJsonSchemaInClasspath("schemas/simulations_not_existent_v1_schema.json"));
    }

    @Test(groups = "contract")
    public void simulationWithMissingInformation() {
        given().
            contentType(ContentType.JSON).
            body(simulationDataFactory.missingAllInformation()).
        when().
            post("/simulations").
        then().
            body(matchesJsonSchemaInClasspath("schemas/simulations_missing_info_v1_schema.json"));
    }
}
