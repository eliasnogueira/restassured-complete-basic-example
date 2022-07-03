# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

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
   