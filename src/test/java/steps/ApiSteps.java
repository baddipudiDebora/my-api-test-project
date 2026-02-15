package steps;

import client.APIClient;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;

public class ApiSteps {

    private String response;
    private final APIClient client = new APIClient();

    @When("I send GET request to {string}")
    public void sendGet(String endpoint) {
        response = client.get(endpoint);
    }

    @When("I send POST request to {string} with body:")
    public void sendPost(String endpoint, String body) {
        response = client.post(endpoint, body, null);
    }

    @Then("the response status should be {int}")
    public void verifyStatus(int status) {
       // Assertions.assertThat();
    }
}
