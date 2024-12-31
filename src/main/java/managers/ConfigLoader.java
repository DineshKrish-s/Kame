package managers;

import utils.CustomLogger;
import utils.FileIO;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * Utility class to load and set configuration properties globally.
 * It reads `config.properties` and overrides specific values based on the environment from `config.json`.
 */
public class ConfigLoader {

    private static final String CONFIG_FILE_PATH = "config/config.properties";
    private static final String CONFIG_JSON_PATH = "src/test/resources/config/config.json";
    private static final String ENV_KEY = "env";

    private final CustomLogger logger = new CustomLogger(ConfigLoader.class);

    /**
     * Loads the configuration by determining the environment and setting properties globally.
     */
    public void loadConfig() {
        Properties properties = loadPropertiesFile();
        String env = determineEnvironment(properties);
        logger.infoWithoutReport("Using environment: " + env);

        loadEnvironmentSpecificSettings(env);
        logger.infoWithoutReport("Configuration properties loaded successfully for environment: " + env);
    }

    /**
     * Loads the `config.properties` file from the classpath.
     *
     * @return Properties object containing configuration key-value pairs.
     */
    private Properties loadPropertiesFile() {
        Properties properties = new Properties();
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream(CONFIG_FILE_PATH)) {
            if (input == null) {
                throw new RuntimeException("config.properties file not found in resources/config.");
            }
            properties.load(input);

            // Set each property as a system property
            for (String key : properties.stringPropertyNames()) {
                System.setProperty(key, properties.getProperty(key));
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration properties from " + CONFIG_FILE_PATH, e);
        }
        return properties;
    }

    /**
     * Determines the environment based on system environment variable or `config.properties`.
     *
     * @param properties The loaded `config.properties`.
     * @return The environment string (e.g., "dev", "prod").
     */
    private String determineEnvironment(Properties properties) {
        String env = System.getenv(ENV_KEY); // Check if 'env' is set as an environment variable
        if (env == null || env.isEmpty()) {
            env = FileIO.getPropertyValue(properties, ENV_KEY);
            if (env == null || env.isEmpty()) {
                throw new RuntimeException("Environment variable 'env' is not set and could not be found in config.properties.");
            }
        }
        return env;
    }

    /**
     * Loads environment-specific settings from the `config.json` file.
     *
     * @param env The environment (e.g., "dev", "prod").
     */
    private void loadEnvironmentSpecificSettings(String env) {
        try {
            Map<String, Object> jsonMap = FileIO.readJsonAsMap(CONFIG_JSON_PATH);
            Map<String, Object> envMap = (Map<String, Object>) jsonMap.get("environments");

            if (envMap == null || !envMap.containsKey(env)) {
                throw new RuntimeException("Environment '" + env + "' not found in config.json.");
            }

            Map<String, Object> envSpecificConfig = (Map<String, Object>) envMap.get(env);

            // Recursively set environment-specific properties
            setPropertiesFromMap(envSpecificConfig, "");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load environment-specific settings from " + CONFIG_JSON_PATH, e);
        }
    }

    /**
     * Recursively sets properties from a map.
     *
     * @param map    The current map of configurations.
     * @param prefix The prefix to append for nested keys.
     */
    private void setPropertiesFromMap(Map<String, Object> map, String prefix) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = prefix.isEmpty() ? entry.getKey() : prefix + "." + entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Map) {
                // Recursively process nested maps
                setPropertiesFromMap((Map<String, Object>) value, key);
            } else {
                // Set the value as a system property
                System.setProperty(key, value.toString());
                logger.infoWithoutReport("Set property: " + key + " = " + value);
            }
        }
    }
}
