package steps;

import client.APIClient;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import utils.JsonFileLoader;

public class ApiSteps {
    private static final String NAME_PATTERN = "\"name\":\"%s\"";
    private static final String STATUS_PATTERN = "\"status\":\"%s\"";
    private static final String ID_PATTERN = "\"id\":%s";

    private String currentResponse;
    private final APIClient apiClient = new APIClient();
    private String requestPayload;

    @Given("a pet payload with json from path {string}")
    public void a_pet_payload_with_json_from_path(String jsonName) {
        requestPayload = JsonFileLoader.load(jsonName+".json");
    }
    @Given("I update the pet payload with status {string}")
    public void updatePetPayloadStatus(String status) {
        requestPayload = requestPayload.replaceAll(
                "\"status\":\\s*\"[^\"]+\"",
                String.format(STATUS_PATTERN, status)
        );
    }

    @When("I send GET request to {string}")
    public void sendGetRequest(String endpoint) {
        currentResponse = apiClient.get(endpoint);
    }

    @When("I send POST request to {string}")
    public void sendPostRequest(String endpoint) {
        currentResponse = apiClient.post(endpoint, "createPet.json", null);
    }

    @When("I send POST request to {string} with body:")
    public void sendPostRequestWithBody(String endpoint, String body) {
        currentResponse = apiClient.post(endpoint, body, null);
    }

    @When("I send PUT request to {string}")
    public void sendPutRequest(String endpoint) {
        currentResponse = String.valueOf(apiClient.put(endpoint, requestPayload, null));
    }

    @When("I send DELETE request to {string}")
    public void sendDeleteRequest(String endpoint) {
        currentResponse = String.valueOf(apiClient.delete(endpoint));
    }
    @Then("the response should contain pet name {string}")
    public void the_response_should_contain_pet_name(String string) {
        // Write code here that turns the phrase above into concrete actions
        currentResponse.contains(string);
    }
}