package endpoints;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EndpointRegistry {

    private static final Map<String, EndpointDefinition> endpoints = new HashMap<>();

    static {
        endpoints.put("user", new EndpointDefinition( "/user/{username}", List.of("GET", "PUT", "DELETE") ));
        endpoints.put("login", new EndpointDefinition( "/user/login", List.of("GET") ));
        endpoints.put("pet", new EndpointDefinition("/pet", List.of("GET", "PUT", "POST")
        ));
    }

    public static EndpointDefinition get(String name) {
        return endpoints.get(name);
    }
}
