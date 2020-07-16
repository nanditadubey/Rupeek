package com.rupeek.testcases;

import com.rupeek.automation.constants.Constants;
import com.rupeek.automation.datareader.ExcelReader;
import com.rupeek.automation.utils.RestUtil;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * Created by nandita.dubey on 15/07/20.
 */
@Slf4j
public class CustomerSearch_TestCase {
    private static Response response;
    static int count=0;
    private static String phoneNo=null;


    @DataProvider(name = "validationdata")
    public Object[][] getxldata() throws IOException {
        Object[][] obj=null;
        ExcelReader er=new ExcelReader();
        obj=er.getExcelData("ValidationData");

        return obj;
    }

    @Test(dataProvider = "validationdata",description = "Validation of complete response data")
    public static void getUsers(String firstname,String lastname,String career,String phone) {
        try {
            response = RestUtil.getCall(Constants.users, HttpStatus.SC_OK,AuthToken_TestCase.token);

                //Check if response is empty will fail as it should fetch users list
                if(response==null){

                    assertNull("No data present in response");
                }

                String getUsersResponseData=response.asString();

            JSONObject jso = new JSONArray(getUsersResponseData).getJSONObject(count);

                //Validations on response Data
                assertEquals(jso.getString("first_name"), firstname);
                assertEquals(jso.getString("last_name"), lastname);
                assertEquals(jso.getString("career"), career);
                assertEquals(jso.getString("phone"), phone);
                count++;

               log.info("getUsersResponseData is-------------" + getUsersResponseData);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(dataProvider = "validationdata",description = "Validation of response data ")
    public static void getUsersByPhoneNo(String firstname,String lastname,String career,String phone) {
        try {
            response = RestUtil.getCall(Constants.users+"/"+phone, HttpStatus.SC_OK,AuthToken_TestCase.token);

            //Check if response is empty will fail as it should fetch user detail on passing phone no as filter list
            if(response==null){

                assertNull("No data present in response");
            }
            String getUsersByPhoneResponseData=response.asString();

            log.info("getUsersByPhoneResponseData is-------------"+getUsersByPhoneResponseData);

            JSONObject jso =new JSONObject(getUsersByPhoneResponseData);

             phoneNo=jso.getString("phone");

            //Validations on response Data
            assertEquals(jso.getString("first_name"), firstname);
            assertEquals(jso.getString("last_name"),lastname);
            assertEquals(jso.getString("career"),career);
            assertEquals(jso.getString("phone"),phone);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(dataProvider = "validationdata",description = "Validation of response size should be 1 object only ")
    public static void getUsersByPhoneNo1(String firstname,String lastname,String career,String phone) {
        try {
            response = RestUtil.getCall(Constants.users+"/"+phone, HttpStatus.SC_OK,AuthToken_TestCase.token);

            //Check if response is empty will fail as it should fetch user detail on passing phone no as filter list
            if(response==null){

                assertNull("No data present in response");
            }
            String getUsersByPhoneResponseData=response.asString();

            ArrayList<String> js =new ArrayList<String>();
            js.add(getUsersByPhoneResponseData);
            int sizeResponseData=js.size();

            assertEquals(sizeResponseData,Constants.SIZEUSERSBYPHONENO);

           log.info(String.valueOf(sizeResponseData));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(description = "On not passing token should not get access to api to fetch all users list")
    public static void getUsersNeg() {
        try {
            response = RestUtil.getCall(Constants.users, HttpStatus.SC_UNAUTHORIZED,"");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(description = "On not passing token should not get access to api to fetch all users by phone")
    public static void getUserByPhoneNoNeg() {
        try {
            response = RestUtil.getCall(Constants.users+"/"+phoneNo, HttpStatus.SC_UNAUTHORIZED,"");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
