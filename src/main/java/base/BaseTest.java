package base;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.CustomLogger;

import java.io.IOException;

public class BaseTest {

    CustomLogger logger = new CustomLogger(BaseTest.class);

    TestBase testBase = new TestBase();

    // TestNG-specific hooks
    @BeforeMethod()
    public void testNGSetUp(ITestContext context) throws IOException {
        logger.infoWithoutReport("Setting up for TestNG test method.");
        testBase.setUp(context);
    }

    @AfterMethod()
    public void testNGTearDown(ITestContext context) {
        logger.infoWithoutReport("Tearing down after TestNG test method.");
        testBase.tearDown(context);
    }
}
