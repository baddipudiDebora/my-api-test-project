package client;

import config.AppConfig;
import config.ProjectConfig;
import endpoints.EndpointDefinition;
import endpoints.EndpointRegistry;
import org.db.apicore.core.RestAssuredHandler;
import org.db.apicore.json.JsonUtility;
import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.http.Headers;
import utils.JsonFileLoader;

import java.util.HashMap;
import java.util.Map;

public class APIClient {

    private final RestAssuredHandler api = new RestAssuredHandler();
    private final ProjectConfig config = AppConfig.getConfig();

    private String endpointName;
    private String fullUrl;
    private String method;
    private String requestBody;

    private final Map<String, String> pathParams = new HashMap<>();
    private final Map<String, String> queryParams = new HashMap<>();

    // -------------------------
    // GIVEN
    // -------------------------

    public APIClient withEndpoint(String endpointName) {
        this.endpointName = endpointName;
        EndpointDefinition def = EndpointRegistry.get(endpointName);
        this.fullUrl = buildUrl(def.getPath());
        return this;
    }

    public APIClient withMethod(String method) {
        EndpointDefinition def = EndpointRegistry.get(endpointName);
        String upper = method.toUpperCase();

        if (!def.getAllowedMethods().contains(upper)) {
            throw new IllegalArgumentException(
                    "HTTP method " + upper + " not allowed for endpoint '" + endpointName + "'"
            );
        }

        this.method = upper;
        return this;
    }

    public APIClient withPathParam(String name, String value) {
        this.pathParams.put(name, value);
        return this;
    }

    public APIClient withQueryParam(String name, String value) {
        this.queryParams.put(name, value);
        return this;
    }

    public APIClient withJsonBodyFromFile(String jsonFile, Map<String, String> updates) {
        String json = JsonFileLoader.load(jsonFile);
        JsonNode root = JsonUtility.parse(json);

        if (updates != null) {
            updates.forEach((key, value) -> JsonUtility.update(root, key, value));
        }

        this.requestBody = JsonUtility.toJson(root);
        return this;
    }

    public APIClient withRawBody(String body) {
        this.requestBody = body;
        return this;
    }

    // -------------------------
    // WHEN
    // -------------------------

    public APIClient execute() {
        String resolvedUrl = applyPathParams(fullUrl);
        resolvedUrl = applyQueryParams(resolvedUrl);
        api.executeRequest(method, resolvedUrl, requestBody);
        return this;
    }

    // --------------git st-----------
    // THEN / accessors
    // -------------------------

    public int getStatusCode() {
        return api.getStatusCode();
    }

    public String getStatusDescription() {
        return api.getStatusDescription();
    }

    public Headers getHeaders() {
        return api.getResponseHeaders();
    }

    public String getBody() {
        return api.getBody();
    }

    public String getJsonValue(String path) {
        return api.getJsonValue(path);
    }

    // -------------------------
    // Helpers
    // -------------------------

    private String buildUrl(String endpointPath) {
        if (endpointPath.startsWith("http")) {
            return endpointPath;
        }
        return config.getBaseUrl() + endpointPath;
    }

    private String applyPathParams(String url) {
        String updated = url;
        for (var entry : pathParams.entrySet()) {
            updated = updated.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return updated;
    }

    private String applyQueryParams(String url) {
        if (queryParams.isEmpty()) {
            return url;
        }

        StringBuilder sb = new StringBuilder(url);
        sb.append(url.contains("?") ? "&" : "?");

        boolean first = true;
        for (var entry : queryParams.entrySet()) {
            if (!first) sb.append("&");
            sb.append(entry.getKey()).append("=").append(entry.getValue());
            first = false;
        }

        return sb.toString();
    }

}
