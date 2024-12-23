package tests;

import base.BaseTest;
import managers.PageObjectManager;
import org.testng.annotations.Test;
import dataProviders.TestDataProvider;

public class LoginTests extends BaseTest {
    private PageObjectManager pageObjectManager;

    @Test(dataProvider = "loginData", dataProviderClass = TestDataProvider.class)
    public void testValidLogin(String username, String password) {
        pageObjectManager = new PageObjectManager(driver);
        pageObjectManager.getLoginPage().login(username, password);
        // Add assertions to verify login success
    }
}
