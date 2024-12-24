package StepDef;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.LoginPage;
import utils.CommonUtils;

public class StepDef {
    @Given("I open the login page")
    public void iOpenTheLoginPage() {
        CommonUtils.getUrl("https://www.google.com/");
        LoginPage loginPage = new LoginPage();
        loginPage.login("dinesh","dinesh");
        System.out.println("Opened login page.");
    }

    @When("I enter valid credentials")
    public void iEnterValidCredentials() {
        System.out.println("Entered valid credentials and clicked login.");
    }

    @Then("I should see the dashboard")
    public void iShouldSeeTheDashboard() {
        System.out.println("Dashboard is visible.");
    }
}
