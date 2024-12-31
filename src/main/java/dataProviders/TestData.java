package dataProviders;

import java.util.HashMap;
import java.util.Map;

public class TestData {

    private final Map<String, Object> data;

    public TestData() {
        this.data = new HashMap<>();
    }

    /**
     * Adds a key-value pair to the test data.
     *
     * @param key   The key for the data.
     * @param value The value associated with the key.
     */
    public void put(String key, Object value) {
        data.put(key, value);
    }

    /**
     * Retrieves a value as an Object for the specified key.
     *
     * @param key The key whose value needs to be retrieved.
     * @return The value as an Object, or null if the key is not present.
     */
    public Object get(String key) {
        return data.get(key);
    }

    /**
     * Retrieves a value as a String for the specified key.
     *
     * @param key The key whose value needs to be retrieved.
     * @return The value as a String, or null if the key is not present.
     */
    public String getValue(String key) {
        Object value = data.get(key);
        return value != null ? value.toString() : null;
    }

    /**
     * Retrieves a value as a Double for the specified key.
     *
     * @param key The key whose value needs to be retrieved.
     * @return The value as a Double, or null if the value is not a Number or is not present.
     */
    public Double getAsDouble(String key) {
        Object value = data.get(key);
        return value instanceof Number ? ((Number) value).doubleValue() : null;
    }

    /**
     * Retrieves a value as a Boolean for the specified key.
     *
     * @param key The key whose value needs to be retrieved.
     * @return The value as a Boolean, or null if the value is not a Boolean or is not present.
     */
    public Boolean getAsBoolean(String key) {
        Object value = data.get(key);
        return value instanceof Boolean ? (Boolean) value : null;
    }

    /**
     * Retrieves the entire data map.
     *
     * @return A map of all key-value pairs in the test data.
     */
    public Map<String, Object> getAllData() {
        return data;
    }

    /**
     * Checks if the test data contains the specified key.
     *
     * @param key The key to check for.
     * @return True if the key is present, false otherwise.
     */
    public boolean containsKey(String key) {
        return data.containsKey(key);
    }

    /**
     * Provides a string representation of the test data for debugging.
     *
     * @return A string representation of the test data.
     */
    @Override
    public String toString() {
        return "TestData{" +
                "data=" + data +
                '}';
    }
}
