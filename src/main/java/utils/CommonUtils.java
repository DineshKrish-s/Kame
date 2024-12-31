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

    static CustomLogger logger = new CustomLogger(CommonUtils.class);

    static final String scrollView = "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});";

    CommonUtils(){}

    public static String getFirstName() {
        Faker faker = new Faker();
        return faker.name().firstName();
    }

    public static String getLastName() {
        Faker faker = new Faker();
        return faker.name().lastName();
    }

    public static String getDateOfBirth() {
        Faker faker = new Faker();
        return faker.date().birthday().toString();
    }

    // Generate a random number with a specified number of digits
    public static long getRandomNumber(int numberOfDigits) {
        Faker faker = new Faker();
        return faker.number().randomNumber(numberOfDigits, true);
    }

    // Generate a random digit string of specified length
    public static String getRandomDigit(int length) {
        Faker faker = new Faker();
        StringBuilder digits = new StringBuilder();
        for (int i = 0; i < length; i++) {
            digits.append(faker.number().digit());
        }
        return digits.toString();
    }

    public static String getRandomEmail(){
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

    public static void getUrl(String url){
        getDriver().get(url);
    }

    public static void clickElement(WebElement element){
        WaitUtils.waitForClickability(element);
        element.click();
        logger.infoWithoutReport(element + " is clicked");
    }

    public static void clickElement(WebElement element, String message){
        WaitUtils.waitForClickability(element);
        element.click();
        logger.infoWithoutReport(message + " is clicked");
    }

    public static void clickElement(By element){
        WaitUtils.waitForClickability(element);
        findElement(element).click();
        logger.infoWithoutReport(element + " is clicked");
    }

    public static void clickElement(By element, String message){
        WaitUtils.waitForClickability(element);
        findElement(element).click();
        logger.infoWithoutReport(message + " is clicked");
    }

    public static String getText(WebElement element){
        WaitUtils.waitForVisibility(element);
        String text = element.getText();
        logger.infoWithoutReport(text + " is text from " + element);
        return text;
    }

    public static String getText(WebElement element, String message){
        WaitUtils.waitForVisibility(element);
        String text = element.getText();
        logger.infoWithoutReport(text + " is text from " + message);
        return text;
    }

    public static String getText(By element){
        WaitUtils.waitForVisibility(element);
        String text = findElement(element).getText();
        logger.infoWithoutReport(text + " is text from " + element);
        return text;
    }

    public static String getText(By element, String message){
        WaitUtils.waitForVisibility(element);
        String text = findElement(element).getText();
        logger.infoWithoutReport(text + " is text from " + message);
        return text;
    }

    public static void inputText(WebElement element, String text){
        WaitUtils.waitForClickability(element);
        element.sendKeys(text);
        logger.infoWithoutReport(text + " is text entered in " + element);
    }

    public static void inputText(WebElement element, String text, String message){
        WaitUtils.waitForClickability(element);
        element.sendKeys(text);
        logger.infoWithoutReport(text + " is text entered in " + message);
    }

    public static void inputText(By element, String text){
        WaitUtils.waitForClickability(element);
        findElement(element).sendKeys(text);
        logger.infoWithoutReport(text + " is text entered in " + element);
    }

    public static void inputText(By element, String text, String message){
        WaitUtils.waitForClickability(element);
        findElement(element).sendKeys(text);
        logger.infoWithoutReport(text + " is text entered in " + message);
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
        logger.infoWithoutReport(text + " is text entered in " + element);
    }

    public static void inputTextSlowly(By element, String text, int delaySec) throws InterruptedException {
        for (char c : text.toCharArray()) {
            inputText(element, String.valueOf(c));
            WaitUtils.implicitWait(delaySec);
        }
        logger.infoWithoutReport(text + " is text entered in " + element);
    }

    public static void inputTextSlowly(WebElement element, String text, int delaySec, String message) throws InterruptedException {
        for (char c : text.toCharArray()) {
            inputText(element, String.valueOf(c));
            WaitUtils.implicitWait(delaySec);
        }
        logger.infoWithoutReport(text + " is text entered in " + message);
    }

    public static void inputTextSlowly(By element, String text, int delaySec, String message) throws InterruptedException {
        for (char c : text.toCharArray()) {
            inputText(element, String.valueOf(c));
            WaitUtils.implicitWait(delaySec);
        }
        logger.infoWithoutReport(text + " is text entered in " + message);
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
        logger.infoWithoutReport("Scrolled into " + x +", " + y);
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
        logger.infoWithoutReport("Scrolled into " + x +", " + y);
    }

    /**
     * Scrolls the webpage to the bottom.
     *
     */
    public static void scrollToBottom() {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
        jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        logger.infoWithoutReport("Scrolled into the bottom of the page");
    }

    /**
     * Scrolls the webpage to the top.
     *
     */
    public static void scrollToTop() {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
        jsExecutor.executeScript("window.scrollTo(0, 0);");
        logger.infoWithoutReport("Scrolled into the top of the page");
    }

    /**
     * Scrolls horizontally by a specified number of pixels.
     *
     * @param x      The horizontal pixels to scroll.
     */
    public static void scrollHorizontally(int x) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
        jsExecutor.executeScript("window.scrollBy(arguments[0], 0);", x);
        logger.infoWithoutReport("Scrolled to the page Horizontally " + x);
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
        logger.infoWithoutReport("Scrolled to the page Vertically" + y);
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
        WaitUtils.waitForVisibility(element);
        JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
        jsExecutor.executeScript(scrollView, findElement(element));
        logger.infoWithoutReport("Scrolled into the " + element +" view ");
    }

    public static void scrollIntoView(By element, String message){
        WaitUtils.waitForVisibility(element);
        JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
        jsExecutor.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", findElement(element));
        logger.infoWithoutReport("Scrolled into the " + message +" view ");
    }

    public static void scrollIntoView(WebElement element){
        WaitUtils.waitForVisibility(element);
        JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
        jsExecutor.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
        logger.infoWithoutReport("Scrolled into the " + element +" view ");
    }

    public static void scrollIntoView(WebElement element, String message){
        WaitUtils.waitForVisibility(element);
        JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
        jsExecutor.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
        logger.infoWithoutReport("Scrolled into the " + message +" view ");
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
        }
    }

    public static boolean isElementPresent(By element){
        try {
            return findElement(element).isDisplayed();
        } catch (NoSuchElementException e) {
            // Element is not found in the DOM
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

    public static String getCurrentUrl(){
        return getDriver().getCurrentUrl();
    }

    public static String getTitleName(){
        return getDriver().getTitle();
    }

}
