package core;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyLoader {
  private Properties properties;

  public PropertyLoader(String filePath) {
    properties = new Properties();
    try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
      properties.load(fileInputStream);
    } catch (IOException e) {
      System.err.println("Error loading properties file: " + e.getMessage());
    }
  }
  public String getProperty(String key) {
    return properties.getProperty(key, null);
  }
}
