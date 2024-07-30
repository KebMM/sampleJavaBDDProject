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

    @Given("I have the token {string}")
    public void iHaveTheToken(String token) {
        this.token = token;
    }

    @Given("I have the headers")
    public void iHaveTheHeaders(Map<String, String> headers) {
        this.headers.putAll(headers);
    }

    @Given("I have the body")
    public void iHaveTheBody(String body) {
        this.body = body;
    }

    @When("I send a GET request to {string}")
    public void iSendAGETRequestTo(String endpoint) {
        String url = baseUrl + endpoint;
        response = CommonAPISteps.sendGetRequest(url, null, null);
    }

    @When("I send a GET request with auth to {string}")
    public void iSendAGETRequestWithAuthTo(String endpoint) {
        String url = baseUrl + endpoint;
        response = CommonAPISteps.sendGetRequestWithAuth(url, token, null);
    }

    @When("I send a POST request to {string}")
    public void iSendAPOSTRequestTo(String endpoint) {
        String url = baseUrl + endpoint;
        response = CommonAPISteps.sendPostRequest(url, headers, body);
    }

    @When("I send a POST request with auth to {string}")
    public void iSendAPOSTRequestWithAuthTo(String endpoint) {
        String url = baseUrl + endpoint;
        response = CommonAPISteps.sendPostRequestWithAuth(url, body, token, headers);
    }

    @When("I send a PUT request to {string}")
    public void iSendAPUTRequestTo(String endpoint) {
        String url = baseUrl + endpoint;
        response = CommonAPISteps.sendPutRequest(url, headers, body);
    }

    @When("I send a DELETE request to {string}")
    public void iSendADELETERequestTo(String endpoint) {
        String url = baseUrl + endpoint;
        response = CommonAPISteps.sendDeleteRequest(url, headers, body);
    }

    @When("I send a PATCH request to {string}")
    public void iSendAPATCHRequestTo(String endpoint) {
        String url = baseUrl + endpoint;
        response = CommonAPISteps.sendPatchRequest(url, headers, body);
    }

    @When("I send a HEAD request to {string}")
    public void iSendAHEADRequestTo(String endpoint) {
        String url = baseUrl + endpoint;
        response = CommonAPISteps.sendHeadRequest(url, headers);
    }

    @When("I send an OPTIONS request to {string}")
    public void iSendAnOPTIONSRequestTo(String endpoint) {
        String url = baseUrl + endpoint;
        response = CommonAPISteps.sendOptionsRequest(url, headers);
    }

    @Then("the status code should be {int}")
    public void theStatusCodeShouldBe(int expectedStatusCode) {
        CommonAPISteps.checkStatusCode(response, expectedStatusCode);
    }

    @Then("the response should contain key {string} with value {string}")
    public void theResponseShouldContainKeyWithValue(String key, String value) {
        CommonAPISteps.validateResponseContainsKeyWithValue(response, key, value);
    }

    @Then("the response should contain {string}")
    public void theResponseShouldContain(String expectedValue) {
        CommonAPISteps.validateResponseContainsValue(response, expectedValue);
    }

    @Then("the response should match the JSON")
    public void theResponseShouldMatchTheJSON(String expectedJson) {
        CommonAPISteps.checkResponseJson(response, expectedJson);
    }

    @Then("the response headers should be")
    public void theResponseHeadersShouldBe(Map<String, String> expectedHeaders) {
        CommonAPISteps.checkResponseHeaders(response, expectedHeaders);
    }

    @Then("the response time should be less than {long} ms")
    public void theResponseTimeShouldBeLessThan(long maxResponseTime) {
        CommonAPISteps.validateResponseTime(response, maxResponseTime);
    }

    @Then("the response should contain key {string}")
    public void theResponseShouldContainKey(String key) {
        CommonAPISteps.validateResponseContainsKey(response, key);
    }

    @Then("the response should have a maximum size of {int}")
    public void theResponseShouldHaveAMaximumSizeOf(int pageSize) {
        CommonAPISteps.validatePagination(response, pageSize);
    }

    @Then("the response should be sorted by {string} in {string} order")
    public void theResponseShouldBeSortedByInOrder(String key, String order) {
        CommonAPISteps.validateSorting(response, key, order);
    }

    @Then("the response structure should match")
    public void theResponseStructureShouldMatch(List<String> structure) {
        CommonAPISteps.validateResponseStructure(response, new HashSet<>(structure));
    }
}
