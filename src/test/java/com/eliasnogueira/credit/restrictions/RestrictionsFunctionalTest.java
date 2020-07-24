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
package com.eliasnogueira.credit.restrictions;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

import com.eliasnogueira.credit.data.factory.RestrictionDataFactory;
import com.eliasnogueira.credit.test.BaseAPI;
import java.text.MessageFormat;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RestrictionsFunctionalTest extends BaseAPI {

    private RestrictionDataFactory restrictionDataFactory;

    @BeforeClass(alwaysRun = true)
    public void setup() {
        restrictionDataFactory = new RestrictionDataFactory();
    }

    @Test(groups = "functional")
    public void cpfWithNoRestriction() {
        given().
            pathParam("cpf", restrictionDataFactory.cpfWithoutRestriction()).
        when().
            get("/restrictions/{cpf}").
        then()
            .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test(groups = "functional")
    public void cpfWithRestriction() {
        String cpfWithRestriction = restrictionDataFactory.cpfWithRestriction();

        given().
            pathParam("cpf", cpfWithRestriction).
        when().
            get("/restrictions/{cpf}").
        then()
            .statusCode(HttpStatus.SC_OK).
            body("message",
                is(MessageFormat.format("CPF {0} has a restriction", cpfWithRestriction)));
    }
}
