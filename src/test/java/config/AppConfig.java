package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

/**
 * Loads environment-specific configuration from appsettings.json.
 * This class is designed for template-based API automation projects.
 */
public class AppConfig {

    private static ProjectConfig config;

    /**
     * Loads the configuration only once (singleton style).
     */
    public static ProjectConfig getConfig() {
        if (config == null) {
            config = loadConfig();
        }
        return config;
    }

    /**
     * Reads appsettings.json and returns the environment-specific section.
     */
    private static ProjectConfig loadConfig() {
        try {
            String env = System.getProperty("env", "dev").toLowerCase();
            ObjectMapper mapper = new ObjectMapper();

            File file = new File("src/test/resources/appsettings.json");
            AppSettings settings = mapper.readValue(file, AppSettings.class);

            switch (env) {
                case "qa":
                    return settings.getQa();
                case "stage":
                    return settings.getStage();
                case "prod":
                    return settings.getProd();
                default:
                    return settings.getDev();
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration from appsettings.json", e);
        }
    }

    /**
     * POJO wrapper for all environments.
     */
    public static class AppSettings {
        private ProjectConfig dev;
        private ProjectConfig qa;
        private ProjectConfig stage;
        private ProjectConfig prod;

        public ProjectConfig getDev() { return dev; }
        public ProjectConfig getQa() { return qa; }
        public ProjectConfig getStage() { return stage; }
        public ProjectConfig getProd() { return prod; }
    }
}
