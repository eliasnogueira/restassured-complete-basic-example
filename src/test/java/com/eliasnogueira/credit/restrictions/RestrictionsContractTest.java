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

import com.eliasnogueira.credit.BaseAPI;
import com.eliasnogueira.credit.data.changeless.RestrictionsData;
import com.eliasnogueira.credit.data.factory.RestrictionDataFactory;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.eliasnogueira.credit.data.changeless.TestSuiteTags.CONTRACT;
import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

class RestrictionsContractTest extends BaseAPI {

    @Test
    @Tag(CONTRACT)
    @DisplayName("Should validate the restrictions schema for GET method in v1")
    void contractOnV1() {
        given().
            pathParam("cpf", RestrictionDataFactory.cpfWithRestriction()).
        when().
            get(RestrictionsData.GET_RESTRICTIONS).
        then().
            body(matchesJsonSchemaInClasspath("schemas/restrictions_v1_schema.json"));
    }

    /*
     * We are overriding the basePath to v2 in the test first line
     *
     * You can enable the test (setting true) and see the errors because the response body is
     * different on v2
     */
    @Test
    @Tag(CONTRACT)
    @Disabled("disabled to execute the v1")
    @DisplayName("Should validate the restrictions schema for GET method in v2")
    void contractOnV2() {
        basePath = "/api/v2";

        given().
            pathParam("cpf", RestrictionDataFactory.cpfWithRestriction()).
        when().
            get(RestrictionsData.GET_RESTRICTIONS).
        then().
            body(matchesJsonSchemaInClasspath("schemas/restrictions_v1_schema.json"));
    }
}
