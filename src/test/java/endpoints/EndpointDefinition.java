package endpoints;

import java.util.List;

public class EndpointDefinition {

    private final String path;
    private final List<String> allowedMethods;

    public EndpointDefinition(String path, List<String> allowedMethods) {
        this.path = path;
        this.allowedMethods = allowedMethods;
    }

    public String getPath() {
        return path;
    }

    public List<String> getAllowedMethods() {
        return allowedMethods;
    }
}
