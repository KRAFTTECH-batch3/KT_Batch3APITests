package apiTests.day06;

import apiPOJOTemplates.User;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class JsonToJavaDeserilization_POJO_User {

    //TASK
    //base url = https://gorest.co.in/
    //end point = /public/v2/users
    //path parameter = {id} --> 1513738
    //send a get request with the above credentials
    //parse to Json object to pojo (custom java class)
    //verify that the body below
    /*
    {
    "id": 1513738,
    "name": "Amish Khan",
    "email": "amish_khan@greenholt.test",
    "gender": "male",
    "status": "inactive"
    }
     */

    @Test
    public void test(){
        Response response = RestAssured
                .given()
                .when()
                .get("https://gorest.co.in/public/v2/users/1513738");

        //assert status code
        Assert.assertEquals(response.statusCode(),200);

        //Map<String,Object> map = response.as(Map.class);
        //de-serilization
        //Json to known Java Collection (map, list)
        User user = response.as(User.class);

        //get id
        System.out.println(user.getId());
        int actualId = user.getId();
        int expectedId = 1513738;
        Assert.assertEquals(actualId,expectedId);

        //get name
        System.out.println(user.getName());
        String actualName = user.getName();
        String expectedName = "Amish Khan";
        Assert.assertEquals(actualName,expectedName);

        //get email
        System.out.println(user.getEmail());
        String actualEmail = user.getEmail();
        String expectedEmail = "amish_khan@greenholt.test";
        Assert.assertEquals(actualEmail,expectedEmail);

        //get gender
        System.out.println(user.getGender());
        String actualGender = user.getGender();
        String expectedGender = "male";
        Assert.assertEquals(actualGender,expectedGender);

        //get status
        System.out.println(user.getStatus());
        String actualStatus = user.getStatus();
        String expectedStatus = "inactive";
        Assert.assertEquals(actualStatus,expectedStatus);
    }
}
