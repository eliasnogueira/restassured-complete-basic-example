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
package com.eliasnogueira.credit.data.provider;

import static org.junit.jupiter.params.provider.Arguments.*;

import com.eliasnogueira.credit.data.factory.SimulationDataFactory;
import com.eliasnogueira.credit.model.Simulation;
import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;

public class SimulationDataProvider {

    private SimulationDataProvider() {
    }

    public static Stream<Arguments> failedValidations() {
        SimulationDataFactory simulationDataFactory = new SimulationDataFactory();

        Simulation simulationLessThanMinAmount = simulationDataFactory.simulationLessThanMinAmount();
        Simulation simulationExceedAmount = simulationDataFactory.simulationExceedAmount();
        Simulation simulationLessThanMinInstallments = simulationDataFactory.simulationLessThanMinInstallments();
        Simulation simulationExceedInstallments = simulationDataFactory.simulationExceedInstallments();
        Simulation simulationWithNotValidEmail = simulationDataFactory.simulationWithNotValidEmail();
        Simulation simulationWithEmptyCPF = simulationDataFactory.simulationWithEmptyCPF();
        Simulation simulationWithEmptyName = simulationDataFactory.simulationWithEmptyName();

        return Stream.of(
            arguments(simulationLessThanMinAmount, "errors.amount", "Amount must be equal or greater than $ 1.000"),
            arguments(simulationExceedAmount, "errors.amount", "Amount must be equal or less than than $ 40.000"),
            arguments(simulationLessThanMinInstallments, "errors.installments", "Installments must be equal or greater than 2"),
            arguments(simulationExceedInstallments, "errors.installments", "Installments must be equal or less than 48"),
            arguments(simulationWithNotValidEmail, "errors.email", "must be a well-formed email address"),
            arguments(simulationWithEmptyCPF, "errors.cpf", "CPF cannot be empty"),
            arguments(simulationWithEmptyName, "errors.name", "Name cannot be empty")
        );
    }
}
