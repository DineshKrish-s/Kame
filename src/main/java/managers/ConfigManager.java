package managers;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class ConfigManager {
    private static JSONObject config;

    public static JSONObject getConfig() {
        if (config == null) {
            try {
                JSONParser parser = new JSONParser();
                FileReader reader = new FileReader("src/test/resources/config/config.json");
                config = (JSONObject) parser.parse(reader);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to load configuration file.");
            }
        }
        return config;
    }
}
