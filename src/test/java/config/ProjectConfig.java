package config;

public class ProjectConfig {

    private String baseUrl;
    private int timeout;
    private boolean logRequests;

    public String getBaseUrl() {
        return baseUrl;
    }

    public int getTimeout() {
        return timeout;
    }

    public boolean isLogRequests() {
        return logRequests;
    }
    public static ProjectConfig load() {
        return AppConfig.getConfig();
    }

}
