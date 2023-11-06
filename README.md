# Rest-Assured Complete Basic Example
[![Actions Status](https://github.com/eliasnogueira/restassured-complete-basic-example/workflows/Build%20and%20Test/badge.svg)](https://github.com/eliasnogueira/restassured-complete-basic-example/actions)

Don't forget to give this project a ⭐

* [Required Software](#required-software)
* [How to execute the tests](#how-to-execute-the-tests)
   * [Running the backend API](#running-the-backend-api)
   * [Running the test suites](#running-the-test-suites)
   * [Generating the test report](#generating-the-test-report)
* [About the Project Structure](#about-the-project-structure)
* [Libraries](#libraries)
* [Patterns applied](#patterns-applied)
* [Pipeline](#pipeline)
* [Do you want to help?](#do-you-want-to-help)

This project was created to start the initial steps with test automation for a REST API using Rest-Assured.
It tests the API: [combined-credit-api](https://github.com/eliasnogueira/combined-credit-api)

> :warning: **Disclaimer**
> 
> This project has an educational objective and does not have the best practices that could be applied
>
> Some practices will help you to improve your test architecture, but the central point of this repository and 
> demonstrate an example of running tests for API in a pipeline
> some practices will help you to improve your test architecture, 
> but the central point of this repository and demonstrate an example of running tests for API in a pipeline

## Required software
* Java JDK 21+
* Maven installed and in your classpath
* Clone/download the backend API [combined-credit-api](https://github.com/eliasnogueira/combined-credit-api)

> :notebook: **Note**
>
> You can use Java 17 if you want


## How to execute the tests
You can open each test class on `src\test\java` and execute all of them, but I recommend you run it by the
command line. It enables us to run in different test execution strategies and, also in a pipeline, that is the repo purpose.

### Running the backend API
Please, before executing any tests, run the backend API.
After cloning this project:

1. Navigate to the project folder using the Terminal / Command prompt
2. Execute the following: `mvn spring-boot:run`
3. Wait until you see something like this: _Application has started! Happy tests!_
4. The API is ready and listen to all requests on `http://localhost:8088`

### Running the test suites

The test suites can be run directly by your IDE or by command line.
If you run `mvn test` all the tests will execute because it's the regular Maven lifecycle to run all the tests.

To run different suites based on the groups defined for each test you must inform the property `-Dgroups` and the group names.
The example below shows how to run the test for each pipeline stage:

| pipeline stage     | command                          |
|--------------------|----------------------------------|
| health check tests | `mvn test -Dgroups="health"`     |
| contract tests     | `mvn test -Dgroups="contract"`   |
| functional tests   | `mvn test -Dgroups="functional"` |
| e2e tests          | `mvn test -Dgroups="e2e"`        |

### Generating the test report

This project uses Allure Report to automatically generate the test report.
There are some configuration to make it happen:
* aspectj configuration on `pom.xml` file
* `allure.properties` file on `src/test/resources`

You can use the command line to generate it in two ways:
* `mvn allure:serve`: will open the HTML report into the browser
* `mvn allure:report`: will generate the HTML port at `target/site/allure-maven-plugin` folder

## About the Project Structure

### src/main/java

#### test
Base Test that sets the initial aspects to make the requests using RestAssured.
It also has the configuration to deal with `BigDecimal` returns and SSL configuration.

#### client
Classes that do some actions in their endpoints. It's used my the `FullSimulationE2ETest` to demonstrate and e2e
scenario.

#### commons
It contains a class where will format the URL expected when we create a new resource in the `simulation` endpoint.
You can add any class that can be used in the project.

#### config
The class `Configuration` is the connections between the property file `api.properties` located in `src/test/resources/`.

The `@Config.Sources` load the properties file and match the attributes with the `@Key`, so you automatically have the value.
You can see two sources.
The first one will get the property values from the system (as environment variables or from the command line) in the case you want to change it, for example, in a pipeline.
The second will load the `api.properties` file from the classpath.
```java
@Config.Sources({
    "system:properties",
    "classpath:api.properties"})
```

The environment variable is read on the `ConfiguratorManager`.
This class reduces the amount of code necessary to get any information on the properties file.

This strategy uses [Owner](https://matteobaccan.github.io/owner/) library

#### data

##### factory
Test Data Factory classes using [java-faker](https://github.com/DiUS/java-faker) to generate fake data and [Lombok] to
create the objects using the Builder pattern.

In a few cases, there are custom data like:
 * the list of existent restrictions and simulations in the database
 * cpf generation
 * data generation returned by the API use

##### provider
JUnit 5 Arguments to reduce the amount of code and maintenance for the functional tests on `SimulationsFunctionalTest`

##### suite
It contains a class having the data related to the test groups.

##### support
Custom CPF (social security number) generator.

#### model
Model and Builder class to
[mapping objects thought serialization and deserialization](https://github.com/rest-assured/rest-assured/wiki/Usage#object-mapping) 
in use with Rest-Assured.

#### specs
Request and Response specifications used by the clients and e2e tests.
The class `InitialStepsSpec` set the basePath, baseURI, and port for the custom specs.
The classes `RestrictionsSpecs` and `SimulationsSpecs` contains the implementation of request and response specifications.

### src/test/java

#### e2e
End-to-End test using both endpoints to simulate the user journey thought the API.

#### general
Health check test to assure the endpoint is available.

#### restrictions
Contract and Functional tests to the Restriction endpoint.

#### simulations
Contract and Functional tests to the Simulations endpoint

### src/test/resources
It has a `schemas` folder with the JSON Schemas to enable Contract Testing using Rest-Assured. Also, the properties file to easily configure the API URI.

## Libraries
* [RestAssured](http://rest-assured.io/) library to test REST APIs
* [JUnit 5](https://junit.org/junit5/) to support the test creation
* [Owner](https://matteobaccan.github.io/owner/) to manage the property files
* [java-faker](https://github.com/DiUS/java-faker) to generate fake data
* [Log4J2](https://logging.apache.org/log4j/2.x/) as the logging strategy
* [Allure Report](https://docs.qameta.io/allure/) as the testing report strategy

## Patterns applied
* Test Data Factory
* Data Provider
* Builder
* Request and Response Specification
* Base Test

## Pipeline

This project uses [GitHub Actions](https://github.com/features/actions) to run the all the tests in a pipeline.
You can find it at https://github.com/eliasnogueira/restassured-complete-basic-example/blob/master/.github/workflows/test-execution.yml

We have the following pipeline steps:
```
build -> health check -> contract -> e2d -> funcional 
```

Except the build, that is the traditional Maven build, the other stages has some parameters to determine the test type and the SUT (System Under Test).
The parameters are:
* `-Dgroups`: specify which test type will be executed
* `-Dapi.base.uri`: specify a new base URI
* `-Dapi.base.path`: specify a new base path
* `-Dapi.port`: specify a new port
* `-Dapi.health.context`: specify a new health context

All the parameters, except the `-Dgroups` are pointing to Heroku because we can't run it locally.
It's a great example about how can you set different attribute values to run your tests.

## Do you want to help?

Please read the [Contribution guide](CONTRIBUTING.md)
