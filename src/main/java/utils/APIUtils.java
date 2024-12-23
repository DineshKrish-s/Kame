package utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpResponse;
import java.util.Map;

public class APIUtils {
    public static HttpResponse sendGetRequest(String url, Map<String, String> headers) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        for (Map.Entry<String, String> header : headers.entrySet()) {
            connection.setRequestProperty(header.getKey(), header.getValue());
        }
        return connection;
    }
}
