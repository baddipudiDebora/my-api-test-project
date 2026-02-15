package utils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class JsonFileLoader {

    public static String load(String fileName) {
        try (InputStream is = JsonFileLoader.class
                .getClassLoader()
                .getResourceAsStream("JsonSamplers/" + fileName)) {

            if (is == null) {
                throw new RuntimeException("JSON file not found: " + fileName);
            }

            return new String(is.readAllBytes(), StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load JSON file: " + fileName, e);
        }
    }
}
