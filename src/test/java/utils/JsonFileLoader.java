package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.db.apicore.json.JsonUtility;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class JsonFileLoader {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String JSON_FILES_PATH = "JsonSamplers/";

    /**
     * Loads a JSON file and updates a field using the base package JsonUtility.
     */
    public static JsonNode loadAndUpdate(String fileName, String path, String newValue) throws JsonProcessingException {
        String jsonContent = load(fileName);
        JsonNode root = OBJECT_MAPPER.readTree(jsonContent);

        // Convert newValue into a JsonNode so types are preserved
        JsonNode newValueNode = OBJECT_MAPPER.readTree(newValue);

        // Delegate update to your base package
        return JsonUtility.update(root, path, String.valueOf(newValueNode));
    }

    /**
     * Loads a JSON file from resources/JsonSamplers/
     */
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
}
