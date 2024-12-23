package constants;

public class APIConstants {

    // Base URL
    public static final String BASE_URL_JSONPLACEHOLDER = "https://jsonplaceholder.typicode.com";

    // HTTP Status Codes
    public static final int HTTP_STATUS_OK = 200;
    public static final int HTTP_STATUS_CREATED = 201;
    public static final int HTTP_STATUS_BAD_REQUEST = 400;
    public static final int HTTP_STATUS_UNAUTHORIZED = 401;
    public static final int HTTP_STATUS_FORBIDDEN = 403;
    public static final int HTTP_STATUS_NOT_FOUND = 404;
    public static final int HTTP_STATUS_INTERNAL_SERVER_ERROR = 500;

    // Common Content Types
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String CONTENT_TYPE_XML = "application/xml";

    // Common JSON Field Names
    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_TITLE = "title";

    // Other Constants
    public static final long MAX_RESPONSE_TIME_MS = 2000; // 2 seconds
}
