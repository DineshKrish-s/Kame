package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {
    private WebDriver driver;
    private static WebDriverWait wait;

    public WaitUtils(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public static void implicitWait(int sec){
        try {
            Thread.sleep(sec * 1000L); // Pause to allow content loading
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void waitForVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForClickability(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForVisibility(By element) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
    }

    public static void waitForClickability(By element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }
}
