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

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import com.eliasnogueira.credit.data.factory.RestrictionDataFactory;
import com.eliasnogueira.credit.test.BaseAPI;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RestrictionsContractTest extends BaseAPI {

    private RestrictionDataFactory restrictionDataFactory;

    @BeforeClass(alwaysRun = true)
    public void setup() {
        restrictionDataFactory = new RestrictionDataFactory();
    }

    @Test(groups = "contract")
    public void contractOnV1() {
        given().
            pathParam("cpf", restrictionDataFactory.cpfWithRestriction()).
        when().
            get("/restrictions/{cpf}").
        then().
            body(matchesJsonSchemaInClasspath("schemas/restrictions_v1_schema.json"));
    }

    /*
     * We are overriding the basePath to v2 in the test first line
     *
     * You can enable the test (setting true) and see the errors because the response body is
     * different on v2
     */
    @Test(groups = "contract", enabled = false)
    public void contractOnV2() {
        basePath = "/api/v2";

        given().
            pathParam("cpf", restrictionDataFactory.cpfWithRestriction()).
        when().
            get("/restrictions/{cpf}").
        then().
            body(matchesJsonSchemaInClasspath("schemas/restrictions_v1_schema.json"));
    }
}
