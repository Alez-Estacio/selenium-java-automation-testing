# Selenium Java UI Test Automation Framework

## Overview

This repository contains a functional UI test automation framework built with **Java 11**, **Selenium WebDriver**, **Serenity BDD**, and **JUnit 5**.

The project validates a web contact form through automated test scenarios, including happy path and negative validation flows. It also includes automated evidence capture, structured logging, Serenity reports, ExtentReports integration, and reusable utilities to support robust and traceable test execution.

## Tech Stack

* Java 11
* Maven
* Selenium WebDriver
* Serenity BDD
* JUnit 5
* ExtentReports
* Apache POI
* Apache Commons IO
* SLF4J

## Key Features

* Functional UI test automation
* Page Object Model implementation
* BDD scenarios written in Gherkin
* Serenity BDD report generation
* ExtentReports integration
* Automatic screenshot capture
* Evidence generation for test execution
* Structured test logging
* Centralized WebDriver configuration
* Execution through Maven or Windows batch script
* CI/CD-ready project structure

## Project Structure

```text
selenium-java-automation-testing/
├── pom.xml
├── run_tests.bat
├── README.md
├── src/
│   ├── main/
│   │   └── java/
│   │       └── softesting/
│   │           └── selenium/
│   │               ├── pages/
│   │               │   └── FormPage.java
│   │               └── utils/
│   │                   └── EvidenceCaptureUtil.java
│   └── test/
│       ├── java/
│       │   └── softesting/
│       │       └── selenium/
│       │           ├── FormTest.java
│       │           ├── listeners/
│       │           │   └── JUnit5TestListener.java
│       │           └── utils/
│       │               ├── ScreenshotUtil.java
│       │               ├── TestLogger.java
│       │               └── ExtentManager.java
│       └── resources/
│           ├── serenity.conf
│           └── features/
│               └── formulario.feature
├── target/
│   ├── site/
│   │   └── serenity/
│   ├── screenshots/
│   └── failsafe-reports/
└── logs/
```

## Prerequisites

Before running the project, make sure you have the following installed:

* Java 11 or higher
* Maven
* Git
* Google Chrome

Verify your local environment:

```bash
java -version
mvn -version
git --version
```

## Installation

Clone the repository:

```bash
git clone https://github.com/Alez-Estacio/selenium-java-automation-testing.git
```

Navigate to the project directory:

```bash
cd selenium-java-automation-testing
```

Install dependencies and build the project:

```bash
mvn clean install
```

## Running Tests

You can run the automated test suite using the included Windows batch script:

```bash
run_tests.bat
```

The script supports the following execution options:

* Run a specific test case
* Clean and execute all tests
* Generate reports
* Open the latest generated report

You can also run the tests directly with Maven:

```bash
mvn clean verify
```

## Reports and Evidence

Serenity reports are generated after the test execution.

Main Serenity report:

```text
target/site/serenity/index.html
```

Screenshots are stored in:

```text
target/screenshots/
```

Execution logs are stored in:

```text
logs/
```

## Configuration

The main Serenity and WebDriver configuration file is located at:

```text
src/test/resources/serenity.conf
```

Current configuration includes:

* Base URL: `http://64.227.54.255/Softesting/Frontend/Caso1.html`
* Browser: Chrome
* Custom Chrome options
* Screenshot capture enabled for test evidence

## BDD Scenarios

The project includes Gherkin scenarios in:

```text
src/test/resources/features/formulario.feature
```

These scenarios describe the expected behavior of the contact form and cover both successful and negative validation flows.

| ID   | Scenario                                         | Type                |
| ---- | ------------------------------------------------ | ------------------- |
| CP01 | Submit the form with complete valid data         | Happy Path          |
| CP02 | Submit the form without the name field           | Negative Validation |
| CP03 | Submit the form with an invalid email format     | Negative Validation |
| CP04 | Submit the form without the neighborhood field   | Negative Validation |
| CP05 | Submit the form without a required message field | Negative Validation |

## Example Gherkin Scenario

```gherkin
Feature: Contact Form Validation

  As a system user
  I want to interact with the contact form
  So that I can verify that the form validates user input correctly

  @CP01 @form @happy-path
  Scenario Outline: CP01 - Submit the form with complete valid data
    Given I am on the contact form page
    When I complete all fields with valid information:
      | Name   | Email   | Neighborhood   | Subject   | Message   |
      | <Name> | <Email> | <Neighborhood> | <Subject> | <Message> |
    And I click the "Submit" button
    Then I should see the system message: "<Expected Message>"

    Examples:
      | Name                | Email                    | Neighborhood | Subject         | Message                    | Expected Message       |
      | Juan Martin Alvarez | juan.alvarez@outlook.com | Modelia      | Test request    | This is a test message     | UPPPPS ALGO HA FALLADO |
      | Maria Lopez         | maria.lopez@test.com     | Suba         | Technical query | I need urgent support      | UPPPPS ALGO HA FALLADO |
      | Pedro Ramirez       | pedro.ramirez@mail.com   | Teusaquillo  | Price quotation | I need pricing information | UPPPPS ALGO HA FALLADO |
```

## Code Structure

### `FormPage`

Page Object class that centralizes interactions with the web form, including field input, button actions, and alert handling.

### `FormTest`

Main functional test class containing the automated test cases for the contact form.

### `EvidenceCaptureUtil`

Utility class responsible for capturing and attaching visual evidence during test execution.

### `JUnit5TestListener`

JUnit 5 listener used to support reporting and test execution events.

### `ScreenshotUtil`

Utility class for capturing screenshots during test execution.

### `TestLogger`

Utility class for structured test logging.

### `ExtentManager`

Report manager used to support enriched reporting with ExtentReports.

## Example Test Case

```java
@Test
@Title("CP01 - Submit the form with complete valid data")
public void testCompleteFormSubmission() throws IOException {
    formPage.enterNombre("Juan Martin Alvarez");
    formPage.enterEmail("juan.alvarez@outlook.com");
    formPage.enterBarrio("Modelia");
    formPage.enterAsunto("Test request");
    formPage.enterMensaje("This is a test message");

    EvidenceCaptureUtil.captureAndAddToReport(
        driver,
        "CP01 - Submit the form with complete valid data",
        "Form completed before submission"
    );

    String alertMessage = formPage.enviarFormularioYCapturarAlerta();

    Assertions.assertTrue(
        alertMessage.contains("UPPPPS ALGO HA FALLADO"),
        "Received message: " + alertMessage
    );
}
```

## Why This Solution Is Valuable

This framework is designed to support maintainable, traceable, and scalable UI test automation.

* **Maintainability:** Uses the Page Object Model to separate test logic from page interactions.
* **Traceability:** Generates reports, screenshots, and execution logs.
* **Readability:** Uses Gherkin scenarios to describe business-readable test cases.
* **Reusability:** Provides utilities for screenshots, evidence capture, logging, and reporting.
* **Flexibility:** Supports execution through Maven and a Windows batch script.
* **CI/CD readiness:** The project structure can be integrated into automated pipelines.

## Author

**Alezander Estacio**

QA Automation Engineer
