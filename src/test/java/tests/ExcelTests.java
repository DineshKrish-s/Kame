package tests;

import base.BaseTest;
import dataProviders.TestData;
import org.testng.annotations.Test;
import dataProviders.TestDataProvider;
import annotations.TestDataSource;
import utils.CustomLogger;

public class ExcelTests extends BaseTest {

    CustomLogger logger = new CustomLogger(ExcelTests.class);
    
    static final String excelName_1 = "testdata.xlsx";
    static final String sheetName_1 = "Sheet1";
    static final String sheetName_2 = "Sheet2";
    static final String excelName_2 = "testdata1.xlsx";
    static final String sheetName_3 = "Sheet1";

    @Test(dataProvider = "singleExcelDataProvider", dataProviderClass = TestDataProvider.class)
    @TestDataSource(fileName = {excelName_1}, sheetName = {sheetName_1})
    public void testSingleSheet_1(TestData testData) {
        logger.infoWithReport("Testing login with:");
        logger.infoWithReport("Username: " + testData.getValue("Username"));
        logger.infoWithReport("Password: " + testData.getValue("Password"));
        logger.infoWithReport("Expected Result: " + testData.getValue("ExpectedResult"));
    }

    @Test(dataProvider = "singleExcelDataProvider", dataProviderClass = TestDataProvider.class)
    @TestDataSource(fileName = {excelName_1}, sheetName = {sheetName_1}, rowRange = "0-0")
    public void testSingleSheet_2(TestData testData) {
        logger.infoWithReport("Testing login with:");
        logger.infoWithReport("Username: " + testData.getValue("Username"));
        logger.infoWithReport("Password: " + testData.getValue("Password"));
        logger.infoWithReport("Expected Result: " + testData.getValue("ExpectedResult"));
    }

    @Test(dataProvider = "singleExcelDataProvider", dataProviderClass = TestDataProvider.class)
    @TestDataSource(fileName = excelName_1, sheetName = sheetName_1, rows = {0})
    public void testSingleSheet_3(TestData testData) {
        logger.infoWithReport("Testing login with:");
        logger.infoWithReport("Username: " + testData.getValue("Username"));
        logger.infoWithReport("Password: " + testData.getValue("Password"));
        logger.infoWithReport("Expected Result: " + testData.getValue("ExpectedResult"));
    }

    @Test(dataProvider = "multiSheetDynamicDataProvider", dataProviderClass = TestDataProvider.class)
    @TestDataSource(fileName = {excelName_1}, sheetName = {sheetName_1, sheetName_2}, rowRange = "0-0")
    public void testSingleExcelMultipleSheets(TestData testData) {
        logger.infoWithReport("Testing login with:");
        logger.infoWithReport("Username: " + testData.getValue("Username"));
        logger.infoWithReport("Password: " + testData.getValue("Password"));
        logger.infoWithReport("Expected Result: " + testData.getValue("ExpectedResult"));
        logger.infoWithReport("User Id " + testData.getValue("userId"));
        logger.infoWithReport("First Name " + testData.getValue("firstName"));

    }

    @Test(dataProvider = "multiExcelDataProvider", dataProviderClass = TestDataProvider.class)
    @TestDataSource(fileName = {excelName_1, excelName_2}, sheetName = {sheetName_1, sheetName_3}, rowRange = "0-0")
    public void testMultipleExcel(TestData testData) {
        logger.infoWithReport("Testing login with:");
        logger.infoWithReport("Username: " + testData.getValue("Username"));
        logger.infoWithReport("Password: " + testData.getValue("Password"));
        logger.infoWithReport("Expected Result: " + testData.getValue("ExpectedResult"));
        logger.infoWithReport("Email Id: " + testData.getValue("emailId"));

    }

}
