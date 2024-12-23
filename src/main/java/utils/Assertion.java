package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class Assertion {

    public static void assertEquals(String actual, String expected) {
        try{
            Assert.assertEquals(actual, expected);
        } catch (AssertionError e) {
            throw e;
        }
    }

    public static void assertEquals(String actual, String expected, String message) {
        try{
            Assert.assertEquals(actual, expected, message);
        } catch (AssertionError e) {
            throw e;
        }
    }

    public static void assertEquals(Object actual, Object expected) {
        try{
            Assert.assertEquals(actual, expected);
        } catch (AssertionError e) {
            throw e;
        }
    }

    public static void assertEquals(Object actual, Object expected, String message) {
        try{
            Assert.assertEquals(actual, expected, message);
        } catch (AssertionError e) {
            throw e;
        }
    }

    public static void assertTrue(boolean condition){
        try {
            Assert.assertTrue(condition);
        } catch (AssertionError e) {
            throw e;
        }
    }

    public static void assertTrue(boolean condition, String message){
        try {
            Assert.assertTrue(condition, message);
        } catch (AssertionError e) {
            throw e;
        }
    }

    public static void assertFalse(boolean condition){
        try {
            Assert.assertFalse(condition);
        } catch (AssertionError e) {
            throw e;
        }
    }

    public static void assertFalse(boolean condition, String message){
        try {
            Assert.assertFalse(condition, message);
        } catch (AssertionError e) {
            throw e;
        }
    }

    public static void verifyElementPresent(WebElement element){
        try {
            Assert.assertTrue(CommonUtils.isElementPresent(element));
        } catch (AssertionError e) {
            throw e;
        }
    }

    public static void verifyElementPresent(WebElement element, String message){
        try {
            Assert.assertTrue(CommonUtils.isElementPresent(element));
        } catch (AssertionError e) {
            throw e;
        }
    }

    public static void verifyElementPresent(By element){
        try {
            Assert.assertTrue(CommonUtils.isElementPresent(element));
        } catch (AssertionError e) {
            throw e;
        }
    }

    public static void verifyElementPresent(By element, String message){
        try {
            Assert.assertTrue(CommonUtils.isElementPresent(element));
        } catch (AssertionError e) {
            throw e;
        }
    }

    public static void verifyElementDisplayed(WebElement element){
        try {
            Assert.assertTrue(CommonUtils.isElementVisible(element));
        } catch (AssertionError e) {
            throw e;
        }
    }

    public static void verifyElementDisplayed(WebElement element, String message){
        try {
            Assert.assertTrue(CommonUtils.isElementVisible(element));
        } catch (AssertionError e) {
            throw e;
        }
    }

    public static void verifyElementDisplayed(By element){
        try {
            Assert.assertTrue(CommonUtils.isElementVisible(element));
        } catch (AssertionError e) {
            throw e;
        }
    }

    public static void verifyElementDisplayed(By element, String message){
        try {
            Assert.assertTrue(CommonUtils.isElementVisible(element));
        } catch (AssertionError e) {
            throw e;
        }
    }

    public static void verifyElementEnabled(WebElement element){
        try {
            Assert.assertTrue(CommonUtils.isElementEnabled(element));
        } catch (AssertionError e) {
            throw e;
        }
    }

    public static void verifyElementEnabled(WebElement element, String message){
        try {
            Assert.assertTrue(CommonUtils.isElementEnabled(element));
        } catch (AssertionError e) {
            throw e;
        }
    }

    public static void verifyElementEnabled(By element){
        try {
            Assert.assertTrue(CommonUtils.isElementEnabled(element));
        } catch (AssertionError e) {
            throw e;
        }
    }

    public static void verifyElementEnabled(By element, String message){
        try {
            Assert.assertTrue(CommonUtils.isElementEnabled(element));
        } catch (AssertionError e) {
            throw e;
        }
    }

    public static void verifyElementSelected(WebElement element){
        try {
            Assert.assertTrue(CommonUtils.isElementSelected(element));
        } catch (AssertionError e) {
            throw e;
        }
    }

    public static void verifyElementSelected(WebElement element, String message){
        try {
            Assert.assertTrue(CommonUtils.isElementSelected(element));
        } catch (AssertionError e) {
            throw e;
        }
    }

    public static void verifyElementSelected(By element){
        try {
            Assert.assertTrue(CommonUtils.isElementSelected(element));
        } catch (AssertionError e) {
            throw e;
        }
    }

    public static void verifyElementSelected(By element, String message){
        try {
            Assert.assertTrue(CommonUtils.isElementSelected(element));
        } catch (AssertionError e) {
            throw e;
        }
    }

}
