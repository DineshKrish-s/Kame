package utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtils {
    public static Connection getConnection(String dbUrl, String username, String password) throws SQLException {
        return DriverManager.getConnection(dbUrl, username, password);
    }

    public static void closeConnection(Connection connection) throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public static List<Map<String, Object>> fetchResults(String query, Connection connection) throws SQLException {
        List<Map<String, Object>> results = new ArrayList<>();
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(metaData.getColumnName(i), rs.getObject(i));
                }
                results.add(row);
            }
        }
        return results;
    }
}
