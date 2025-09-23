package ufrn.imd.project.config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
  private static Properties props = null;

  public static void load() {
    props = new Properties();

    try {
      InputStream input = ConfigLoader.class
        .getClassLoader()
        .getResourceAsStream("application.properties");

      props.load(input);
    } catch (Exception e) {
      throw new RuntimeException("Could not read application.properties file.");
    }
  }

  public static Protocol getProtocol() {
    if (props == null) {
      load();
    }

    String prop = props.getProperty("protocol");

    switch (prop) {
      case "udp":
        return Protocol.UDP;
      case "tcp":
        return Protocol.TCP;
      case "grpc":
        return Protocol.GRPC;
    }

    return Protocol.UDP;
  }
}
