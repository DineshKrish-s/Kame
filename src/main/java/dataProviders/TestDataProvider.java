package dataProviders;

import org.testng.annotations.DataProvider;

public class TestDataProvider {
    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return new Object[][]{
                {"user1", "password1"},
                {"user2", "password2"},
        };
    }
}
