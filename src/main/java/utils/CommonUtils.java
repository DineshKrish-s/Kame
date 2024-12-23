package utils;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import com.github.javafaker.Faker;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CommonUtils extends TestBase {

    public String getFirstName() {
        Faker faker = new Faker();
        return faker.name().firstName();
    }

    public String getLastName() {
        Faker faker = new Faker();
        return faker.name().lastName();
    }

    public String getDateOfBirth() {
        Faker faker = new Faker();
        return faker.date().birthday().toString();
    }

    // Generate a random number with a specified number of digits
    public long getRandomNumber(int numberOfDigits) {
        Faker faker = new Faker();
        return faker.number().randomNumber(numberOfDigits, true);
    }

    // Generate a random digit string of specified length
    public String getRandomDigit(int length) {
        Faker faker = new Faker();
        StringBuilder digits = new StringBuilder();
        for (int i = 0; i < length; i++) {
            digits.append(faker.number().digit());
        }
        return digits.toString();
    }

    public String getRandomEmail(){
        Faker faker = new Faker();
        return faker.internet().emailAddress();
    }

    public static String generateRandomString(int length) {
        Faker faker = new Faker();
        StringBuilder randomString = new StringBuilder();

        // Append random alphabetic characters to the string
        for (int i = 0; i < length; i++) {
            randomString.append(faker.letterify("?")); // Replace '?' with a random letter
        }

        return randomString.toString();
    }

    public static String getCurrentTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public static WebElement findElement(By element){
        return getDriver().findElement(element);
    }

    public static List<WebElement> findElements(By element){
        return getDriver().findElements(element);
    }

    public static void clickElement(WebElement element){
        WaitUtils.waitForClickability(element);
        element.click();
    }

    public static void clickElement(WebElement element, String message){
        WaitUtils.waitForClickability(element);
        element.click();
    }

    public static void clickElement(By element){
        WebElement elem = findElement(element);
        WaitUtils.waitForClickability(elem);
        elem.click();
    }

    public static void clickElement(By element, String message){
        WebElement elem = findElement(element);
        WaitUtils.waitForVisibility(elem);
        elem.click();
    }

    public static String getText(WebElement element){
        WaitUtils.waitForVisibility(element);
        return element.getText();
    }

    public static String getText(WebElement element, String message){
        WaitUtils.waitForVisibility(element);
        return element.getText();
    }

    public static String getText(By element){
        WebElement elem = findElement(element);
        WaitUtils.waitForVisibility(elem);
        return elem.getText();
    }

    public static String getText(By element, String message){
        WebElement elem = findElement(element);
        WaitUtils.waitForVisibility(elem);
        return elem.getText();
    }

    public static void inputText(WebElement element, String text){
        WaitUtils.waitForClickability(element);
        element.sendKeys(text);
    }

    public static void inputText(WebElement element, String text, String message){
        WaitUtils.waitForVisibility(element);
        element.sendKeys(text);
    }

    public static void inputText(By element, String text){
        WebElement elem = findElement(element);
        WaitUtils.waitForVisibility(elem);
        elem.sendKeys(text);
    }

    public static void inputText(By element, String text,String message){
        WebElement elem = findElement(element);
        WaitUtils.waitForVisibility(elem);
        elem.sendKeys(text);
    }

    /**
     * Inputs text into a field character by character with a delay between each character.
     *
     * @param element  The WebElement where the text will be entered.
     * @param text     The text to be entered.
     * @param delaySec  Delay in seconds between each character.
     * @throws InterruptedException If the thread sleep is interrupted.
     */
    public static void inputTextSlowly(WebElement element, String text, int delaySec) throws InterruptedException {
        for (char c : text.toCharArray()) {
            inputText(element, String.valueOf(c));
            WaitUtils.implicitWait(delaySec);
        }
    }

    public static void inputTextSlowly(By element, String text, int delaySec) throws InterruptedException {
        for (char c : text.toCharArray()) {
            inputText(element, String.valueOf(c));
            WaitUtils.implicitWait(delaySec);
        }
    }

    public static void inputTextSlowly(WebElement element, String text, int delaySec, String messasge) throws InterruptedException {
        for (char c : text.toCharArray()) {
            inputText(element, String.valueOf(c));
            WaitUtils.implicitWait(delaySec);
        }
    }

    public static void inputTextSlowly(By element, String text, int delaySec, String messasge) throws InterruptedException {
        for (char c : text.toCharArray()) {
            inputText(element, String.valueOf(c));
            WaitUtils.implicitWait(delaySec);
        }
    }

    /**
     * Scrolls the webpage by the specified number of pixels.
     *
     * @param x      The horizontal pixels to scroll.
     * @param y      The vertical pixels to scroll.
     */
    public static void scrollByPixel(int x, int y) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
        jsExecutor.executeScript("window.scrollBy(arguments[0], arguments[1]);", x, y);
    }

    /**
     * Scrolls the webpage to a specific position.
     *
     * @param x      The x-coordinate to scroll to.
     * @param y      The y-coordinate to scroll to.
     */
    public static void scrollToPosition(int x, int y) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
        jsExecutor.executeScript("window.scrollTo(arguments[0], arguments[1]);", x, y);
    }

    /**
     * Scrolls the webpage to the bottom.
     *
     */
    public static void scrollToBottom() {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
        jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    /**
     * Scrolls the webpage to the top.
     *
     */
    public static void scrollToTop() {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
        jsExecutor.executeScript("window.scrollTo(0, 0);");
    }

    /**
     * Scrolls horizontally by a specified number of pixels.
     *
     * @param x      The horizontal pixels to scroll.
     */
    public static void scrollHorizontally(int x) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
        jsExecutor.executeScript("window.scrollBy(arguments[0], 0);", x);
    }

    /**
     * Scrolls vertically within a scrollable element.
     *
     * @param element The scrollable container element.
     * @param y       The vertical pixels to scroll.
     */
    public static void scrollWithinElement(WebElement element, int y) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
        jsExecutor.executeScript("arguments[0].scrollTop = arguments[1];", element, y);
    }

    /**
     * Performs infinite scrolling until the end of the page.
     *
     * @throws InterruptedException If the thread sleep is interrupted.
     */
    public static void infiniteScroll() throws InterruptedException {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
        long lastHeight = (long) jsExecutor.executeScript("return document.body.scrollHeight;");

        while (true) {
            jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            WaitUtils.implicitWait(2); // Pause to allow content loading
            long newHeight = (long) jsExecutor.executeScript("return document.body.scrollHeight;");
            if (newHeight == lastHeight) {
                break;
            }
            lastHeight = newHeight;
        }
    }

    public static void scrollIntoView(By element){
        JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
        jsExecutor.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", findElement(element));
    }

    public static void scrollIntoView(By element, String message){
        JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
        jsExecutor.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", findElement(element));
    }

    public static void scrollIntoView(WebElement element){
        JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
        WaitUtils.waitForVisibility(element);
        jsExecutor.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }

    public static void scrollIntoView(WebElement element, String message){
        JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
        jsExecutor.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }

    /**
     * Checks if the specified WebElement is present and displayed.
     *
     * @param element The WebElement to check.
     * @return True if the element is present and displayed; otherwise, false.
     */
    public static boolean isElementPresent(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            // Element is not found in the DOM
            return false;
        } catch (Exception e) {
            // Catch other unexpected exceptions
            return false;
        }
    }

    public static boolean isElementPresent(By element){
        try {
            return findElement(element).isDisplayed();
        } catch (NoSuchElementException e) {
            // Element is not found in the DOM
            return false;
        } catch (Exception e) {
            // Catch other unexpected exceptions
            return false;
        }
    }

    public static boolean isElementVisible(WebElement element){
        return element.isDisplayed();
    }

    public static boolean isElementVisible(By element){
        return findElement(element).isDisplayed();
    }

    public static boolean isElementEnabled(WebElement element){
        return element.isEnabled();
    }

    public static boolean isElementEnabled(By element){
        return findElement(element).isEnabled();
    }

    public static boolean isElementSelected(WebElement element){
        return element.isSelected();
    }

    public static boolean isElementSelected(By element){
        return findElement(element).isSelected();
    }


}
