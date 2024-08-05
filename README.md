# Python Automation Framework
Welcome to the Java Testing Framework! This guide is designed to help you get started with setting up and using Java for API, Mobile and UI automated test cases.
## Table of Contents
- [Overview](#overview)
- [Prerequisites](#prerequisites)
- [Framework Structure](#framework-structure)
- [Training Materials](#training-materials)
- [BDD with Cucumber](#bdd-with-cucumber)
- [UI Automation](#ui-automation)
- [API Automation](#api-automation)
- [Mobile Automation](mobile-automation)
- [Using the Reporting Tool](#using-the-reporting-tool)

## Overview
This framework is designed to simplify automated end-to-end testing for various domains including UI, API, and Mobile. It is designed to be user-friendly and flexible, supporting the BDD (Behavior-Driven Development) approach, making it versatile for a range of testing methodologies.

**Key Benefits**
- **User-Friendly and Flexible:** Designed to be easily customisable to suit various testing needs.
- **Enhanced Integration:** Compatible with reporting tools like Allure for detailed test reports.
- **Powerful Custom Components:** Simplifies repetitive tasks and improves test readability.

Each user has different test goals, so the flexibility of components, like the ability to easily customise timeout values, or the ability to integrate the tests with reporting tools like Allure, gives you full control of each test case. While the core functionality of tools like Selenium, Requests, and Appium remains intact, our framework adds several powerful features that differentiate it from a typical setup. One of the standout features is the extensive set of custom components we have developed. These components, coupled with comprehensive training materials, enable users to quickly start writing and running tests with minimal setup. For example: 
- **get_elements_text(driver, locator):** This component simplifies the process of retrieving text from a web-page, by only requiring the webdriver and the element locator. This is a commonly used step in UI tests, so by developing this reusable component tests are made to be more maintainable.
- **wait_for_visibility(driver, locator, timeout):** Waits for an element to be visible within a specified timeout, abstracting common wait logic into a reusable function.

This repository serves as the clean template you can use to build your testing projects. For a sample framework with sample tests to guide you, please visit "Sample Framework Repository", or follow the instructions below.

**Features of this framework include:**
- Java-based BDD Capability
- Pre-built UI Automation Components
- Pre-built API Automation Components
- Pre-built Mobile Automation Components
- Reporting Tool
- Sample Implementations

  
## Prerequisites
Ensure you have the following installed on your system:

Java version+
- package installer?
- Google Chrome (for UI tests)
- Maven
- RestAssured
- Cucumber

## Getting Started 
Once the repository has been cloned, cd into the path of the repository and install the necessary dependencies:
```
java requirements.txt
```

## API Automation with Cucumber:
API automation in this framework uses RestAssured and other libraries to interact with APIs. The src/test/java/steps/ directory contains pre-built methods for common API operations in the CommonApiSteps.java file. You can edit and add to this file to create custom methods for further testing. API can be tested using BDD with Cucumber.


1. Ensure Behave is installed (This should have been installed when installing dependencies)
```
pip install behave
```
2. Write Feature Files


    Feature files are written in Gherkin syntax and are located in the src/test/resources/features/ directory.

   Example feature file (src/test/resources/features/api.feature):
```
Feature: API Testing with RestAssured

  Background:
    Given I have the base URL "https://jsonplaceholder.typicode.com"

  Scenario: GET request
    When I send a GET request to "/posts/1"
    Then the status code should be 200
    And the response should contain key "userId" with value "1"
    And the response should match the JSON
      """
      {
        "userId": 1,
        "id": 1,
        "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
        "body": "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
      }
      """
    And the response time should be less than 2000 ms
```

3. Define Steps

   Step definitions are in the steps/ directory. This is where the test code is written. <br />
   Example:
```
package projectFolders.apiAutomation.step_definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import projectFolders.apiAutomation.utilities.CommonAPISteps;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class apiTest {
    private String baseUrl;
    private String token;
    private Response response;
    private Map<String, String> headers = new HashMap<>();
    private Object body;

    @Given("I have the base URL {string}")
    public void iHaveTheBaseURL(String baseUrl) {
        this.baseUrl = baseUrl;
    }
    @When("I send a GET request to {string}")
        public void iSendAGETRequestTo(String endpoint) {
            String url = baseUrl + endpoint;
            response = CommonAPISteps.sendGetRequest(url, null, null);
        }
    @Then("the status code should be {int}")
    public void theStatusCodeShouldBe(int expectedStatusCode) {
        CommonAPISteps.checkStatusCode(response, expectedStatusCode);
    }
}
```


4. Run the test using the following command:
```
mvn test

```

For additional information about Behave please visit https://behave.readthedocs.io/en/stable/tutorial.html
