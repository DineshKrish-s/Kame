package managers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverManager {

    private static ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (driverThread.get() == null) {
            System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless", "--disable-gpu");
            driverThread.set(new ChromeDriver(options));
        }
        return driverThread.get();
    }

    public static void closeDriver() {
        if (driverThread.get() != null) {
            driverThread.get().quit();
            driverThread.remove();
        }
    }
}
