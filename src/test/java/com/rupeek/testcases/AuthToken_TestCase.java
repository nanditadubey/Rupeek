package com.rupeek.testcases;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.rupeek.automation.constants.PathConstants;
import com.rupeek.automation.datareader.ExcelReader;
import com.rupeek.automation.pojo.AuthToken;
import com.rupeek.automation.utils.RestUtil;
import java.io.IOException;

/**
 * Created by nandita.dubey on 15/07/20.
 */
@Slf4j
public class AuthToken_TestCase {

    private static AuthToken req;
    private static Response response=null;
    public static String token;

    @DataProvider(name = "requestdata")
    public Object[][] getxldata() throws IOException {
        Object[][] obj=null;
        ExcelReader er=new ExcelReader();
        obj=er.getExcelData("AuthData");
        return obj;

    }

    @Test(dataProvider="requestdata")
    public static void authtest(String a, String b) {
        try {
            req= new AuthToken();
            req.setUsername(a);
            req.setPassword(b);
            String auh=req.getUsername();
            response = RestUtil.postCall(req, PathConstants.auth, HttpStatus.SC_OK);
            String strr=response.asString();
            JsonPath js = new JsonPath(strr);
            token = js.getString("token");
            System.out.print("token is-------------"+token);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
