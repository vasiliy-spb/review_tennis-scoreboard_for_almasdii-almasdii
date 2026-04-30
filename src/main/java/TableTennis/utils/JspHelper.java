package TableTennis.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JspHelper {
    private final static String PATH = "/WEB-INF/%s.jsp";
    public static String getPath(String name){
        return String.format(PATH,name);
    }
}
