package org.utils;


import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Config manager to read config.properties
 *
 */
public class ConfigManager {

    public static final String CURRENT_DIR = System.getProperty("user.dir");
    public static final String TEST_RESOURCE_DIR = Paths.get(CURRENT_DIR,"src","test","resources").toString();
    //private static final String TEST_CONFIG_FILE = Paths.get(System.getProperty("user.dir"),"src","test","resources","config.properties").toString();
    private static final String TEST_CONFIG_FILE = Paths.get(TEST_RESOURCE_DIR,"config.properties").toString();
    private static final Logger LOGGER = LogManager.getLogger(ConfigManager.class);
    private static Map<String, String> configMap = new HashMap<>();

    private ConfigManager() {
    }

    public static synchronized String getConfigProperty(String key) {
        if (configMap.size() == 0) {
            Properties properties = new Properties();
            try {
                properties.load(new FileInputStream(TEST_CONFIG_FILE));
                configMap = new HashMap<>(properties.entrySet()
                        .stream()
                        .collect(Collectors.toMap(e -> e.getKey().toString(),
                                e -> e.getValue().toString())));
                LOGGER.debug("Loaded config properties : " + TEST_CONFIG_FILE);
            } catch (Exception e) {
                LOGGER.error(e);
                throw new RuntimeException(e);
            }
        }

        return configMap.get(key);
    }

}
