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
package com.eliasnogueira.credit.data.factory;

import static io.restassured.RestAssured.when;

import com.eliasnogueira.credit.data.support.CpfGenerator;
import com.eliasnogueira.credit.model.Simulation;
import com.eliasnogueira.credit.model.SimulationBuilder;
import com.github.javafaker.Faker;
import java.math.BigDecimal;
import java.util.Random;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;

public class SimulationDataFactory {

    private static final int MIN_AMOUNT = 1000;
    private static final int MAX_AMOUNT = 40000;
    private static final int MIN_INSTALLMENTS = 2;
    private static final int MAX_INSTALLMENTS = 48;
    private final Faker faker;

    public SimulationDataFactory() {
        faker = new Faker();
    }

    public String nonExistentName() {
        return faker.name().firstName();
    }

    public String notExistentCpf() {
        return new CpfGenerator().generate();
    }

    public Simulation validSimulation() {
        return newSimulation();
    }

    public Simulation oneExistingSimulation() {
        Simulation[] simulations = allSimulationsFromApi();
        return simulations[new Random().nextInt(simulations.length)];
    }

    public Simulation[] allExistingSimulations() {
        return allSimulationsFromApi();
    }

    public Simulation simulationLessThanMinAmount() {
        Simulation simulation = validSimulation();
        simulation.setAmount(new BigDecimal(faker.number().numberBetween(1, MIN_AMOUNT - 1)));

        return simulation;
    }

    public Simulation simulationExceedAmount() {
        Simulation simulation = validSimulation();
        simulation.setAmount(new BigDecimal(faker.number().numberBetween(MAX_AMOUNT + 1, 99999)));

        return simulation;
    }

    public Simulation simulationLessThanMinInstallments() {
        Simulation simulation = validSimulation();
        simulation.setInstallments(MIN_INSTALLMENTS - 1);

        return simulation;
    }

    public Simulation simulationExceedInstallments() {
        Simulation simulation = validSimulation();
        simulation.setInstallments(faker.number().numberBetween(MAX_INSTALLMENTS + 1, 999));

        return simulation;
    }

    public Simulation simulationWithNotValidEmail() {
        Simulation simulation = validSimulation();
        simulation.setEmail(faker.name().username());

        return simulation;
    }

    public Simulation simulationWithEmptyCPF() {
        Simulation simulation = validSimulation();
        simulation.setCpf(StringUtils.EMPTY);

        return simulation;
    }

    public Simulation simulationWithEmptyName() {
        Simulation simulation = validSimulation();
        simulation.setName(StringUtils.EMPTY);

        return simulation;
    }

    public Simulation missingAllInformation() {
        return new SimulationBuilder().
            cpf(StringUtils.EMPTY).
            name(StringUtils.EMPTY).
            email(faker.name().username()).
            amount(new BigDecimal(faker.number().numberBetween(1, MIN_AMOUNT - 1))).
            installments(MIN_INSTALLMENTS - 1).
            insurance(faker.bool().bool()).
            build();
    }

    private Simulation[] allSimulationsFromApi() {
        return
            when().
                get("/simulations/").
            then().
                statusCode(HttpStatus.SC_OK).
                extract().
                    as(Simulation[].class);
    }

    private Simulation newSimulation() {
        return new SimulationBuilder().
            name(faker.name().nameWithMiddle()).
            cpf(new CpfGenerator().generate()).
            email(faker.internet().emailAddress()).
            amount(new BigDecimal(faker.number().numberBetween(MIN_AMOUNT, MAX_AMOUNT))).
            installments(faker.number().numberBetween(MIN_INSTALLMENTS, MAX_INSTALLMENTS)).
            insurance(faker.bool().bool()).build();
    }
}
