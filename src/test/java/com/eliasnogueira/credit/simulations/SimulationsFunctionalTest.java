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

import static com.eliasnogueira.credit.data.suite.TestTags.FUNCTIONAL;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.eliasnogueira.credit.commons.MessageFormat;
import com.eliasnogueira.credit.model.Simulation;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class SimulationsFunctionalTest extends SimulationsBase {

    private static final String FAILED_VALIDATION =
        "com.eliasnogueira.credit.data.provider.SimulationDataProvider#failedValidations";

    /*
     * not that, in order to assert the amount without problem, we must enable a configuration
     * it's located at BaseAPI class
     */
    @Test
    @Tag(FUNCTIONAL)
    @DisplayName("Should validate one existing simulation")
    void getOneExistingSimulation() {
        Simulation existingSimulation = simulationDataFactory.oneExistingSimulation();

        given().
            pathParam("cpf", existingSimulation.getCpf()).
        when().
            get("/simulations/{cpf}").
        then().
            statusCode(HttpStatus.SC_OK).
            body(
                "name", equalTo(existingSimulation.getName()),
                "cpf", equalTo(existingSimulation.getCpf()),
                "email", equalTo(existingSimulation.getEmail()),
                "amount", equalTo(existingSimulation.getAmount()),
                "installments", equalTo(existingSimulation.getInstallments()),
                "insurance", equalTo(existingSimulation.getInsurance())
            );
    }

    @Test
    @Tag(FUNCTIONAL)
    @DisplayName("Should validate all existing simulations")
    void getAllExistingSimulations() {
        Simulation[] existingSimulations = simulationDataFactory.allExistingSimulations();

        Simulation[] simulationsRequested =
            when().
                get("simulations/").
            then().
                statusCode(HttpStatus.SC_OK).
                extract().
                   as(Simulation[].class);

        assertThat(existingSimulations, Matchers.arrayContaining(simulationsRequested));
    }

    @Test
    @Tag(FUNCTIONAL)
    @DisplayName("Should filter by name a non-existing simulation")
    void simulationByNameNotFound() {
        given().
            queryParam("name", simulationDataFactory.nonExistentName()).
        when().
            get("/simulations").
        then().
            statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    @Tag(FUNCTIONAL)
    @DisplayName("Should find a simulation filtered by name")
    void returnSimulationByName() {
        Simulation existingSimulation = simulationDataFactory.oneExistingSimulation();

        given().
            queryParam("name", existingSimulation.getName()).
        when().
            get("/simulations").
        then().
            statusCode(HttpStatus.SC_OK).
            body(
                "[0].name", equalTo(existingSimulation.getName()),
                "[0].cpf", equalTo(existingSimulation.getCpf()),
                "[0].email", equalTo(existingSimulation.getEmail()),
                "[0].amount", equalTo(existingSimulation.getAmount()),
                "[0].installments", equalTo(existingSimulation.getInstallments()),
                "[0].insurance", equalTo(existingSimulation.getInsurance())
            );
    }

    /*
     * here there is an header validation
     */
    @Test
    @Tag(FUNCTIONAL)
    @DisplayName("Should create a new simulation")
    void createNewSimulationSuccessfully() {
        Simulation simulation = simulationDataFactory.validSimulation();

        given().
            contentType(ContentType.JSON).
            body(simulation).
        when().
            post("/simulations").
        then().
            statusCode(HttpStatus.SC_CREATED).
            header("Location", containsString(MessageFormat.locationURLByEnvironment()));
    }

    @Tag(FUNCTIONAL)
    @ParameterizedTest(name = "Scenario: {2}")
    @MethodSource(value = FAILED_VALIDATION)
    @DisplayName("Should validate all the invalid scenarios")
    void invalidSimulations(Simulation invalidSimulation, String path, String validationMessage) {
        given().
            contentType(ContentType.JSON).
            body(invalidSimulation).
        when().
            post("/simulations").
        then().
            statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY).
            body(path, is(validationMessage));
    }

    @Test
    @Tag(FUNCTIONAL)
    @DisplayName("Should validate an CFP duplication")
    void simulationWithDuplicatedCpf() {
        Simulation existingSimulation = simulationDataFactory.oneExistingSimulation();
        given().
            contentType(ContentType.JSON).
            body(existingSimulation).
        when().
            post("/simulations/").
        then().
            statusCode(HttpStatus.SC_CONFLICT).
            body("message", is("CPF already exists"));
    }

    @Test
    @Tag(FUNCTIONAL)
    @DisplayName("Should delete an existing simulation")
    void deleteSimulationSuccessfully() {
        Simulation existingSimulation = simulationDataFactory.oneExistingSimulation();

        given().
            pathParam("cpf", existingSimulation.getCpf()).
        when().
            delete("/simulations/{cpf}").
        then().
            statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    @Tag(FUNCTIONAL)
    @DisplayName("Should validate the return when a non-existent simulation is sent")
    void notFoundWhenDeleteSimulation() {
        given().
            pathParam("cpf", simulationDataFactory.notExistentCpf()).
        when().
            delete("/simulations/{cpf}").
        then().
            statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    @Tag(FUNCTIONAL)
    @DisplayName("Should update an existing simulation")
    void changeSimulationSuccessfully() {
        Simulation existingSimulation = simulationDataFactory.oneExistingSimulation();

        Simulation simulation = simulationDataFactory.validSimulation();
        simulation.setCpf(existingSimulation.getCpf());
        simulation.setInsurance(existingSimulation.getInsurance());

        Simulation simulationReturned =
            given().
                contentType(ContentType.JSON).
                pathParam("cpf", existingSimulation.getCpf()).
                body(simulation).
            when().
                put("/simulations/{cpf}").
            then().
                statusCode(HttpStatus.SC_OK).
                extract().
                    as(Simulation.class);

        assertThat("Simulation are not the same",
            simulationReturned, is(simulation));
    }

    @Test
    @Tag(FUNCTIONAL)
    @DisplayName("Should validate the return of an update for a non-existent CPF")
    void changeSimulationCpfNotFound() {
        Simulation simulation = simulationDataFactory.validSimulation();

        given().
            contentType(ContentType.JSON).
            pathParam("cpf", simulationDataFactory.notExistentCpf()).
            body(simulation).
        when().
            put("/simulations/{cpf}").
        then().
            statusCode(HttpStatus.SC_NOT_FOUND);
    }
}
