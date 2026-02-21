package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.db.apicore.json.JsonUtility;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class JsonFileLoader {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String BASE_PATH = "JsonSamplers/";

    public static String load(String fileName) {
        String fullPath = BASE_PATH + fileName +".json";

        try (InputStream is = JsonFileLoader.class
                .getClassLoader()
                .getResourceAsStream(fullPath)) {

            if (is == null) {
                throw new RuntimeException("JSON file not found on classpath: " + fullPath);
            }

            return new String(is.readAllBytes(), StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load JSON file: " + fullPath, e);
        }
    }

    public static JsonNode loadAndUpdate(String fileName, String jsonPath, String newValue) {
        try {
            String json = load(fileName);
            JsonNode root = OBJECT_MAPPER.readTree(json);
            JsonNode newValueNode = OBJECT_MAPPER.readTree(newValue);
            return JsonUtility.update(root, jsonPath, String.valueOf(newValueNode));
        } catch (Exception e) {
            throw new RuntimeException("Failed to update JSON file: " + fileName, e);
        }
    }
}
