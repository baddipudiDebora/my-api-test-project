
package hooks;

import config.ProjectConfig;
import org.db.apicore.core.RestAssuredHandler;
import io.cucumber.java.Before;

public class TestHooks {
    private static RestAssuredHandler apiHandler;

    @Before
    public void initializeTestEnvironment() {
        initializeApiHandler();
    }

    private void initializeApiHandler() {
        ProjectConfig.load();
        apiHandler = new RestAssuredHandler();
    }

    public static RestAssuredHandler getApiHandler() {
        if (apiHandler == null) {
            throw new IllegalStateException("API handler not initialized");
        }
        return apiHandler;
    }
}