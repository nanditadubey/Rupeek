package com.rupeek.automation.utils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * Created by nandita.dubey on 15/07/20.
 */
public class RestUtil {

    public static Response postCall(Object body, String url, int statuscode) {
        Response response = null;
        try {

            response = given().
                    contentType(ContentType.JSON)
                    .body(body)
                    .when()
                    .post(url)
                    .then()
                    .assertThat()
                    .statusCode(statuscode)
                    .extract().response();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }


    public static Response getCall(String url,int statuscode,String token) {
        Response response = null;
        try {

            response = given().
                    header("Authorization","Bearer "+token)
                    .contentType(ContentType.JSON)
                    .when()
                    .get(url)
                    .then()
                    .assertThat()
                    .statusCode(statuscode)
                    .extract().response();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
