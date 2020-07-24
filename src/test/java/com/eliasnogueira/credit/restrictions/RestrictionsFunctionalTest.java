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
