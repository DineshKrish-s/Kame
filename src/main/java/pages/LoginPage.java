package pages;

import base.TestBase;
import org.openqa.selenium.By;
import utils.CommonUtils;
import utils.WaitUtils;

public class LoginPage extends TestBase {

    private By usernameField = By.id("username");
    private By passwordField = By.id("password");
    private By loginButton = By.id("loginBtn");

    CommonUtils commonUtils;
    WaitUtils waitUtils;

    public LoginPage() {
        waitUtils = new WaitUtils();
    }

    public void login(String username, String password) {
        CommonUtils.inputText(usernameField, username);
        CommonUtils.inputText(passwordField, password);
        CommonUtils.clickElement(loginButton);
    }
}
