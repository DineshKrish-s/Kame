package testrunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "classpath:features/test.feature",
        //tags = "@Login",
        glue = {
                "StepDef",
                "base"
        },
        plugin = {
                "pretty",
                "html:target/cucumber-reports.html"
        } // Reporting
)
public class testrunner extends AbstractTestNGCucumberTests {
    // No additional code needed
}