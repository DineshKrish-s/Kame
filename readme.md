# Java Selenium Testing Framework

## Overview
This project is a comprehensive testing framework built in Java that supports both **Cucumber** and **TestNG** test execution. The framework is designed to execute tests in parallel on a Selenium Grid, ensuring efficiency and scalability for UI and API testing.

---

## Features

1. **Hybrid Test Execution**:
   - Supports **Cucumber** for BDD-style test execution.
   - Utilizes **TestNG** for traditional test case execution with DataProvider support.

2. **Parallel Execution**:
   - Runs tests in parallel to optimize test execution time.
   - Configured for seamless integration with Selenium Grid.

3. **Utility-Driven Design**:
   - Centralized utility files to manage common functionalities:
     - **CommonUtils**: General-purpose utilities.
     - **WaitUtils**: Wait mechanisms for stable UI interactions.
     - **FileIO**: File input/output operations.
     - **Assertion**: Custom assertion utilities.
     - **Logger**: Centralized logging for better traceability.
     - **DBUtils**: Database connection and query management.
     - **APIUtils**: Simplified API interaction handling.

4. **Dynamic Test Data Management**:
   - Uses DataProvider methods in TestNG for parameterized testing.
   - Supports dynamic execution of multiple test scenarios across domains and input data.

5. **Gherkin File Integration**:
   - Automated generation of Gherkin files from acceptance criteria using ChatGPT's API.

---

## Folder Structure

```
project-root
├── src/main/java
│   ├── Utils
│   │   ├── CommonUtils.java
│   │   ├── WaitUtils.java
│   │   ├── FileIO.java
│   │   ├── Assertion.java
│   │   ├── Logger.java
│   │   ├── DBUtils.java
│   │   ├── APIUtils.java
│   └── ... (other source files)
├── src/test/java
│   ├── CucumberTests
│   │   ├── StepDefinitions
│   │   └── FeatureFiles
│   ├── TestNGTests
│   │   └── ...
│   └── DataProviders
├── resources
│   ├── config.json
│   └── test-data
└── pom.xml
```

---

## Prerequisites

- **Java**: JDK 8 or higher.
- **Maven**: Ensure Maven is installed and added to the PATH.
- **Selenium Grid**: Set up and running.
- **Browser Drivers**: Ensure required browser drivers are available in the PATH.

---

## Configuration

### 1. **`config.json` File**:

- Place credentials, URLs, environment details, and retry counts in the `config.json` file located in the `resources` folder.

Example:
```json
{
  "baseUrl": "https://example.com",
  "environment": "QA",
  "retryCount": 3,
  "credentials": {
    "username": "test_user",
    "password": "secure_password"
  }
}
```

### 2. **Selenium Grid**:

- Configure Selenium Grid for parallel execution by updating `gridConfig` in the test setup files.

---

## Usage

### Running Tests

1. **Cucumber Tests**:
   ```bash
   mvn test -Dcucumber
   ```

2. **TestNG Tests**:
   ```bash
   mvn test -Dtestng
   ```

3. **Parallel Execution**:
   - Configure parallel threads in the `testng.xml` file or `cucumber.properties` file for Cucumber.

### Generating Gherkin Files

- Use ChatGPT's API to generate Gherkin files from acceptance criteria.
- Automate the process via a script or utility within the project.

---

## Utilities

### CommonUtils
- Generic helper methods for repetitive tasks.

### WaitUtils
- Implicit, explicit, and fluent wait implementations.

### FileIO
- Manage file read/write operations.

### Assertion
- Enhanced assertion methods for improved validation.

### Logger
- Centralized logging for test execution.

### DBUtils
- Simplified database interactions.

### APIUtils
- Utility methods for API testing (e.g., GET, POST).

---

## Test Execution Workflow

1. **Data Providers**:
   - Ensure test data is placed in `DataProviders`.

2. **Scenarios with Multiple Inputs**:
   - Iterate scenarios using TestNG DataProviders or Cucumber Examples table.

3. **Vouchers and Domains**:
   - Handle multiple vouchers for each domain by iterating over data sources.

---

## Reporting

- Test results are generated using the following:
  - **TestNG Reports**: Detailed execution logs.
  - **Cucumber HTML Reports**: For BDD scenarios.

---

## Future Enhancements

- Integration with CI/CD pipelines.
- Advanced reporting with Allure.
- Enhanced API testing with REST-Assured integration.
- Dockerized Selenium Grid setup.

---

## Contribution Guidelines

- Ensure proper comments and documentation for all code.
- Follow the project's coding standards.
- Raise a PR for code review before merging.

---

## License

This project is licensed under the MIT License. See the LICENSE file for details.

---

## Contact

For any queries, please reach out to the project maintainers.
