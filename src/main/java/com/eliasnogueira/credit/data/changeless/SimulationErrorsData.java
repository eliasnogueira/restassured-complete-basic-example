/*
 * MIT License
 *
 * Copyright (c) 2022 Elias Nogueira
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
package com.eliasnogueira.credit.data.changeless;

public enum SimulationErrorsData {

    ERRORS_AMOUNT_GREATER("errors.amount", "Amount must be equal or greater than $ 1.000"),
    ERRORS_AMOUNT_LESS("errors.amount", "Amount must be equal or less than than $ 40.000"),
    ERRORS_INSTALLMENTS_GREATER("errors.installments", "Installments must be equal or greater than 2"),
    ERRORS_INSTALLMENTS_LESS("errors.installments", "Installments must be equal or less than 48"),
    ERRORS_EMAIL("errors.email", "must be a well-formed email address"),
    ERRORS_CPF("errors.cpf", "CPF cannot be empty"),
    ERRORS_NAME("errors.name", "Name cannot be empty");

    public final String key;
    public final String message;

    SimulationErrorsData(String key, String message) {
        this.key = key;
        this.message = message;
    }
}
