package com.eliasnogueira.credit.specs;

import com.eliasnogueira.credit.data.factory.RestrictionDataFactory;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;

public class RestrictionsSpecs {

    public static RequestSpecification cpfWithoutRestrictionRequestSpec() {
        RestrictionDataFactory restrictionDataFactory = new RestrictionDataFactory();

        return new RequestSpecBuilder().
            addRequestSpecification(InitialStateSpecs.set()).
            addPathParam("cpf", restrictionDataFactory.cpfWithoutRestriction()).
            build();
    }

    public static ResponseSpecification notFoundResponse() {
        return new ResponseSpecBuilder().
            expectStatusCode(HttpStatus.SC_NOT_FOUND).
            build();
    }

}
