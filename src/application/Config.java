package application;


import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Properties properties = new Properties();

    static {
        // Try to load the config.properties file
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
            } else {
                properties.load(input);  // Load properties only if file is found
            }
        } catch (Exception e) {
            e.printStackTrace();  // Handle any errors during loading
        }
    }

    // Method to fetch properties by key
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
