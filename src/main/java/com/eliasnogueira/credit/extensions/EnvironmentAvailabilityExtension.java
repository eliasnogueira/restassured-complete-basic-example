/*
 * MIT License
 *
 * Copyright (c) 2026 Elias Nogueira
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
package com.eliasnogueira.credit.extensions;

import com.eliasnogueira.credit.config.ConfigurationManager;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static io.restassured.config.ConnectionConfig.connectionConfig;

public class EnvironmentAvailabilityExtension implements BeforeAllCallback {

    private static Boolean isEnvironmentReady;

    private static final Logger log = LogManager.getLogger(EnvironmentAvailabilityExtension.class);

    @Override
    public void beforeAll(ExtensionContext context) {
        if (isEnvironmentReady == null) checkEnvironmentHealth();

        Assumptions.assumeTrue(isEnvironmentReady, "Environment is not available.");
    }

    private synchronized void checkEnvironmentHealth() {
        if (isEnvironmentReady != null) return;

        try {
            RestAssured.given()
                    .baseUri(buildHealthUrlString())
                    .config(RestAssured.config().connectionConfig(connectionConfig()
                            .closeIdleConnectionsAfterEachResponse()))
                    .when().get().then().statusCode(HttpStatus.SC_OK);

            isEnvironmentReady = true;
        } catch (Exception e) {
            log.error("Environment is not available. Check it before running the tests.");
            log.error("Endpoint used: {}", buildHealthUrlString());
            log.error("Exception: {}", e.getMessage());
            isEnvironmentReady = false;
        }
    }

    private String buildHealthUrlString() {
        var config = ConfigurationManager.getConfiguration();
        return String.format("%s:%s%s/health", config.baseURI(), config.port(), config.health());
    }
}
