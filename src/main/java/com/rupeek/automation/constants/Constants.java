package com.rupeek.automation.constants;

/**
 * Created by nandita.dubey on 15/07/20.
 */
public class Constants {

    public static final String RootPath = System.getProperty("user.dir").replace("\\", "/");
    public static final String baseurl="http://13.126.80.194:8080/";
    public static String auth =baseurl+"authenticate";
    public static String users =baseurl+"api/v1/users";
    public static final String excelloc=RootPath+"/src/main/resources/RupeekTestData.xlsx";
    public static int SIZEUSERSBYPHONENO =1;


}
