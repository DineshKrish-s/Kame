package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;

import java.net.URL;
import java.util.Properties;

public class TestBase {

    private static Properties config;
    private static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    // Load configuration
    static {
        try {
            config = new Properties();
            System.out.println("Attempting to load 'config/config.properties'...");

            // Specify the correct path to the properties file within test/resources/config
            var resource = Thread.currentThread().getContextClassLoader().getResource("config/config.properties");
            if (resource == null) {
                throw new RuntimeException("'config/config.properties' not found in classpath.");
            }

            System.out.println("Found config.properties at: " + resource);
            config.load(resource.openStream());
            System.out.println("Configuration file loaded successfully.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to load configuration file. Ensure 'config/config.properties' is in src/test/resources/config.", e);
        }
    }

    // Set up WebDriver for TestNG or Cucumber
    public static void setUp(ITestContext context) {
        if (threadLocalDriver.get() == null) {
            try {

                String browser = System.getProperty("browser", config.getProperty("browser", "chrome"));
                String gridUrl = System.getProperty("grid.url", config.getProperty("grid.url", ""));
                String gridIndicator = System.getProperty("grid.indicator", config.getProperty("grid.indicator", "false"));

                WebDriver driver;

                if ("true".equalsIgnoreCase(gridIndicator) && gridUrl != null && !gridUrl.isEmpty()) {
                    DesiredCapabilities capabilities = new DesiredCapabilities();
                    capabilities.setBrowserName(browser);
                    driver = new RemoteWebDriver(new URL(gridUrl), capabilities);
                } else {
                    System.out.println("Grid is disabled or Grid URL is not defined. Falling back to local WebDriver.");
                    driver = createLocalDriver(browser);
                }

                threadLocalDriver.set(driver);

                if (context != null) {
                    context.setAttribute("WebDriver", driver);
                }

                System.out.println("WebDriver initialized for: " + (context != null ? context.getName() : "Cucumber scenario"));

            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize WebDriver.", e);
            }
        }
    }

    private static WebDriver createLocalDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                return createChromeDriver();
            case "firefox":
                return createFirefoxDriver();
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }

    // Helper to create ChromeDriver with options
    private static WebDriver createChromeDriver() {
        System.out.println("Initializing ChromeDriver...");

        // Chrome Options
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized"); // Start browser maximized
        chromeOptions.addArguments("--disable-notifications"); // Disable notifications
        chromeOptions.addArguments("--disable-extensions"); // Disable extensions
        chromeOptions.addArguments("--remote-allow-origins=*"); // Allow cross-origin if needed
        chromeOptions.addArguments("--incognito"); // Launch browser in incognito mode
        chromeOptions.setAcceptInsecureCerts(true); // Accept insecure certificates

        // Set additional desired capabilities if required
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

        return new org.openqa.selenium.chrome.ChromeDriver(chromeOptions);
    }

    // Helper to create FirefoxDriver with options
    private static WebDriver createFirefoxDriver() {
        System.out.println("Initializing FirefoxDriver...");

        // Firefox Options
        org.openqa.selenium.firefox.FirefoxOptions firefoxOptions = new org.openqa.selenium.firefox.FirefoxOptions();
        firefoxOptions.addArguments("--start-maximized"); // Start browser maximized
        firefoxOptions.addArguments("--disable-notifications"); // Disable notifications
        firefoxOptions.setAcceptInsecureCerts(true); // Accept insecure certificates

        // Set additional desired capabilities if required
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("moz:firefoxOptions", firefoxOptions);

        return new org.openqa.selenium.firefox.FirefoxDriver(firefoxOptions);
    }


    // Overloaded setUp method for Cucumber
    public static void setUp() {
        setUp(null);
    }

    // Get WebDriver instance
    public static WebDriver getDriver() {
        return threadLocalDriver.get();
    }

    // Tear down WebDriver for TestNG or Cucumber
    public static void tearDown(ITestContext context) {
        WebDriver driver = threadLocalDriver.get();
        if (driver != null) {
            driver.quit();
            threadLocalDriver.remove();
            if (context != null) {
                context.removeAttribute("WebDriver");
            }
            System.out.println("WebDriver cleaned up for: " + (context != null ? context.getName() : "Cucumber scenario"));
        }
    }

    // Overloaded tearDown method for Cucumber
    public static void tearDown() {
        tearDown(null);
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
//                System.out.println("WebDriver initialized for: " + context.getName());
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
//                System.out.println("Retrying WebDriver initialization (attempt " + attempt + ")");
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
//            System.out.println("WebDriver cleaned up for: " + context.getName());
//        }
//    }
}