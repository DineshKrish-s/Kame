package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;

import java.net.URL;
import java.util.Properties;

public class TestBase {

    private static Properties config;
    private static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    static {
        try {
            config = new Properties();
            config.load(ClassLoader.getSystemResourceAsStream("config.properties"));
            validateConfig();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load or validate configuration file.", e);
        }
    }

    private static void validateConfig() {
        if (config.getProperty("browser") == null) {
            throw new RuntimeException("Browser not specified in config.properties.");
        }
        if (config.getProperty("grid.url") == null) {
            throw new RuntimeException("Grid URL not specified in config.properties.");
        }
    }

    public static void setUp(ITestContext context) {
        if (threadLocalDriver.get() == null) {
            try {
                String browser = config.getProperty("browser", "chrome");
                String gridUrl = config.getProperty("grid.url");

                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setBrowserName(browser);

                WebDriver driver = createDriverWithRetry(gridUrl, capabilities);
                threadLocalDriver.set(driver);
                context.setAttribute("WebDriver", driver);

                System.out.println("WebDriver initialized for: " + context.getName());
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize WebDriver.", e);
            }
        }
    }

    private static WebDriver createDriverWithRetry(String gridUrl, DesiredCapabilities capabilities) {
        int maxRetries = 3;
        int attempt = 0;

        while (attempt < maxRetries) {
            try {
                return new RemoteWebDriver(new URL(gridUrl), capabilities);
            } catch (Exception e) {
                attempt++;
                System.out.println("Retrying WebDriver initialization (attempt " + attempt + ")");
                if (attempt == maxRetries) {
                    throw new RuntimeException("Failed to connect to Selenium Grid after " + maxRetries + " attempts.", e);
                }
            }
        }
        return null;
    }

    public static WebDriver getDriver(ITestContext context) {
        return threadLocalDriver.get();
    }

    public static WebDriver getDriver() {
        return threadLocalDriver.get();
    }

    public static void tearDown(ITestContext context) {
        WebDriver driver = threadLocalDriver.get();
        if (driver != null) {
            driver.quit();
            threadLocalDriver.remove();
            context.removeAttribute("WebDriver");
            System.out.println("WebDriver cleaned up for: " + context.getName());
        }
    }
}
