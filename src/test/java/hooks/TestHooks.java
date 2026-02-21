package hooks;

import config.ProjectConfig;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.db.apicore.core.RestAssuredHandler;
import org.db.apicore.reporting.Reporter;
import org.db.apicore.reporting.ExtentReportHandler;

public class TestHooks {

    private static RestAssuredHandler apiHandler;

    @Before
    public void initializeTestEnvironment(Scenario scenario) {

        // Start Extent test for this scenario
        Reporter.startTest(scenario.getName());

        // Log scenario start
        Reporter.info("Starting scenario: " + scenario.getName());

        // Initialize API handler
        ProjectConfig.load();
        apiHandler = new RestAssuredHandler();

        Reporter.pass("API handler initialized successfully");
    }

    @After
    public void tearDown(Scenario scenario) {

        if (scenario.isFailed()) {
            Reporter.fail("Scenario FAILED: " + scenario.getName());
        } else {
            Reporter.pass("Scenario PASSED: " + scenario.getName());
        }

        // End ThreadLocal test
        Reporter.endTest();

        // Flush Extent report
        ExtentReportHandler.flush();
    }

    public static RestAssuredHandler getApiHandler() {
        if (apiHandler == null) {
            throw new IllegalStateException("API handler not initialized");
        }
        return apiHandler;
    }
}
