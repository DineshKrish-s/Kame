package tests;

import base.BaseTest;
import dataProviders.TestData;
import org.testng.annotations.Test;
import dataProviders.TestDataProvider;
import annotations.TestDataSource;
import pages.LoginPage;
import utils.CommonUtils;
import utils.CustomLogger;

public class LoginTests extends BaseTest {

    CustomLogger logger = new CustomLogger(LoginTests.class);
    
    static final String excelName_1 = "testdata.xlsx";
    static final String sheetName_1 = "LoginData";

    static final String excelName_2 = "testdata1.xlsx";
    static final String sheetName_2 = "LoginData";

    @Test(dataProvider = "singleExcelDataProvider", dataProviderClass = TestDataProvider.class)
    @TestDataSource(fileName = {excelName_1}, sheetName = {sheetName_1})
    public void testLogin(TestData testData) {
        CommonUtils.getUrl(testData.getValue("url"));
        LoginPage loginPage = new LoginPage();
        loginPage.login("dinesh","dinesh");
        logger.infoWithReport("Testing login with:");
        logger.infoWithReport("Username: " + testData.getValue("Username"));
        logger.infoWithReport("Password: " + testData.getValue("Password"));
        logger.infoWithReport("Expected Result: " + testData.getValue("ExpectedResult"));
    }

    @Test(dataProvider = "singleExcelDataProvider", dataProviderClass = TestDataProvider.class, enabled = true)
    @TestDataSource(fileName = excelName_2, sheetName = sheetName_2)
    public void testSearch(TestData testData) {
        CommonUtils.getUrl(testData.getValue("url"));
        LoginPage loginPage = new LoginPage();
        loginPage.login("dinesh","dinesh");
        logger.infoWithReport("Testing login with:");
        logger.infoWithReport("Username: " + testData.getValue("Username"));
        logger.infoWithReport("Password: " + testData.getValue("Password"));
        logger.infoWithReport("Expected Result: " + testData.getValue("ExpectedResult"));
    }
}
