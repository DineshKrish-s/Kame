Feature: Login Functionality

  @Login
  Scenario: Successful Login
    Given I open the login page
    When I enter valid credentials
    Then I should see the dashboard