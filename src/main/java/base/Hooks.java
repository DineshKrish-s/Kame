package base;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {

    // Cucumber-specific hooks
    @Before
    public void cucumberSetUp() {
        System.out.println("Setting up for Cucumber scenario.");
        TestBase.setUp(null);
    }

    @After
    public void cucumberTearDown() {
        System.out.println("Tearing down after Cucumber scenario.");
        TestBase.tearDown(null);
    }

}