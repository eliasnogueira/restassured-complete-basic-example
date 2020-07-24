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
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

import com.eliasnogueira.credit.commons.MessageFormat;
import com.eliasnogueira.credit.data.factory.SimulationDataFactory;
import com.eliasnogueira.credit.data.provider.SimulationDataProvider;
import com.eliasnogueira.credit.model.Simulation;
import com.eliasnogueira.credit.test.BaseAPI;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SimulationsFunctionalTest extends BaseAPI {

    private SimulationDataFactory simulationDataFactory;

    @BeforeClass(alwaysRun = true)
    public void setup() {
        simulationDataFactory = new SimulationDataFactory();
    }

    /*
     * not that, in order to assert the amount without problem, we must enable a configuration
     * it's located at BaseAPI class
     */
    @Test(groups = "functional")
    public void getOneExistingSimulation() {
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

    @Test(groups = "functional")
    public void getAllExistingSimulations() {
        Simulation[] existingSimulations = simulationDataFactory.allExistingSimulations();

        Simulation[] simulationsRequested =
            when().
                get("simulations/").
            then().
                statusCode(HttpStatus.SC_OK).
                extract().
                   as(Simulation[].class);

        assertThat(existingSimulations, equalTo(simulationsRequested));
    }

    @Test(groups = "functional")
    public void simulationByNameNotFound() {
        given().
            queryParam("name", simulationDataFactory.nonExistentName()).
        when().
            get("/simulations").
        then().
            statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test(groups = "functional")
    public void returnSimulationByName() {
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
    @Test(groups = "functional")
    public void createNewSimulationSuccessfully() {
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

    @Test(groups = "functional", dataProvider = "failSimulationValidations",
        dataProviderClass = SimulationDataProvider.class)
    public void invalidSimulations(Simulation invalidSimulation, String path,
        String validationMessage) {

        given().
            contentType(ContentType.JSON).
            body(invalidSimulation).
        when().
            post("/simulations").
        then().
            statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY).
            body(path, is(validationMessage));
    }

    @Test(groups = "functional")
    public void simulationWithDuplicatedCpf() {
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

    @Test(groups = "functional")
    public void deleteSimulationSuccessfully() {
        Simulation existingSimulation = simulationDataFactory.oneExistingSimulation();

        given().
            pathParam("cpf", existingSimulation.getCpf()).
        when().
            delete("/simulations/{cpf}").
        then().
            statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test(groups = "functional")
    public void notFoundWhenDeleteSimulation() {
        given().
            pathParam("cpf", simulationDataFactory.notExistentCpf()).
        when().
            delete("/simulations/{cpf}").
        then().
            statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test(groups = "functional")
    public void changeSimulationSuccessfully() {
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
            simulationReturned, equalTo(simulation));
    }

    @Test(groups = "functional")
    public void changeSimulationCpfNotFound() {
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
