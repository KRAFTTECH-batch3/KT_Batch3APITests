package apiTests.Day05;


import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.testng.Assert.*;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class JsonToJavaCollection {
    @BeforeClass
    public void beforeClass() {
        baseURI = "https://www.krafttechexlab.com/sw/api/v1";
    }

    @Test
    public void allUsersToMap() {
        Response response = given().accept(ContentType.JSON)
                .queryParam("pagesize", 50)
                .and()
                .queryParam("page", 1)
                .when()
                .get("allusers/alluser");

        assertEquals(response.statusCode(),200);
        //we need to de-serialiaze Json response to java collection
        List<Map<String,Object>> allUsersMap= response.as(List.class);
        System.out.println("allUsersMap = " + allUsersMap);

        System.out.println("allUsersMap.get(1).get(\"name\") = " + allUsersMap.get(2).get("name"));
        String name=(String)allUsersMap.get(2).get("name");
        Assert.assertEquals(name,"mike");

        System.out.println("allUsersMap.get(0).get(\"skills\") = " + allUsersMap.get(0).get("skills"));

        List<String> skill=(List<String>)allUsersMap.get(0).get("skills");
        Assert.assertEquals(skill.get(0),"PHP");

        List<Map<String,Object>> experienceListMap=(List<Map<String,Object>>)allUsersMap.get(0).get("experience");
        System.out.println("experienceListMap = " + experienceListMap);
        System.out.println("experienceListMap.get(1).get(\"id\") = " + experienceListMap.get(1).get("id"));
    }

    /** Home Work
     *
     *
     *          * given accept type is JSON
     *          * When user send a get request to  url  https://demoqa.com/Account/v1/User/11
     *           * than status code is 401
     *           * de-serialize => json to java collection
     *          * verify that message is User not authorized!
     *          * verify that code is 1200
     *          */


}
