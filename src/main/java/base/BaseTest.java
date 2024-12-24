package base;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    // TestNG-specific hooks
    @BeforeMethod(alwaysRun = true)
    public void testNGSetUp(ITestContext context) {
        System.out.println("Setting up for TestNG test method.");
        TestBase.setUp(context);
    }

    @AfterMethod(alwaysRun = true)
    public void testNGTearDown(ITestContext context) {
        System.out.println("Tearing down after TestNG test method.");
        TestBase.tearDown(context);
    }
}
