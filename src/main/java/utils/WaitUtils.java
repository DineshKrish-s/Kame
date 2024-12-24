package utils;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils extends TestBase {

    private static WebDriverWait wait;

    public WaitUtils() {
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
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

    public static void waitForVisibility(By element) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
    }

    public static void waitForVisibility(WebElement element, int sec) {
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(sec));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForVisibility(By element, int sec) {
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(sec));
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
    }

    public static void waitForClickability(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForClickability(By element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForClickability(WebElement element, int sec) {
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(sec));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForClickability(By element, int sec) {
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(sec));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForInvisibility(WebElement element) {
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public static void waitForInvisibility(By element) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(element));
    }

    public static void waitForInvisibility(WebElement element, int sec) {
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(sec));
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public static void waitForInvisibility(By element, int sec) {
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(sec));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(element));
    }

    public static void waitForElementToContain(WebElement element, String text){
        wait.until(ExpectedConditions.textToBePresentInElementValue(element, text));
    }

    public static void waitForElementToContain(By element, String text) {
        wait.until(ExpectedConditions.textToBePresentInElementValue(element, text));
    }

    public static void waitForElementToContain(WebElement element, String text, int sec) {
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(sec));
        wait.until(ExpectedConditions.textToBePresentInElementValue(element, text));
    }

    public static void waitForElementToContain(By element, String text, int sec) {
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(sec));
        wait.until(ExpectedConditions.textToBePresentInElementValue(element, text));
    }

    /**
     * Waits for the specified element to be refreshed in the DOM.
     *
     */
    public static void waitForElementToBeRefreshed(WebElement element){
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(element)));
    }

    public static void waitForElementToBeRefreshed(By element){
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(element)));
    }

    public static void waitForElementToBeRefreshed(WebElement element, int sec){
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(sec));
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(element)));
    }

    public static void waitForElementToBeRefreshed(By element, int sec){
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(sec));
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(element)));
    }

    /**
     * Waits for the web page to be fully loaded.
     **/
    public static void waitForWebPageToBeReady(){
        wait.until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }

    /**
     * Waits for the web page to be fully loaded.
     *
     * @param timeout  The timeout in seconds to wait for the page to be ready.
     */
    public static void waitForPageToBeReady(int timeout) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        wait.until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }

    /**
     * Waits until the URL of the current page contains the specified substring.
     *
     * @param substring The substring that the URL should contain.
     */
    public static void waitForUrlToContain(String substring) {
        wait.until(ExpectedConditions.urlContains(substring));
    }

    /**
     * Waits until the URL of the current page contains the specified substring.
     *
     * @param substring The substring that the URL should contain.
     * @param timeout The timeout in seconds to wait for the condition.
     */
    public static void waitForUrlToContain(String substring, int timeout) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.urlContains(substring));
    }

}
