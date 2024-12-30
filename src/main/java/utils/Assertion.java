package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class Assertion {

    static CustomLogger logger = new CustomLogger(Assertion.class);
    
    static final String isSelected = " is Selected";
    static final String isNotSelected = " is not Selected";
    static final String isEnabled = " is Enabled";
    static final String isNotEnabled = " is not Enabled";
    static final String isPresent = " is Present";
    static final String isNotPresent = " is not Present";
    static final String isDisplayed = " is Displayed";
    static final String isNotDisplayed = " is Not Displayed";
    static final String actualText = "Actual: ";
    static final String expectedText = "Expected: ";

    Assertion(){}
    
    public static void assertEquals(String actual, String expected) {
        try{
            Assert.assertEquals(actual, expected);
            logger.pass(actualText + actual + expectedText + expected );
        } catch (AssertionError e) {
            logger.logErrorWithReport(actualText + actual + expectedText + expected );
            throw e;
        }
    }

    public static void assertEquals(String actual, String expected, String message) {
        try{
            Assert.assertEquals(actual, expected, message);
            logger.pass(actualText + actual + expectedText + expected );
        } catch (AssertionError e) {
            logger.logErrorWithReport(actualText + actual + expectedText + expected );
            throw e;
        }
    }

    public static void assertEquals(Object actual, Object expected) {
        try{
            Assert.assertEquals(actual, expected);
            logger.pass(actualText + actual + expectedText + expected );
        } catch (AssertionError e) {
            logger.logErrorWithReport(actualText + actual + expectedText + expected );
            throw e;
        }
    }

    public static void assertEquals(Object actual, Object expected, String message) {
        try{
            Assert.assertEquals(actual, expected, message);
            logger.pass(actualText + actual + expectedText + expected );
        } catch (AssertionError e) {
            logger.logErrorWithReport(actualText + actual + expectedText + expected );
            throw e;
        }
    }

    public static void assertTrue(boolean condition){
        try {
            Assert.assertTrue(condition);
            logger.pass(actualText + condition + expectedText + !condition );
        } catch (AssertionError e) {
            logger.logErrorWithReport(actualText + condition + expectedText + !condition );
            throw e;
        }
    }

    public static void assertTrue(boolean condition, String message){
        try {
            Assert.assertTrue(condition, message);
            logger.pass(actualText + condition + expectedText + !condition );
        } catch (AssertionError e) {
            logger.logErrorWithReport(actualText + condition + expectedText + !condition );
            throw e;
        }
    }

    public static void assertFalse(boolean condition){
        try {
            Assert.assertFalse(condition);
            logger.pass(actualText + condition + expectedText + !condition );
        } catch (AssertionError e) {
            logger.logErrorWithReport(actualText + condition + expectedText + !condition );
            throw e;
        }
    }

    public static void assertFalse(boolean condition, String message){
        try {
            Assert.assertFalse(condition, message);
            logger.pass(actualText + condition + expectedText + !condition );
        } catch (AssertionError e) {
            logger.logErrorWithReport(actualText + condition + expectedText + !condition );
            throw e;
        }
    }

    public static void verifyElementPresent(WebElement element){
        try {
            Assert.assertTrue(CommonUtils.isElementPresent(element));
            logger.pass(element + isPresent);
        } catch (AssertionError e) {
            logger.logErrorWithReport(element + isNotPresent);
            throw e;
        }
    }

    public static void verifyElementPresent(WebElement element, String message){
        try {
            Assert.assertTrue(CommonUtils.isElementPresent(element), message);
            logger.pass(element + isPresent);
        } catch (AssertionError e) {
            logger.logErrorWithReport(element + isNotPresent);
            throw e;
        }
    }

    public static void verifyElementPresent(By element){
        try {
            Assert.assertTrue(CommonUtils.isElementPresent(element));
            logger.pass(element + isPresent);
        } catch (AssertionError e) {
            logger.logErrorWithReport(element + isNotPresent);
            throw e;
        }
    }

    public static void verifyElementPresent(By element, String message){
        try {
            Assert.assertTrue(CommonUtils.isElementPresent(element), message);
            logger.pass(element + isPresent);
        } catch (AssertionError e) {
            logger.logErrorWithReport(element + isNotPresent);
            throw e;
        }
    }

    public static void verifyElementDisplayed(WebElement element){
        try {
            Assert.assertTrue(CommonUtils.isElementVisible(element));
            logger.pass(element + isDisplayed);
        } catch (AssertionError e) {
            logger.logErrorWithReport(element + isNotDisplayed);
            throw e;
        }
    }

    public static void verifyElementDisplayed(WebElement element, String message){
        try {
            Assert.assertTrue(CommonUtils.isElementVisible(element), message);
            logger.pass(element + isDisplayed);
        } catch (AssertionError e) {
            logger.logErrorWithReport(element + isNotDisplayed);
            throw e;
        }
    }

    public static void verifyElementDisplayed(By element){
        try {
            Assert.assertTrue(CommonUtils.isElementVisible(element));
            logger.pass(element + isDisplayed);
        } catch (AssertionError e) {
            logger.logErrorWithReport(element + isNotDisplayed);
            throw e;
        }
    }

    public static void verifyElementDisplayed(By element, String message){
        try {
            Assert.assertTrue(CommonUtils.isElementVisible(element), message);
            logger.pass(element + isDisplayed);
        } catch (AssertionError e) {
            logger.logErrorWithReport(element + isNotDisplayed);
            throw e;
        }
    }

    public static void verifyElementEnabled(WebElement element){
        try {
            Assert.assertTrue(CommonUtils.isElementEnabled(element));
            logger.pass(element + isEnabled);
        } catch (AssertionError e) {
            logger.logErrorWithReport(element + isNotEnabled);
            throw e;
        }
    }

    public static void verifyElementEnabled(WebElement element, String message){
        try {
            Assert.assertTrue(CommonUtils.isElementEnabled(element), message);
            logger.pass(element + isEnabled);
        } catch (AssertionError e) {
            logger.logErrorWithReport(element + isNotEnabled);
            throw e;
        }
    }

    public static void verifyElementEnabled(By element){
        try {
            Assert.assertTrue(CommonUtils.isElementEnabled(element));
            logger.pass(element + isEnabled);
        } catch (AssertionError e) {
            logger.logErrorWithReport(element + isNotEnabled);
            throw e;
        }
    }

    public static void verifyElementEnabled(By element, String message){
        try {
            Assert.assertTrue(CommonUtils.isElementEnabled(element), message);
            logger.pass(element + isEnabled);
        } catch (AssertionError e) {
            logger.logErrorWithReport(element + isNotEnabled);
            throw e;
        }
    }

    public static void verifyElementSelected(WebElement element){
        try {
            Assert.assertTrue(CommonUtils.isElementSelected(element));
            logger.pass(element + isSelected);
        } catch (AssertionError e) {
            logger.logErrorWithReport(element + isNotSelected);
            throw e;
        }
    }

    public static void verifyElementSelected(WebElement element, String message){
        try {
            Assert.assertTrue(CommonUtils.isElementSelected(element), message);
            logger.pass(element + isSelected);
        } catch (AssertionError e) {
            logger.logErrorWithReport(element + isNotSelected);
            throw e;
        }
    }

    public static void verifyElementSelected(By element){
        try {
            Assert.assertTrue(CommonUtils.isElementSelected(element));
            logger.pass(element + isSelected);
        } catch (AssertionError e) {
            logger.logErrorWithReport(element + isNotSelected);
            throw e;
        }
    }

    public static void verifyElementSelected(By element, String message){
        try {
            Assert.assertTrue(CommonUtils.isElementSelected(element), message);
            logger.pass(element + isSelected);
        } catch (AssertionError e) {
            logger.logErrorWithReport(element + isNotSelected);
            throw e;
        }
    }

    public static void softAssertEquals(String actual, String expected) {
        try{
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(actual, expected);
            logger.pass(actualText + actual + expectedText + expected );
        } catch (AssertionError e) {
            logger.logErrorWithReport(actualText + actual + expectedText + expected );
        }
    }

    public static void softAssertEquals(String actual, String expected, String message) {
        try{
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(actual, expected, message);
            logger.pass(actualText + actual + expectedText + expected );
        } catch (AssertionError e) {
            logger.logErrorWithReport(actualText + actual + expectedText + expected );
        }
    }

    public static void softAssertEquals(Object actual, Object expected) {
        try{
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(actual, expected);
            logger.pass(actualText + actual + expectedText + expected );
        } catch (AssertionError e) {
            logger.logErrorWithReport(actualText + actual + expectedText + expected );
        }
    }

    public static void softAssertEquals(Object actual, Object expected, String message) {
        try{
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(actual, expected, message);
            logger.pass(actualText + actual + expectedText + expected );
        } catch (AssertionError e) {
            logger.logErrorWithReport(actualText + actual + expectedText + expected );
        }
    }

    public static void softAssertTrue(boolean condition){
        try {
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(condition);
            logger.pass(actualText + condition + expectedText + !condition );
        } catch (AssertionError e) {
            logger.logErrorWithReport(actualText + condition + expectedText + !condition );
        }
    }

    public static void softAssertTrue(boolean condition, String message){
        try {
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(condition, message);
            logger.pass(actualText + condition + expectedText + !condition );
        } catch (AssertionError e) {
            logger.logErrorWithReport(actualText + condition + expectedText + !condition );
        }
    }

    public static void softAssertFalse(boolean condition){
        try {
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertFalse(condition);
            logger.pass(actualText + condition + expectedText + !condition );
        } catch (AssertionError e) {
            logger.logErrorWithReport(actualText + condition + expectedText + !condition );
        }
    }

    public static void softAssertFalse(boolean condition, String message){
        try {
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertFalse(condition, message);
            logger.pass(actualText + condition + expectedText + !condition );
        } catch (AssertionError e) {
            logger.logErrorWithReport(actualText + condition + expectedText + !condition );
        }
    }

    public static void softVerifyElementPresent(WebElement element){
        try {
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(CommonUtils.isElementPresent(element));
            logger.pass(element + isPresent);
        } catch (AssertionError e) {
            logger.logErrorWithReport(element + isNotPresent);
        }
    }

    public static void softVerifyElementPresent(WebElement element, String message){
        try {
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(CommonUtils.isElementPresent(element),message);
            logger.pass(element + isPresent);
        } catch (AssertionError e) {
            logger.logErrorWithReport(element + isNotPresent);
        }
    }

    public static void softVerifyElementPresent(By element){
        try {
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(CommonUtils.isElementPresent(element));
            logger.pass(element + isPresent);
        } catch (AssertionError e) {
            logger.logErrorWithReport(element + isNotPresent);
        }
    }

    public static void softVerifyElementPresent(By element, String message){
        try {
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(CommonUtils.isElementPresent(element), message);
            logger.pass(element + isPresent);
        } catch (AssertionError e) {
            logger.logErrorWithReport(element + isNotPresent);
        }
    }

    public static void softVerifyElementDisplayed(WebElement element){
        try {
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(CommonUtils.isElementVisible(element));
            logger.pass(element + isDisplayed);
        } catch (AssertionError e) {
            logger.logErrorWithReport(element + isNotDisplayed);
        }
    }

    public static void softVerifyElementDisplayed(WebElement element, String message){
        try {
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(CommonUtils.isElementVisible(element), message);
            logger.pass(element + isDisplayed);
        } catch (AssertionError e) {
            logger.logErrorWithReport(element + isNotDisplayed);
        }
    }

    public static void softVerifyElementDisplayed(By element){
        try {
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(CommonUtils.isElementVisible(element));
            logger.pass(element + isDisplayed);
        } catch (AssertionError e) {
            logger.logErrorWithReport(element + isNotDisplayed);
        }
    }

    public static void softVerifyElementDisplayed(By element, String message){
        try {
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(CommonUtils.isElementVisible(element), message);
            logger.pass(element + isDisplayed);
        } catch (AssertionError e) {
            logger.logErrorWithReport(element + isNotDisplayed);
        }
    }

    public static void softVerifyElementEnabled(WebElement element){
        try {
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(CommonUtils.isElementEnabled(element));
            logger.pass(element + isEnabled);
        } catch (AssertionError e) {
            logger.logErrorWithReport(element + isNotEnabled);
        }
    }

    public static void softVerifyElementEnabled(WebElement element, String message){
        try {
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(CommonUtils.isElementEnabled(element), message);
            logger.pass(element + isEnabled);
        } catch (AssertionError e) {
            logger.logErrorWithReport(element + isNotEnabled);
        }
    }

    public static void softVerifyElementEnabled(By element){
        try {
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(CommonUtils.isElementEnabled(element));
            logger.pass(element + isEnabled);
        } catch (AssertionError e) {
            logger.logErrorWithReport(element + isNotEnabled);
        }
    }

    public static void softVerifyElementEnabled(By element, String message){
        try {
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(CommonUtils.isElementEnabled(element), message);
            logger.pass(element + isEnabled);
        } catch (AssertionError e) {
            logger.logErrorWithReport(element + isNotEnabled);
        }
    }

    public static void softVerifyElementSelected(WebElement element){
        try {
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(CommonUtils.isElementSelected(element));
            logger.pass(element + isSelected);
        } catch (AssertionError e) {
            logger.logErrorWithReport(element + isNotSelected);
        }
    }

    public static void softVerifyElementSelected(WebElement element, String message){
        try {
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(CommonUtils.isElementSelected(element), message);
            logger.pass(element + isSelected);
        } catch (AssertionError e) {
            logger.logErrorWithReport(element + isNotSelected);
        }
    }

    public static void softVerifyElementSelected(By element){
        try {
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(CommonUtils.isElementSelected(element));
            logger.pass(element + isSelected);
        } catch (AssertionError e) {
            logger.logErrorWithReport(element + isNotSelected);
        }
    }

    public static void softVerifyElementSelected(By element, String message){
        try {
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(CommonUtils.isElementSelected(element), message);
            logger.pass(element + isSelected);
        } catch (AssertionError e) {
            logger.logErrorWithReport(element + isNotSelected);
        }
    }
    
}
