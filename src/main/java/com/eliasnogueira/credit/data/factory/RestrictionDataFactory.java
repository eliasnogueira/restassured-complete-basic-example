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

import net.datafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class RestrictionDataFactory {

    private static final Faker faker = new Faker();
    private static final Logger log = LogManager.getLogger(RestrictionDataFactory.class);

    private RestrictionDataFactory() {
    }

    public static String cpfWithoutRestriction() {
        String cpf = String.valueOf(faker.number().randomNumber(11, false));

        log.info("CPF without restriction in use: {}", cpf);
        return cpf;
    }

    public static String cpfWithRestriction() {
        String cpfWithRestriction = faker.options().option("97093236014", "60094146012", "84809766080",
                "62648716050", "26276298085", "01317496094", "55856777050", "19626829001", "24094592008",
                "58063164083");

        log.info("CPF with restriction in use: {}", cpfWithRestriction);
        return cpfWithRestriction;
    }
}
