# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

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
    - restassured.version-4.3.3
    - allure.version-2.13.8
    
## [2.0.1] - 28-11-2020

### Changed
 - Updated the following library versions
    - allure-2.13.7

## [2.2.0] 23-11-2020

### Changed
 - `RestrictionsClient` and `SimulationsClient` to return the response object
 - Added assertions in `FullSimulationE2ETest`
 - Added disabled description in `RestrictionsContractTest`
 - Updated the following library versions
    - rest-assured-4.3.2
 - Added libraries
    - assertj
    - slf4j
   