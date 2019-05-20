package util;

import org.apache.logging.log4j.core.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Administrator on 2019/4/18.
 */
public class PropertiesHelper {

    private Properties properties;

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public PropertiesHelper(String propertiesPath) {
        this.setProperties(init(propertiesPath));
    }

    public String getProperty(String key) {
        return getProperty(key, "");
    }

    public String getProperty(String key, String defaultVal) {
        return properties.getProperty(key, defaultVal);
    }

    public static String getPropertyByPath(String propertiesPath, String key) {
        return getPropertyByPath(propertiesPath, key, "");
    }

    public static String getPropertyByPath(String propertiesPath, String key, String defaultVal) {
        return init(propertiesPath).getProperty(key, defaultVal);
    }

    private static Properties init(String propertiesPath) {
        Assert.requireNonEmpty(propertiesPath, "未找到配置文件");

        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            ClassLoader classLoader = PropertiesHelper.class.getClassLoader();
            if (classLoader == null) {
                inputStream = ClassLoader.getSystemResourceAsStream(propertiesPath);
            } else {
                inputStream = classLoader.getResourceAsStream(propertiesPath);
            }
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }
}
