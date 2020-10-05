/*
 * MIT License
 *
 * Copyright (c) 2020 Elias Nogueira
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.eliasnogueira.credit.simulations;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import com.eliasnogueira.credit.data.factory.SimulationDataFactory;
import com.eliasnogueira.credit.base.BaseAPI;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class SimulationsContractTest extends BaseAPI {

    private static SimulationDataFactory simulationDataFactory;

    @BeforeAll
    static void setup() {
        simulationDataFactory = new SimulationDataFactory();
    }

    @Test
    @Tag("contract")
    @DisplayName("Should validate the simulation schema for GET method")
    void getOneSimulation() {
        String existentCpf = simulationDataFactory.oneExistingSimulation().getCpf();
        given().
            pathParam("cpf", existentCpf).
        when().
            get("/simulations/{cpf}").
        then().
            body(matchesJsonSchemaInClasspath("schemas/simulations_v1_schema.json"));
    }

    @Test
    @Tag("contract")
    @DisplayName("Should validate the simulation schema for non-existing simulation")
    void simulationNotFound() {
        given().
            pathParam("cpf", simulationDataFactory.notExistentCpf()).
        when().
            get("/simulations/{cpf}").
        then().
            body(matchesJsonSchemaInClasspath("schemas/simulations_not_existent_v1_schema.json"));
    }

    @Test
    @Tag("contract")
    @DisplayName("Should validate the simulation schema for missing information")
    void simulationWithMissingInformation() {
        given().
            contentType(ContentType.JSON).
            body(simulationDataFactory.missingAllInformation()).
        when().
            post("/simulations").
        then().
            body(matchesJsonSchemaInClasspath("schemas/simulations_missing_info_v1_schema.json"));
    }
}
