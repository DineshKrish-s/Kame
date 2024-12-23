package utils;

import constants.APIConstants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;

public class APIUtils {

    /**
     * Validates the status code of an API response using predefined constants.
     *
     * @param endpoint          The endpoint to call.
     * @param expectedStatusCode The expected status code (from APIConstants).
     * @return The Response object for further validation if needed.
     */
    public static Response validateStatusCode(String endpoint, int expectedStatusCode) {
        RestAssured.baseURI = APIConstants.BASE_URL_JSONPLACEHOLDER;
        RequestSpecification request = RestAssured.given();
        Response response = request.get(endpoint);

        response.then().statusCode(expectedStatusCode);
        System.out.println("Status Code Validation Passed: " + response.getStatusCode());

        return response;
    }

    /**
     * Validates the response time of an API call using predefined constants.
     *
     * @param endpoint The endpoint to call.
     */
    public static void validateResponseTime(String endpoint) {
        RestAssured.baseURI = APIConstants.BASE_URL_JSONPLACEHOLDER;
        RequestSpecification request = RestAssured.given();
        Response response = request.get(endpoint);

        response.then().time(Matchers.lessThan(APIConstants.MAX_RESPONSE_TIME_MS));
        System.out.println("Response Time Validation Passed: " + response.time() + " ms");
    }

    /**
     * Validates a specific JSON field in the response body using predefined constants.
     *
     * @param endpoint     The endpoint to call.
     * @param jsonPath     The JSON path to validate (e.g., from APIConstants).
     * @param expectedValue The expected value of the JSON field.
     */
    public static void validateResponseBodyField(String endpoint, String jsonPath, Object expectedValue) {
        RestAssured.baseURI = APIConstants.BASE_URL_JSONPLACEHOLDER;
        RequestSpecification request = RestAssured.given();
        Response response = request.get(endpoint);

        response.then().body(jsonPath, Matchers.equalTo(expectedValue));
        System.out.println("Response Body Field Validation Passed: " + jsonPath + " = " + expectedValue);
    }

    /**
     * Validates the content type of the response using predefined constants.
     *
     * @param endpoint The endpoint to call.
     */
    public static void validateContentType(String endpoint) {
        RestAssured.baseURI = APIConstants.BASE_URL_JSONPLACEHOLDER;
        RequestSpecification request = RestAssured.given();
        Response response = request.get(endpoint);

        response.then().contentType(APIConstants.CONTENT_TYPE_JSON);
        System.out.println("Content Type Validation Passed: " + response.contentType());
    }

    /**
     * Validates if the status code matches 200 OK.
     *
     * @param endpoint The endpoint to call.
     */
    public static void validateStatusOk(String endpoint) {
        validateStatusCode(endpoint, APIConstants.HTTP_STATUS_OK);
    }

    /**
     * Validates if the status code matches 404 Not Found.
     *
     * @param endpoint The endpoint to call.
     */
    public static void validateStatusNotFound(String endpoint) {
        validateStatusCode(endpoint, APIConstants.HTTP_STATUS_NOT_FOUND);
    }

    /**
     * Validates if the status code matches 201 Created.
     *
     * @param endpoint The endpoint to call.
     */
    public static void validateStatusCreated(String endpoint) {
        validateStatusCode(endpoint, APIConstants.HTTP_STATUS_CREATED);
    }

    /**
     * Validates if the status code matches 400 Bad Request.
     *
     * @param endpoint The endpoint to call.
     */
    public static void validateStatusBadRequest(String endpoint) {
        validateStatusCode(endpoint, APIConstants.HTTP_STATUS_BAD_REQUEST);
    }

    /**
     * Validates if the status code matches 401 Unauthorized.
     *
     * @param endpoint The endpoint to call.
     */
    public static void validateStatusUnauthorized(String endpoint) {
        validateStatusCode(endpoint, APIConstants.HTTP_STATUS_UNAUTHORIZED);
    }

}