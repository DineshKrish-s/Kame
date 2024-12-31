package managers;

import base.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariOptions;
import utils.CustomLogger;

public class DriverManager {

    private final CustomLogger logger = new CustomLogger(DriverManager.class);

    /**
     * Creates a local WebDriver instance based on the specified browser.
     *
     * @param browser The browser name (e.g., "chrome", "firefox", "edge", "safari").
     * @return A WebDriver instance for the specified browser.
     */
    public WebDriver createLocalDriver(String browser) {
        boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "false")); // Check for headless mode

        WebDriver driver;
        switch (browser.toLowerCase()) {
            case "chrome" -> driver = createDriver(new ChromeOptions(), isHeadless);
            case "firefox" -> driver = createDriver(new FirefoxOptions(), isHeadless);
            case "edge" -> driver = createDriver(new EdgeOptions(), isHeadless);
            case "safari" -> driver = createDriver(new SafariOptions(), false); // Safari doesn't support headless
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        logger.infoWithoutReport("WebDriver initialized for browser: " + browser + (isHeadless ? " (headless)" : ""));
        return driver;
    }

    /**
     * Creates a WebDriver instance based on the browser options.
     *
     * @param options    The browser-specific options (e.g., ChromeOptions, FirefoxOptions).
     * @param isHeadless Whether to enable headless mode (ignored for Safari).
     * @return A WebDriver instance.
     */
    private WebDriver createDriver(Object options, boolean isHeadless) {
        configureOptions(options, isHeadless);

        if (options instanceof ChromeOptions chromeOptions) {
            return new org.openqa.selenium.chrome.ChromeDriver(chromeOptions);
        } else if (options instanceof FirefoxOptions firefoxOptions) {
            return new org.openqa.selenium.firefox.FirefoxDriver(firefoxOptions);
        } else if (options instanceof EdgeOptions edgeOptions) {
            return new org.openqa.selenium.edge.EdgeDriver(edgeOptions);
        } else if (options instanceof SafariOptions safariOptions) {
            return new org.openqa.selenium.safari.SafariDriver(safariOptions);
        } else {
            throw new IllegalArgumentException("Unsupported options type: " + options.getClass().getName());
        }
    }

    /**
     * Configures common and headless options for the specified browser options.
     *
     * @param options    The browser-specific options (e.g., ChromeOptions, FirefoxOptions).
     * @param isHeadless Whether to enable headless mode.
     */
    private void configureOptions(Object options, boolean isHeadless) {
        if (options instanceof ChromeOptions chromeOptions) {
            chromeOptions.addArguments("--disable-notifications", "--disable-extensions", "--incognito");
            chromeOptions.setAcceptInsecureCerts(true);

            if (isHeadless) {
                chromeOptions.addArguments("--headless", "--disable-gpu", "--window-size=1920,1080");
            } else {
                chromeOptions.addArguments("--start-maximized"); // Maximize the browser window in non-headless mode
            }
        } else if (options instanceof FirefoxOptions firefoxOptions) {
            firefoxOptions.addArguments("--disable-notifications", "--incognito");
            firefoxOptions.setAcceptInsecureCerts(true);

            if (isHeadless) {
                firefoxOptions.addArguments("--headless");
                firefoxOptions.addPreference("layout.css.devPixelsPerPx", "1.0"); // Optional: Ensure proper scaling in headless mode
            } else {
                firefoxOptions.addArguments("--start-maximized"); // Maximize the browser window in non-headless mode
            }
        } else if (options instanceof EdgeOptions edgeOptions) {
            edgeOptions.addArguments("--disable-notifications", "--inprivate");
            edgeOptions.setAcceptInsecureCerts(true);

            if (isHeadless) {
                edgeOptions.addArguments("--headless", "--disable-gpu", "--window-size=1920,1080");
            } else {
                edgeOptions.addArguments("--start-maximized"); // Maximize the browser window in non-headless mode
            }
        } else if (options instanceof SafariOptions safariOptions) {
            safariOptions.setCapability("safari.cleanSession", true); // Safari-specific setting
            // Safari maximization requires manual scripting or defaults to the last used window size.
        } else {
            throw new IllegalArgumentException("Unsupported options type: " + options.getClass().getName());
        }
    }

}
