package base;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.CustomLogger;

public class BaseTest {

    CustomLogger logger = new CustomLogger(BaseTest.class);

    // TestNG-specific hooks
    @BeforeMethod()
    public void testNGSetUp(ITestContext context) {
        logger.infoWithoutReport("Setting up for TestNG test method.");
        TestBase.setUp(context);
    }

    @AfterMethod()
    public void testNGTearDown(ITestContext context) {
        logger.infoWithoutReport("Tearing down after TestNG test method.");
        TestBase.tearDown(context);
    }
}
