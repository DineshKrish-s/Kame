package testrunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

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

        @Override
        @DataProvider(parallel = false)
        public Object[][] scenarios() {
                return super.scenarios();
        }
}