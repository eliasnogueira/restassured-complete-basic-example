# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [2.4.1] - 26-05-2024
- Bump `actions/cache`
- Bump Maven Wrapper
- Change message e-mail expected error message in the `SimulationErrorsData`
- Updated the following dependencies
  - `junit.jupiter.version -> 5.11.0-M2`
  - `assertj.version -> 3.26.0`
  - `datafaker.version -> 2.2.2`
  - `slf4j.version -> 2.0.13`
  - `allure.version -> 2.27.0`
  - `aspectj.version -> 1.9.22.1`

## [2.4.0] - 20-03-2024

### Changed

- Adopt Java 22
  - Set `java.version` to 22
  - Changed `actions/setup-java@v4` in `.github/workflows/test-execution.yml` to Java 22
- Updated the following dependencies
  - `maven-compiler-plugin.version -> 3.13.0`
  - `maven-surefire-plugin.version -> 3.2.5`
  - `maven-failsafe-plugin.version -> 3.2.5`
  - `junit.jupiter.version -> 5.10.2`
  - `assertj.version -> 3.25.3`
  - `datafaker.version -> 2.1.0`
  - `log4j.version -> 2.23.1`
  - `slf4j.version -> 2.0.12`
  - `allure.version -> 2.26.0`
  - `aspectj.version -> 1.9.21.2`
  - `commons-codec.version -> 1.16.1`
  - `jackson-databind.version -> 2.17.0`

## [2.3.1] - 17-12-2023

### Changed
- Updated the following dependencies
  - `maven-surefire-plugin -> 3.2.3`
  - `restassured -> 5.4.0`
  - `allure -> 2.25.0`
  - `aspectj -> 1.9.21`
  - `jackson-databind -> 2.16.0`

## [2.3.0] - 06-11-2023

### Changed
- Set Java version as 21 in the `pom.xml` and `.github/workflows/test-execution.yml`
- Updated Maven Wrapper
- Updated the following dependencies
  - `maven-surefire-plugin.version -> 3.2.1`
  - `maven-failsafe-plugin.version -> 3.2.1`
  - `restassured.version -> 5.3.2`
  - `junit.jupiter.version -> 5.10.1`
  - `datafaker.version -> 2.0.2`
  - `slf4j.version -> 2.0.9`
  - `allure.version -> 2.24.0`
  - `aspectj.version -> 1.9.20.1`

### Removed
- Removed custom BaseTest classes for Restrictions and Simulations as the data factories shouldn't be instantiated

## [2.2.6] - 06-06-2023

### Changed
- Update the following dependencies
  - `maven-surefire-plugin.version -> 3.1.2`
  - `restassured.version -> 5.3.1`
  - `junit.jupiter.version -> 5.10.0-M1`
  - `datafaker.version -> 2.0.1`
  - `allure.version -> 2.23.0`
  - `aspectj.version -> 1.9.19`
- Added `commons-codec` and `jackson-databind` libraries explicitly to solve dependencies security issues
- Updated `READM` and `CONTRIBUTION` mentioning Java 17

## [2.2.5] - 14-03-2023

### Changed
- Update the following dependencies
 - `allure-maven -> 2.12.0`
 - `allure -> 2.21.0`
 - `aspectj -> 1.9.19`
 - `assertj -> 3.24.2`
 - `datafaker -> 1.8.1`
 - `junit.jupiter -> 5.9.2`
 - `log4j -> 2.20.0`
 - `maven-compiler-plugin -> 3.11.0`
 - `maven-surefire-plugin -> 3.0.0`
 - `slf4j -> 2.0.7`
- Added quiet mode in all Maven commands in the GitHub workflow
- Removed CPF validations as it now returns HTTP 403 handled by the Restrictions controller
- Fix the Simulations endpoints in several classes

## [2.2.4] - 27-09-2022

### Changed
- Adoption of Java 17
- Replace of JavaFaker by DataFaker at
  - `RestricitonDataFactory`
  - `SimulationDataFactory`
- General refactoring on `SimulationsFunctionalTest`
- Removed (exclusion) the following libraries as internal dependencies due to security issues, adding them as main dependencies:
  - `commons-codec-1.15`
  - `jackson-databind-2.13.4`
  - `guava-31.1-android`
  - `jcommander-1.82`
- Update of the following dependencies
  - `maven-compiler-plugin-3.10.1`
  - `maven-surefire-plugin-3.0.0-M7`
  - `maven-failsafe-plugin-3.0.0-M7`
  - `restassured-5.2.0`
  - `junit.jupiter-5.9.1`
  - `log4j-2.19.0`
  - `slf4j-2.0.2`
  - `allure-2.19.0`
  - `aspectj-1.9.9.1`

### Removed
- Removed `CpfGenerator` and replaced by the DataFaker `faker.cpf().valid()`

## [2.2.3] - 03-07-2022

### Changed
- Updated the branch name from `master` to `main` and actions version update in `.github/workflows/test-execution.yaml`

## [2.2.2] - 03-07-2022

### Added
- New Data classes in the `changeless` package to deal with duplicated values and error key and messages

### Changed
- Adoption of the data changeless classes in some code
- Usage of `var` instead of the class in some fields to reduce the amount of code
- Update of the following libraries
  - `restassured.version-5.1.1`
  - `junit.jupiter.version-5.9.0-M1`
  - `assertj.version-3.23.1`
  - `log4j.version-2.18.0`
  - `allure.version-2.18.1`

## [2.2.1] - 19-04-2022

### Changed
- Updated the following libraries
  - `restassured-5.0.1`
  - `junit.jupiter-5.8.2`
  - `assertj-3.22.0`
  - `log4j-2.17.2`
  - `slf4j-2.0.0-alpha7`
  - `allure-2.17.3`

## [2.2.0] - 19-10-2021

### Added
- Configuration to log all request and responses setting `log.all` as `true` in the `api.properties

### Changed
- Changed the expected message when e-mail is malformed

## [2.1.0] - 18-10-2021

### Changed
- Updated the following libraries
   - `allure-2.15.0` 
   - `assertj-3.21.0`
   - `junit-5.8.1`
   - `log4j-2.14.1`
   - `restassured-4.4.0`

## [2.1.0] - 03-01-2021

### Changed
 - Moved `BaseAPI` to `src/test/java`
 - General refactor to change the `BaseAPI` package in the tests and other classes
 - Updated the following library versions
    - `restassured.version-4.3.3`
    - `allure.version-2.13.8`
    
## [2.0.1] - 28-11-2020

### Changed
 - Updated the following library versions
    - `allure-2.13.7`

## [2.2.0] 23-11-2020

### Changed
 - `RestrictionsClient` and `SimulationsClient` to return the response object
 - Added assertions in `FullSimulationE2ETest`
 - Added disabled description in `RestrictionsContractTest`
 - Updated the following library versions
    - `rest-assured-4.3.2`
 - Added libraries
    - assertj
    - slf4j
   