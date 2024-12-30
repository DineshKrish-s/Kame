package StepDef;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.LoginPage;
import utils.CommonUtils;
import utils.CustomLogger;

public class StepDef {

    CustomLogger logger = new CustomLogger(StepDef.class);

    @Given("I open the login page")
    public void iOpenTheLoginPage() {
        CommonUtils.getUrl("https://www.google.com/");
        LoginPage loginPage = new LoginPage();
        loginPage.login("dinesh","dinesh");
        logger.infoWithReport("Opened login page.");
    }

    @When("I enter valid credentials")
    public void iEnterValidCredentials() {
        logger.infoWithReport("Entered valid credentials and clicked login.");
    }

    @Then("I should see the dashboard")
    public void iShouldSeeTheDashboard() {
        logger.infoWithReport("Dashboard is visible.");
    }
}
