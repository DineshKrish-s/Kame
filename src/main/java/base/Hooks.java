package base;

import io.cucumber.java.After;
import io.cucumber.java.Before;

import utils.CustomLogger;

import java.io.IOException;

public class Hooks {

    CustomLogger logger = new CustomLogger(Hooks.class);

    TestBase testBase = new TestBase();

    // Cucumber-specific hooks
    @Before
    public void cucumberSetUp() throws IOException {
        logger.infoWithoutReport("Setting up for Cucumber scenario.");
        testBase.setUp(null);
    }

    @After
    public void cucumberTearDown() {
        logger.infoWithoutReport("Tearing down after Cucumber scenario.");
        testBase.tearDown(null);
    }

}