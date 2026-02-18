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

    private final APIClient client = new APIClient();

    @Given("I call {string} endpoint with http method {string}")
    public void iCallEndpointWithMethod(String endpointName, String method) {
        client.withEndpoint(endpointName)
                .withMethod(method);
    }

    @Given("I set path parameter {string} to {string}")
    public void iSetPathParam(String name, String value) {
        client.withPathParam(name, value);
    }

    @Given("I set query parameter {string} to {string}")
    public void iSetQueryParam(String name, String value) {
        client.withQueryParam(name, value);
    }

    @Given("I load body from file {string}")
    public void iLoadBodyFromFile(String fileName) {
        client.withJsonBodyFromFile(fileName, null);
    }

    @When("I execute the request")
    public void iExecuteTheRequest() {
        client.execute();
    }

    @Then("the status code should be {int}")
    public void theStatusCodeShouldBe(int expected) {
        org.junit.jupiter.api.Assertions.assertEquals(expected, client.getStatusCode());
    }

    @Then("the response body should contain {string}")
    public void bodyShouldContain(String expected) {
        org.junit.jupiter.api.Assertions.assertTrue(
                client.getBody().contains(expected),
                "Body did not contain: " + expected
        );
    }
}
