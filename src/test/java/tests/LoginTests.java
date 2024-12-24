package tests;

import base.BaseTest;
import dataProviders.TestData;
import org.testng.annotations.Test;
import dataProviders.TestDataProvider;
import annotations.TestDataSource;
import pages.LoginPage;
import utils.CommonUtils;

public class LoginTests extends BaseTest {

    static final String excelName = "testdata.xlsx";
    static final String sheetName = "LoginData";

    @Test(dataProvider = "excelDataProvider", dataProviderClass = TestDataProvider.class)
    @TestDataSource(fileName = excelName, sheetName = sheetName)
    public void testLogin(TestData testData) {
        CommonUtils.getUrl(testData.getValue("url"));
        LoginPage loginPage = new LoginPage();
        loginPage.login("dinesh","dinesh");
        System.out.println("Testing login with:");
        System.out.println("Username: " + testData.getValue("Username"));
        System.out.println("Password: " + testData.getValue("Password"));
        System.out.println("Expected Result: " + testData.getValue("ExpectedResult"));
    }

}
