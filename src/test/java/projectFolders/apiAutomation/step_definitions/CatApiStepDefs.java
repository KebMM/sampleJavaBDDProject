package projectFolders.apiAutomation.step_definitions;

import com.google.gson.Gson;
import io.cucumber.java.en.Given;

import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import org.junit.Assert;
import org.openqa.selenium.json.TypeToken;
import projectFolders._projectUtilities.ConfigurationReader;
import projectFolders.apiAutomation.controllers.CatBreed;
import projectFolders.apiAutomation.utilities.Auth;
import projectFolders.apiAutomation.utilities.CurrentResponse;


import java.lang.reflect.Type;
import java.util.List;

public class CatApiStepDefs extends CurrentResponse {
    @Given("the user sends a request to get all cat breeds to CAT api")
    public void theUserSendsARequestToGetAllCatBreedsToCATApi() throws InterruptedException {
        String apiKey = Auth.getAPIKey("newUser");
        response = RestAssured.given().header("x-api-key",apiKey)
                .when().get("https://api.thecatapi.com/v1/breeds");
    }

    @Then("verifies that the status code is {int}")
    public void verifiesThatTheStatusCodeIs(int statusCode) {
        Assert.assertEquals(response.statusCode(),statusCode);
    }

    @Then("the response contains {string} breed")
    public void theResponseContainsBreed(String breedType) {
//        response.body().prettyPrint();
//        List<CatBreed> catBreeds = response.body().as(List.class);
//        System.out.println("catBreed.getCfaUrl() = " + catBreeds.get(0).getCfaUrl());

        String json = response.body().asString();
        Type catBreedListType = new TypeToken<List<CatBreed>>(){}.getType();
        List<CatBreed> catBreeds = new Gson().fromJson(json, catBreedListType);

        // Now you can work with the list of CatBreed objects
        System.out.println("Number of cat breeds: " + catBreeds.size());
        System.out.println(catBreeds.get(0).toString());

    }
}
