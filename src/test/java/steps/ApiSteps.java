package steps;

import client.APIClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.db.apicore.json.JsonUtility;
import utils.JsonFileLoader;

import java.io.IOException;

public class ApiSteps {
    private static final String NAME_PATTERN = "\"name\":\"%s\"";
    private static final String STATUS_PATTERN = "\"status\":\"%s\"";
    private static final String ID_PATTERN = "\"id\":%s";

    private String currentResponse;
    private final APIClient apiClient = new APIClient();
    private String requestPayload;

    @Given("I pass a payload with json from path {string}")
    public void i_pass_a_payload_with_json_from_path(String jsonName) throws IOException {
        // Write code here that turns the phrase above into concrete actions
        requestPayload = JsonFileLoader.load(jsonName+".json");
    }
    @Given("I update the jsonpayload {string} for field {string} with value {string}")
    public void i_update_the_jsonpayload_for_field_with_value(String jsonName, String key, String valueToUpdate) throws JsonProcessingException {
       System.out.println("Json name"+jsonName+"   Updating payload with key: " + key + " and value: " + valueToUpdate);
        requestPayload = updateJsonPayload(ensureJsonExtension(jsonName), key, valueToUpdate);
    }

    private String updateJsonPayload(String jsonFileName, String key, String value) throws JsonProcessingException {
        JsonNode updatedJson = JsonFileLoader.loadAndUpdate(jsonFileName, key, value);
        return JsonUtility.toJson(updatedJson);
    }

    private String ensureJsonExtension(String fileName) {
        return fileName.endsWith(".json") ? fileName : fileName + ".json";
    }

    @When("I send GET request to {string}")
    public void sendGetRequest(String endpoint) {
        currentResponse = apiClient.get(endpoint);
    }

    @When("I send POST request to {string}")
    public void sendPostRequest(String endpoint) {
        currentResponse = apiClient.post(endpoint, requestPayload, null);
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