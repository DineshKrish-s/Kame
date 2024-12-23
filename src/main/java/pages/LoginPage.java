package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private By usernameField = By.id("username");
    private By passwordField = By.id("password");
    private By loginButton = By.id("loginBtn");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String username, String password) {
        type(driver.findElement(usernameField), username);
        type(driver.findElement(passwordField), password);
        click(driver.findElement(loginButton));
    }
}
