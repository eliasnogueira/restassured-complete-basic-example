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
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.text.MessageFormat;

import static com.eliasnogueira.credit.data.changeless.TestSuiteTags.FUNCTIONAL;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

class RestrictionsFunctionalTest extends BaseAPI {

    @Test
    @Tag(FUNCTIONAL)
    @DisplayName("Should query a CPF without restriction")
    void cpfWithNoRestriction() {
        given().
            pathParam(RestrictionsData.CPF, RestrictionDataFactory.cpfWithoutRestriction()).
        when().
            get(RestrictionsData.GET_RESTRICTIONS).
        then()
            .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    @Tag(FUNCTIONAL)
    @DisplayName("Should query a CPF with restriction")
    void cpfWithRestriction() {
        String cpfWithRestriction = RestrictionDataFactory.cpfWithRestriction();

        given().
            pathParam(RestrictionsData.CPF, cpfWithRestriction).
        when().
            get(RestrictionsData.GET_RESTRICTIONS).
        then()
            .statusCode(HttpStatus.SC_OK).
            body("message", is(MessageFormat.format("CPF {0} has a restriction", cpfWithRestriction)));
    }
}
