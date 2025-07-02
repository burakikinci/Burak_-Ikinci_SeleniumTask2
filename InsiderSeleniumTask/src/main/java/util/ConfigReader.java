package util;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    private ConfigReader() {}

    public static Properties getProperties() {
        if (properties == null) {
            try (InputStream in = ConfigReader.class
                    .getClassLoader()
                    .getResourceAsStream("config.properties")) {
                properties = new Properties();
                properties.load(in);
            } catch (Exception e) {
                throw new IllegalStateException("Config file cannot be loaded!", e);
            }
        }
        return properties;
    }

    public static String get(String key) {
        return getProperties().getProperty(key);
    }
}
