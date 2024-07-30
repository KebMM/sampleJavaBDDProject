package projectFolders.apiAutomation.utilities;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import projectFolders.uiAutomation.utilities.CommonUISteps;
import projectFolders.uiAutomation.utilities.Driver;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.File;
import java.util.stream.Collectors;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class CommonAPISteps extends CurrentResponse {

    public static Response sendGetRequest(String url, Map<String, String> headers, Map<String, String> params) {
        var request = io.restassured.RestAssured.given();
        if (headers != null) request.headers(headers);
        if (params != null) request.params(params);
        return request.when().get(url);
    }

    public static Response sendPostRequest(String url, Map<String, String> headers, Object body) {
        var request = io.restassured.RestAssured.given();
        if (headers != null) request.headers(headers);
        if (body != null) request.body(body);
        return request.when().post(url);
    }

    public static Response sendPutRequest(String url, Map<String, String> headers, Object body) {
        var request = io.restassured.RestAssured.given();
        if (headers != null) request.headers(headers);
        if (body != null) request.body(body);
        return request.when().put(url);
    }

    public static Response sendDeleteRequest(String url, Map<String, String> headers, Object body) {
        var request = io.restassured.RestAssured.given();
        if (headers != null) request.headers(headers);
        if (body != null) request.body(body);
        return request.when().delete(url);
    }

    public static Response sendPatchRequest(String url, Map<String, String> headers, Object body) {
        var request = io.restassured.RestAssured.given();
        if (headers != null) request.headers(headers);
        if (body != null) request.body(body);
        return request.when().patch(url);
    }

    public static Response sendHeadRequest(String url, Map<String, String> headers) {
        var request = io.restassured.RestAssured.given();
        if (headers != null) request.headers(headers);
        return request.when().head(url);
    }

    public static Response sendOptionsRequest(String url, Map<String, String> headers) {
        var request = io.restassured.RestAssured.given();
        if (headers != null) request.headers(headers);
        return request.when().options(url);
    }

    public static Response sendGetRequestWithAuth(String url, String token, Map<String, String> headers) {
        if (headers == null) headers = new java.util.HashMap<>();
        headers.put("Authorization", "Bearer " + token);
        return sendGetRequest(url, headers, null);
    }

    public static Response sendPostRequestWithAuth(String url, Object body, String token, Map<String, String> headers) {
        if (headers == null) headers = new java.util.HashMap<>();
        headers.put("Authorization", "Bearer " + token);
        return sendPostRequest(url, headers, body);
    }

    public static void checkStatusCode(Response response, int expectedStatus) {
        response.then().statusCode(expectedStatus);
    }

    public static void checkResponseJson(Response response, String expectedJson) {
        JsonElement expected = JsonParser.parseString(expectedJson);
        JsonElement actual = JsonParser.parseString(response.asString());
        assertTrue(expected.equals(actual));
    }

    public static void checkResponseHeaders(Response response, Map<String, String> expectedHeaders) {
        for (Map.Entry<String, String> entry : expectedHeaders.entrySet()) {
            response.then().header(entry.getKey(), equalTo(entry.getValue()));
        }
    }

    public static void validateResponseTime(Response response, long maxResponseTime) {
        response.then().time(Matchers.lessThan(maxResponseTime));
    }

    public static void validateResponseContainsKey(Response response, String key) {
        assertTrue(response.jsonPath().getMap("").containsKey(key));
    }

    public static void validatePagination(Response response, int pageSize) {
        response.then().body("size()", Matchers.lessThanOrEqualTo(pageSize));
    }

    public static void validateSorting(Response response, String key, String order) {
        List<Map<String, Object>> items = response.jsonPath().getList("");
        List<Map<String, Object>> sortedItems = new ArrayList<>(items);
        sortedItems.sort((a, b) -> {
            Comparable<Object> valA = (Comparable<Object>) a.get(key);
            Comparable<Object> valB = (Comparable<Object>) b.get(key);
            return order.equals("desc") ? valB.compareTo(valA) : valA.compareTo(valB);
        });
        assertTrue(items.equals(sortedItems));
    }

    public static Response apiFileUpload(String url, String filePath, Map<String, String> headers) {
        var request = io.restassured.RestAssured.given();
        if (headers != null) request.headers(headers);
        request.multiPart("file", new File(filePath));
        return request.when().post(url);
    }

    public static void validateResponseStructure(Response response, Set<String> structure) {
        // Fetch the keys as Set<Object>
        Set<Object> responseKeysObject = response.jsonPath().getMap("").keySet();

        // Create a Set<String> and add the keys as strings
        Set<String> responseKeys = new HashSet<>();
        for (Object key : responseKeysObject) {
            responseKeys.add((String) key);
        }

        assertTrue(responseKeys.equals(structure), "Expected structure does not match the actual response structure.");
    }

    public static void validateResponseContainsKeyWithValue(Response response, String key, String value) {
        String actualValue = response.jsonPath().getString(key);
        assertTrue(actualValue.equals(value), "Expected value does not match actual value.");
    }

    public static void validateResponseContainsValue(Response response, String expectedValue) {
        assertTrue(response.asString().contains(expectedValue), "Expected value does not exist in the response.");
    }
}
