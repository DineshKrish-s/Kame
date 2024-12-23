package dataProviders;

import java.util.HashMap;
import java.util.Map;

public class TestData {
    private Map<String, Object> data;

    public TestData() {
        this.data = new HashMap<>();
    }

    public void put(String key, Object value) {
        data.put(key, value);
    }

    public Object get(String key) {
        return data.get(key);
    }

    public String getValue(String key) {
        Object value = data.get(key);
        return value != null ? value.toString() : null;
    }

    public Double getAsDouble(String key) {
        Object value = data.get(key);
        return value instanceof Number ? ((Number) value).doubleValue() : null;
    }

    public Boolean getAsBoolean(String key) {
        Object value = data.get(key);
        return value instanceof Boolean ? (Boolean) value : null;
    }

    @Override
    public String toString() {
        return "DynamicTestData{" +
                "data=" + data +
                '}';
    }
}
