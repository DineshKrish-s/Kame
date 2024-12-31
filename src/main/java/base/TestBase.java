package base;

import managers.ConfigLoader;
import managers.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import utils.CommonUtils;
import utils.CustomLogger;

import java.io.IOException;
import java.net.URL;

public class TestBase {

    protected static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    static CustomLogger logger = new CustomLogger(TestBase.class);

    private static final DriverManager driverManager = new DriverManager();
    private static final ConfigLoader configLoader = new ConfigLoader();

    // Set up WebDriver for TestNG or Cucumber
    void setUp(ITestContext context) throws IOException {

        configLoader.loadConfig();
        if (threadLocalDriver.get() == null) {
            try {

                String browser = System.getProperty("browser", System.getProperty("browser", "chrome"));
                String gridUrl = System.getProperty("nodeURL", System.getProperty("nodeURL", ""));
                String gridIndicator = System.getProperty("nodeIndicator", System.getProperty("nodeIndicator", "false"));

                WebDriver driver;

                if ("true".equalsIgnoreCase(gridIndicator) && gridUrl != null && !gridUrl.isEmpty()) {
                    DesiredCapabilities capabilities = new DesiredCapabilities();
                    capabilities.setBrowserName(browser);
                    driver = new RemoteWebDriver(new URL(gridUrl), capabilities);
                } else {
                    logger.infoWithoutReport("Grid is disabled or Grid URL is not defined. Falling back to local WebDriver.");
                    driver = driverManager.createLocalDriver(browser);
                }

                threadLocalDriver.set(driver);

                if (context != null) {
                    context.setAttribute("WebDriver", driver);
                }

                logger.infoWithoutReport("WebDriver initialized for: " + (context != null ? context.getName() : "Cucumber scenario"));

                CommonUtils.getUrl(System.getProperty("url"));

            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize WebDriver.", e);
            }
        }
    }

    public void setUp() throws IOException {
        setUp(null);
    }

    // Tear down WebDriver for TestNG or Cucumber
    public void tearDown(ITestContext context) {
        WebDriver driver = threadLocalDriver.get();
        if (driver != null) {
            driver.quit();
            threadLocalDriver.remove();
            if (context != null) {
                context.removeAttribute("WebDriver");
            }
            logger.infoWithoutReport("WebDriver cleaned up for: " + (context != null ? context.getName() : "Cucumber scenario"));
        }
    }

    // Overloaded tearDown method for Cucumber
    public void tearDown() {
        tearDown(null);
    }

    /**
     * Retrieves the current WebDriver instance for the current thread.
     *
     * @return The WebDriver instance.
     */
    public static WebDriver getDriver() {
        return threadLocalDriver.get();
    }

    /**
     * Sets the WebDriver instance for the current thread.
     *
     * @param driver The WebDriver instance to set.
     */
    protected static void setDriver(WebDriver driver) {
        threadLocalDriver.set(driver);
    }

    /**
     * Removes the WebDriver instance for the current thread.
     */
    protected static void removeDriver() {
        threadLocalDriver.remove();
    }


//
//    public static void setUp(ITestContext context) {
//        if (threadLocalDriver.get() == null) {
//            try {
//                String browser = config.getProperty("browser", "chrome");
//                String gridUrl = config.getProperty("grid.url");
//
//                DesiredCapabilities capabilities = new DesiredCapabilities();
//                capabilities.setBrowserName(browser);
//
//                WebDriver driver = createDriverWithRetry(gridUrl, capabilities);
//                threadLocalDriver.set(driver);
//                context.setAttribute("WebDriver", driver);
//
//                logger.infoWithoutReport("WebDriver initialized for: " + context.getName());
//            } catch (Exception e) {
//                throw new RuntimeException("Failed to initialize WebDriver.", e);
//            }
//        }
//    }
//
//    private static WebDriver createDriverWithRetry(String gridUrl, DesiredCapabilities capabilities) {
//        int maxRetries = 3;
//        int attempt = 0;
//
//        while (attempt < maxRetries) {
//            try {
//                return new RemoteWebDriver(new URL(gridUrl), capabilities);
//            } catch (Exception e) {
//                attempt++;
//                logger.infoWithoutReport("Retrying WebDriver initialization (attempt " + attempt + ")");
//                if (attempt == maxRetries) {
//                    throw new RuntimeException("Failed to connect to Selenium Grid after " + maxRetries + " attempts.", e);
//                }
//            }
//        }
//        return null;
//    }
//
//    public static WebDriver getDriver() {
//        return threadLocalDriver.get();
//    }
//
//    public static void tearDown(ITestContext context) {
//        WebDriver driver = threadLocalDriver.get();
//        if (driver != null) {
//            driver.quit();
//            threadLocalDriver.remove();
//            context.removeAttribute("WebDriver");
//            logger.infoWithoutReport("WebDriver cleaned up for: " + context.getName());
//        }
//    }
}