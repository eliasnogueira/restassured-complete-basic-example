package com.eliasnogueira.credit.data.provider;

import com.eliasnogueira.credit.data.factory.SimulationDataFactory;
import com.eliasnogueira.credit.model.Simulation;
import org.testng.annotations.DataProvider;

public class SimulationDataProvider {

    @DataProvider(name = "failSimulationValidations")
    public static Object[][] failValidations() {
        SimulationDataFactory simulationDataFactory = new SimulationDataFactory();

        Simulation simulationLessThanMinAmount = simulationDataFactory.simulationLessThanMinAmount();
        Simulation simulationExceedAmount = simulationDataFactory.simulationExceedAmount();
        Simulation simulationLessThanMinInstallments = simulationDataFactory.simulationLessThanMinInstallments();
        Simulation simulationExceedInstallments = simulationDataFactory.simulationExceedInstallments();
        Simulation simulationWithNotValidEmail = simulationDataFactory.simulationWithNotValidEmail();
        Simulation simulationWithEmptyCPF = simulationDataFactory.simulationWithEmptyCPF();
        Simulation simulationWithEmptyName = simulationDataFactory.simulationWithEmptyName();

        return new Object[][]{
            {simulationLessThanMinAmount, "errors.amount", "Amount must be equal or greater than $ 1.000"},
            {simulationExceedAmount, "errors.amount", "Amount must be equal or less than than $ 40.000"},
            {simulationLessThanMinInstallments, "errors.installments", "Installments must be equal or greater than 2"},
            {simulationExceedInstallments, "errors.installments", "Installments must be equal or less than 48"},
            {simulationWithNotValidEmail, "errors.email", "must be a well-formed email address"},
            {simulationWithEmptyCPF, "errors.cpf", "CPF cannot be empty"},
            {simulationWithEmptyName, "errors.name", "Name cannot be empty"}
        };
    }

}
