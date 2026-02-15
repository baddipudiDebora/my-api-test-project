package client;

import config.AppConfig;
import config.ProjectConfig;
import org.db.apicore.core.RestAssuredHandler;
import org.db.apicore.json.JsonUtility;
import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.response.Response;
import utils.JsonFileLoader;

import java.util.Map;

public class APIClient {

    private final RestAssuredHandler api;
    private final ProjectConfig config;

    public APIClient() {
        this.config = AppConfig.getConfig();
        this.api = new RestAssuredHandler();
    }

    /**
     * Send GET request
     */
    public String get(String endpoint) {
        String fullUrl = buildUrl(endpoint);
        return api.executeRequestAsString("GET", fullUrl, null);
    }

    /**
     * Send POST request with optional JSON updates
     */
    public String post(String endpoint, String jsonFile, Map<String, String> updates) {

        String fullUrl = buildUrl(endpoint);

        String json = JsonFileLoader.load(jsonFile);
        JsonNode root = JsonUtility.parse(json);

        if (updates != null) {
            updates.forEach((key, value) ->
                    JsonUtility.update(root, key, value)
            );
        }

        String updatedBody = JsonUtility.toJson(root);

        return api.executeRequestAsString("POST", fullUrl, updatedBody);
    }

    /**
     * Send PUT request
     */
    public Response put(String endpoint, String jsonFile, Map<String, String> updates) {

        String fullUrl = buildUrl(endpoint);

        String json = JsonFileLoader.load(jsonFile);
        JsonNode root = JsonUtility.parse(json);

        if (updates != null) {
            updates.forEach((key, value) ->
                    JsonUtility.update(root, key, value)
            );
        }

        String updatedBody = JsonUtility.toJson(root);

        return api.executeRequest("PUT", fullUrl, updatedBody);
    }

    /**
     * Send DELETE request
     */
    public Response delete(String endpoint) {
        String fullUrl = buildUrl(endpoint);
        return api.executeRequest("DELETE", fullUrl, null);
    }

    /**
     * Helper method to prepend base URL
     */
    private String buildUrl(String endpoint) {
        if (endpoint.startsWith("http")) {
            return endpoint;
        }
        return config.getBaseUrl() + endpoint;
    }
}
