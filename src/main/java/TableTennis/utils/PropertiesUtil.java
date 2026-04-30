package TableTennis.utils;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@UtilityClass
public class PropertiesUtil {
    private final static Properties PROPERTIES = new Properties();
    static {
        loadProperties();
    }
    private static void loadProperties(){
        try(InputStream res = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties");) {
            PROPERTIES.load(res);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String get(String key){
        return PROPERTIES.getProperty(key);
    }


}
