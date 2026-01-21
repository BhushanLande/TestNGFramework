package config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        try {
            FileInputStream file = new FileInputStream("src/test/resources/config.properties");
            properties.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static {
        try (InputStream is =
                     ConfigReader.class.getClassLoader()
                             .getResourceAsStream("config.properties")) {

            if (is == null) {
                throw new RuntimeException("config.properties not found");
            }
            properties.load(is);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static boolean isHealingEnabled() {
        return Boolean.parseBoolean(
                properties.getProperty("healing.enabled", "false"));
    }

    public static String getOpenAIKey() {
        return properties.getProperty("openai.key");
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
