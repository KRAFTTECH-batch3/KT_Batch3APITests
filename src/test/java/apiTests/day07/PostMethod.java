package apiTests.day07;

import apiPOJOTemplates.PostKraftUser;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class PostMethod {

    Faker faker = new Faker();

    //POST METHOD
    /*
    Along with the others, there are 3 particular ways to provide data into the request body when we use post method:
        1. Assign the JSON body inside a string variable and put it into the body() method
        2. Put data inside a map and provide it into the body() method
        NOTE:body() method converts the data inside the map to JSON automatically. This only happens with POST,PUT andPATCH method.
        3. Put data into an object which is created based on a java custom class and put it into the body() method.
     */



    //TASK
    /*
    baseUrl = https://www.krafttechexlab.com/sw/api/v1
    endpoint = /allusers/register
    Given accept type and Content type is JSON
    And request json body is:
    {
    "name": "gokhan",
    "email": "gokhan@gokhan.com",
    "password": "Gokhan1234"
    }
    When user sends POST request
    Then status code 200
    And content type should be application/json
    And json payload/response/body should contain:
    a new generated id that is special for user
    name
    email
    ...
     */

    //FIRST WAY
    //STRING
    @Test
    public void test1(){

        String nameFaker = faker.name().fullName();
        String emailFaker = faker.internet().emailAddress();
        String password = faker.internet().password();

        String jsonBody = "{\n" +
                "    \"name\": \"" + nameFaker + "\",\n" +
                "    \"email\": \"" + emailFaker + "\",\n" +
                "    \"password\": \"" + password + "\"\n" +
                "    }";

        Response response = RestAssured
                .given()
                .accept(ContentType.JSON) //hey database, please send me data as json format
                .contentType(ContentType.JSON) // hey database, I'm sending you data as json format
                .body(jsonBody)
                .when()
                .post("https://www.krafttechexlab.com/sw/api/v1/allusers/register");

        //verify status code
        Assert.assertEquals(response.statusCode(),200);

        //verify if there is an id or not
        Assert.assertNotNull(response.path("id"));

        //verify name
        String actualName = response.path("name");
        String expectedName = nameFaker;
        Assert.assertEquals(actualName,expectedName);

        //verify email
        String actualEmail = response.path("email");
        String expectedEmail = emailFaker;
        Assert.assertEquals(actualEmail,expectedEmail);
    }

    //TASK
    /*
    baseUrl = https://www.krafttechexlab.com/sw/api/v1
    endpoint = /allusers/register
    Given accept type and Content type is JSON
    And request json body is:
    {
    "name": "gokhan1",
    "email": "gokhan1@gokhan1.com",
    "password": "Gokhan1234"
    }
    When user sends POST request
    Then status code 200
    And content type should be application/json
    And json payload/response/body should contain:
    a new generated id that is special for user
    name
    email
    ...
     */

    //SECOND WAY
    //MAP
    @Test
    public void test2(){

        String nameFaker = faker.name().fullName();
        String emailFaker = faker.internet().emailAddress();
        String password = faker.internet().password();

        Map<String,String> map = new HashMap<>();
        map.put("name", nameFaker);
        map.put("email", emailFaker);
        map.put("password", password);

        Response response = RestAssured
                .given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(map)
                .post("https://www.krafttechexlab.com/sw/api/v1/allusers/register");

        //verify status code
        Assert.assertEquals(response.statusCode(),200);

        //verify if there is an id or not
        Assert.assertNotNull(response.path("id"));

        //verify name
        String actualName = response.path("name");
        String expectedName = nameFaker;
        Assert.assertEquals(actualName,expectedName);

        //verify email
        String actualEmail = response.path("email");
        String expectedEmail = emailFaker;
        Assert.assertEquals(actualEmail,expectedEmail);
    }

    //TASK
    /*
    baseUrl = https://www.krafttechexlab.com/sw/api/v1
    endpoint = /allusers/register
    Given accept type and Content type is JSON
    And request json body is:
    {
    "name": "gokhan2",
    "email": "gokhan2@gokhan2.com",
    "password": "Gokhan1234"
    }
    When user sends POST request
    Then status code 200
    And content type should be application/json
    And json payload/response/body should contain:
    a new generated id that is special for user
    name
    email
    ...
     */

    //THIRD WAY
    //JAVA CUSTOM CLASS
    @Test
    public void test3(){

        String nameFaker = faker.name().fullName();
        String emailFaker = faker.internet().emailAddress();
        String password = faker.internet().password();

        PostKraftUser postKraftUser = new PostKraftUser();
        postKraftUser.setName(nameFaker);
        postKraftUser.setEmail(emailFaker);
        postKraftUser.setPassword(password);

        System.out.println("postKraftUser = " + postKraftUser);


        Response response = RestAssured
                .given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(postKraftUser)
                .when()
                .post("https://www.krafttechexlab.com/sw/api/v1/allusers/register");

        //verify status code
        Assert.assertEquals(response.statusCode(),200);

        //verify if there is an id or not
        Assert.assertNotNull(response.path("id"));

        //verify name
        String actualName = response.path("name");
        String expectedName = nameFaker;
        Assert.assertEquals(actualName,expectedName);

        //verify email
        String actualEmail = response.path("email");
        String expectedEmail = emailFaker;
        Assert.assertEquals(actualEmail,expectedEmail);
    }
}
