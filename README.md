# Combined Credit Test API

Don't forget to give this project a ⭐

This project was created to start the initial steps with test automation for a REST API using RestAssured.
It tests the API: [combined-credit-api](https://github.com/eliasnogueira/combined-credit-api)

> :warning: **Disclaimer**
> 
> This project has an educational objective and does not have the best practices that could be applied
>
> Some practices will help you to improve your test architecture, but the central point of this repository and 
> demonstrate an example of running tests for api in a pipeline
> some practices will help you to improve your test architecture, 
> but the central point of this repository and demonstrate an example of running tests for api in a pipeline

# Required software
* Java JDK 11+
* Maven installed and in your classpath
* [Configure your IDE to use Lombok](https://www.baeldung.com/lombok-ide)
* Download the backend API [combined-credit-api](https://github.com/eliasnogueira/combined-credit-api)

# How to execute the tests
You can open each test classes on `src\test\java` and execute all of them, but I recommend you run it by the
command line. It enables us to run in different test execution strategies and also in a pipeline, that is the propose of this repo.

## Running the backend API
Please, before execute any tests, run the backend API.
After cloning this project:

1. Navigate to the project folder using the Terminal / Command prompt
2. Execute the following: `mvn spring-boot:run`
3. Wait until you see something like this: Application has started! Happy tests!
4. The API is ready and listen all requests on http://localhost:8088

## About the environments
You can run the tests simulation two environments: dev and test.
By default, the scripts use the dev environment.

If you want to run the tests on the test environment set the property `environment` to it.
```
mvn test -Denvironment=test
```

## Running the test suites
The test suites can be run directly by your IDE or by command line.
If you run `mvn test` all the tests will execute because there's a property `suite` with `all` as value.
This means that, during a build/running activity, Maven will search for the given suite and execute it.
If no suite is informed, the default will be used.

To run different suites you must inform the property `-Dsuite` to the command line with the suite name:

| run | command |
|-----|---------|
| health check tests | `mvn test -Dsuite=health` |
| contract tests | `mvn test -Dsuite=contract` |
| functional tests | `mvn test -Dsuite=functional` |
| e2e tests | `mvn test -Dsuite=e2e` |

Note that, if do you want to create a new test suite file, it must be placed on the `suites` folder.

# About the Project Structure

## src/main/java

### client
Classes that do some actions in their endpoints. It's used my the `FullSimulationE2ETest` to demonstrate and e2e
scenario.

### config
The class `Configuration` is the connections between the properties files located in `src/test/resources/conf`
The annotation `@Config.Sources({"classpath:conf/${environment}.properties"})` load the property file based on the 
property `environment`, creating a full load. E.g: `conf/dev.properties`.

The environment variable is read on the `ConfiguratorManager` by the private method `setEnvironment`.
If there's no property for `environment` set, the file `dev.properties` will be used.
This class reduces the amount of code necessary to get any information on the properties file.

This strategy uses [Owner](http://owner.aeonbits.org/) library

### data

#### factory
Test Data Factory classes using [java-faker](https://github.com/DiUS/java-faker) to generate fake data and [Lombok] to
create the objects using the Builder pattern.

In a few cases, there are custom data like:
 * the list of existent restrictions and simulations in the database
 * cpf generation
 * data generation returned by the api use

#### provider
TestNG Data Provider class to reduce the amount of code and maintenance for the functional tests on `SimulationsFunctionalTest`

#### support
Custom CPF generator

### model
Model class using Lombok to enable 
[mapping objects thought serialization and deserialization](https://github.com/rest-assured/rest-assured/wiki/Usage#object-mapping) 
in use with RestAssured

### specs
Request and Response specifications used by the clients and e2e tests.
The class `InitialStepsSpec` set the basePath, baseURI, and port for the custom specs.
The classes `RestrictionsSpecs` and `SimulationsSpecs` contains the implementation of request and response specifications.

### test
Base Test that sets the initial aspects to make the requests using RestAssured.
It also has the configuration to deal with `BigDecimal` returns and SSL configuration.

This class also has a listener for TestNG to, automatically, generate a test report with ExtentReport.

## src/test/java

### e2e
End to End test using both endpoints to simulate the user journey thought the API.

### general
Health check test to assure the endpoint is available

### restrictions
Contract and Functional tests to the Restriction endpoint

### simulations
Contract and Functional tests to the Simulations endpoint

## src/test/resources

### conf
Configuration simulation different environments:
* dev: development environment
* test: test/pre-prod environment

### schemas
JSON Schemas to enable Contract Testing

### suites
TestNG suites divided by a pipeline strategy

# Libraries
* [Owner](http://owner.aeonbits.org/) to manage the property files
* [java-faker](https://github.com/DiUS/java-faker) to generate fake data
* [TestNG](https://testng.org/doc/) to support the test creation
* [RestAssured](http://rest-assured.io/) library to test REST APIs
* [Log4J2](https://logging.apache.org/log4j/2.x/) as the logging strategy
* [ExtentReport](https://extentreports.com/) to automatically generate a test report

# Patterns applied
* Test Data Factory
* Data Provider
* Builder
* Request and Response Specification
* Base Test



# Do you want to help?
Please read the [Contribution guide](CONTRIBUTING.md)
