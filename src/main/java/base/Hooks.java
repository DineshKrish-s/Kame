package base;

import io.cucumber.java.After;
import io.cucumber.java.Before;

import utils.CustomLogger;

public class Hooks {

    CustomLogger logger = new CustomLogger(Hooks.class);

    // Cucumber-specific hooks
    @Before
    public void cucumberSetUp() {
        logger.infoWithoutReport("Setting up for Cucumber scenario.");
        TestBase.setUp(null);
    }

    @After
    public void cucumberTearDown() {
        logger.infoWithoutReport("Tearing down after Cucumber scenario.");
        TestBase.tearDown(null);
    }

}