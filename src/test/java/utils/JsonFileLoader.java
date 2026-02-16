package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.db.apicore.json.JsonUtility;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.*;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class JsonFileLoader {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String JSON_FILES_PATH = "JsonSamplers/";
    private static final ObjectMapper mapper = new ObjectMapper();
    public static JsonNode loadAndUpdate(String fileName, String path, String newValue) throws JsonProcessingException {
        String jsonContent = load(fileName);
        com.fasterxml.jackson.databind.JsonNode root = OBJECT_MAPPER.readTree(jsonContent);
        return update(root, path, newValue);
    }

    public static String load(String fileName) {
        try (InputStream is = JsonFileLoader.class
                .getClassLoader()
                .getResourceAsStream(JSON_FILES_PATH + fileName)) {

            if (is == null) {
                throw new RuntimeException("JSON file not found: " + fileName);
            }

            return new String(is.readAllBytes(), StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load JSON file: " + fileName, e);
        }
    }
    /**
     * Update JSON using a key path like "user.address.city"
     */
    public static JsonNode update(JsonNode json, String jsonPath, String newValue) {
        ObjectNode node = (ObjectNode) json;
        String[] parts = jsonPath.split("\\.");

        for (int i = 0; i < parts.length - 1; i++) {
            node = (ObjectNode) node.get(parts[i]);
        }

        node.put(parts[parts.length - 1], newValue);
        return json;
    }

}