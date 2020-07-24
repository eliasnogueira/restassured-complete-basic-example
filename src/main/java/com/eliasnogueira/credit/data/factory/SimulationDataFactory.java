package com.eliasnogueira.credit.data.factory;

import static io.restassured.RestAssured.when;

import com.eliasnogueira.credit.data.support.CpfGenerator;
import com.eliasnogueira.credit.model.Simulation;
import com.github.javafaker.Faker;
import java.math.BigDecimal;
import java.util.Random;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;

public class SimulationDataFactory {

    private final Faker faker;
    private static final int MIN_AMOUNT = 1000;
    private static final int MAX_AMOUNT = 40000;
    private static final int MIN_INSTALLMENTS = 2;
    private static final int MAX_INSTALLMENTS = 48;

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
        Simulation[] simulations = allSimulations();
        return simulations[new Random().nextInt(simulations.length)];
    }

    public Simulation[] allExistingSimulations() {
        return allSimulations();
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
        return Simulation.builder().
            cpf("").
            name("").
            email(faker.name().username()).
            amount(new BigDecimal(faker.number().numberBetween(1, MIN_AMOUNT - 1))).
            installments(MIN_INSTALLMENTS - 1).
            insurance(faker.bool().bool()).
            build();
    }

    private Simulation[] allSimulations() {
        return
            when().
                get("/simulations/").
            then().
                statusCode(HttpStatus.SC_OK).
                extract().
                    as(Simulation[].class);
    }

    private Simulation newSimulation() {
        return Simulation.builder().
            name(faker.name().nameWithMiddle()).
            cpf(new CpfGenerator().generate()).
            email(faker.internet().emailAddress()).
            amount(new BigDecimal(faker.number().numberBetween(MIN_AMOUNT, MAX_AMOUNT))).
            installments(faker.number().numberBetween(MIN_INSTALLMENTS, MAX_INSTALLMENTS)).
            insurance(faker.bool().bool()).build();
    }
}
