package TableTennis.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JspHelper {

    // При аннотации @UtilityClass можно явно не писать static в объявлении полей и методов

    private final static String PATH = "/WEB-INF/%s.jsp";
    public static String getPath(String name){
        return String.format(PATH,name);
    }
}
