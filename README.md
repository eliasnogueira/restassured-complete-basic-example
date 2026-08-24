# Rest-Assured Complete Basic Example

[![Actions Status](https://github.com/eliasnogueira/restassured-complete-basic-example/workflows/Build%20and%20Test/badge.svg)](https://github.com/eliasnogueira/restassured-complete-basic-example/actions)

Remember to give this project a ⭐

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

This project was created to demonstrate the initial steps of REST API test automation using REST-Assured.
It tests the API: [credit-api](https://github.com/eliasnogueira/credit-api)

> :warning: **Disclaimer**
>
> This project has an educational goal and is intentionally kept small. It demonstrates common API testing patterns,
> but production projects may require additional isolation, security, and reporting practices.
>
> The main goal is to demonstrate an API test architecture and run the tests in a CI pipeline.

## Required software

* Java JDK 25
* Clone/download the backend API [credit-api](https://github.com/eliasnogueira/credit-api)

The project includes the Maven Wrapper, so Maven does not need to be installed separately.

## How to execute the tests

You can run the tests from an IDE or from the command line. The command-line approach also makes it easy to select
different test groups locally and in CI.

### Running the backend API

Please, before executing any tests, run the backend API.
After cloning the [credit-api](https://github.com/eliasnogueira/credit-api) project:

1. Navigate to the project folder using the Terminal / Command prompt
2. Execute the following: `./mvnw spring-boot:run`
3. Wait until you see something like this: _Application has started! Happy tests!_
4. The API is ready to receive requests at `http://localhost:8088`

### Running the test suites

The test suites can be run directly by your IDE or by the command line.
If you run `./mvnw test` all the tests will execute because it's the regular Maven lifecycle to run all the tests.

To run different suites based on the groups defined for each test, you must inform the property `-Dgroups` and the group
names.
The example below shows how to run the test for each pipeline stage:

| pipeline stage   | command                             |
|------------------|-------------------------------------|
| contract tests   | `./mvnw test -Dgroups="contract"`   |
| functional tests | `./mvnw test -Dgroups="functional"` |
| e2e tests        | `./mvnw test -Dgroups="e2e"`        |

### Generating the test report

This project uses Allure Report to automatically generate the test report.
There is some configuration to make it happen:

* aspectj configuration on `pom.xml` file
* `allure.properties` file on `src/test/resources`

You can use the command line to generate it in two ways:

* `./mvnw allure:serve`: will open the HTML report into the browser
* `./mvnw allure:report`: generates the HTML report in `target/site/allure-maven-plugin`

## About the Project Structure

### src/main/java

#### client

Classes that encapsulate endpoint actions. They are used by `FullSimulationE2ETest` to demonstrate an E2E scenario.

#### commons

Contains shared helpers, such as the URL resolver used to validate the `Location` header returned when a simulation
is created.

#### config

`Configuration` maps the properties in `src/test/resources/api.properties` to typed accessors.

The `@Config.Sources` load the properties file and match the attributes with the `@Key`, so you automatically have the
value.
You can see two sources.
The first one will get the property values from the system (as environment variables or from the command line) in the
case you want to change it, for example, in a pipeline.
The second will load the `api.properties` file from the classpath.

```java
@Config.Sources({
        "system:properties",
        "classpath:api.properties"})
```

System properties take precedence over the classpath defaults, which makes the configuration suitable for CI and
different environments. `ConfigurationManager` provides a single access point to the configuration.

This strategy uses the [Owner](https://matteobaccan.github.io/owner/) library

#### data

##### changeless

Contains test-group names, endpoint paths, and validation error constants.

##### factory

Test Data Factory classes use [DataFaker](https://www.datafaker.net/) to generate test data.

In a few cases, there are custom data like:

* the list of existent restrictions and simulations in the database
* cpf generation
* data generation returned by the API use

##### provider

JUnit 5 Arguments to reduce the amount of code and maintenance for the functional tests on `SimulationsFunctionalTest`

#### model

Model and Builder classes used for
[serialization and deserialization](https://github.com/rest-assured/rest-assured/wiki/Usage#object-mapping)
with REST-Assured.

#### specs

Request and Response specifications used by the clients and e2e tests.
`InitialStateSpecs` sets the base URI, base path, and port for request specifications. `RestrictionsSpecs` and
`SimulationsSpecs` define reusable request and response specifications.

### src/test/java

#### e2e

End-to-End test using both endpoints to simulate a user journey through the API.

#### restrictions

Contract and functional tests for the restrictions endpoint.

#### simulations

Contract and functional tests for the simulations endpoint.

### src/test/resources

Contains JSON schemas used by the contract tests and the API configuration file.

## Libraries

* [REST-Assured](http://rest-assured.io/) for testing REST APIs
* [JUnit 5](https://junit.org/junit5/) for writing and running tests
* [Owner](https://matteobaccan.github.io/owner/) to manage the property files
* [DataFaker](https://www.datafaker.net/) to generate test data
* [Log4j 2](https://logging.apache.org/log4j/2.x/) for logging
* [Allure Report](https://docs.qameta.io/allure/) for test reporting

## Patterns applied

* Test Data Factory
* Data Provider
* Builder
* Request and response specification
* Base test

## Pipeline

This project uses [GitHub Actions](https://github.com/features/actions) to run the tests in a pipeline. The workflow
is available at `.github/workflows/test-execution.yml`.

We have the following pipeline steps:

```
build -> contract -> e2e -> functional
```

The tests support these system properties for selecting a target environment:

* `-Dgroups`: specify which test type will be executed
* `-Dapi.base.uri`: specify a new base URI
* `-Dapi.base.path`: specify a new base path
* `-Dapi.port`: specify a new port
* `-Dapi.health.context`: specify a new health context

The default target is the local API at `http://localhost:8088/api/v1`. Override these properties when running against
another environment, for example:

```shell
./mvnw test -Dgroups=functional \
  -Dapi.base.uri=https://example.test \
  -Dapi.base.path=/api/v1 \
  -Dapi.port=443
```

The GitHub Actions workflow starts the backend service configured in the workflow. The environment availability
extension checks `api.health.context/health` before running API tests and skips the suite when the target is
unavailable.

## Troubleshooting

* If tests are skipped with `Environment is not available`, verify that the backend is running and that the configured
  URI, port, and health context resolve to a healthy endpoint.
* If port `8088` is already in use, stop the conflicting process or override `api.port` for both the backend and tests.
* If Allure commands are unavailable, run `./mvnw allure:report` first; the Maven plugin downloads the matching
  Allure command-line distribution.

## Do you want to help?

Please read the [Contribution guide](CONTRIBUTING.md)
