package endpoints;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EndpointRegistry {

    private static final Map<String, EndpointDefinition> endpoints = new HashMap<>();

    static {
        endpoints.put("user", new EndpointDefinition(
                "/user/{username}",
                List.of("GET", "PUT", "DELETE")
        ));

        endpoints.put("login", new EndpointDefinition(
                "/user/login",
                List.of("GET")
        ));

        endpoints.put("createUser", new EndpointDefinition(
                "/user}",
                List.of("PUT")
        ));
    }

    public static EndpointDefinition get(String name) {
        return endpoints.get(name);
    }
}
