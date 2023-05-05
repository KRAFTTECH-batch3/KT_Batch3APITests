package apiTests.day04;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;

public class UserGetRequestWithJsonPath {

    @BeforeClass
    public void beforeClass() {
        baseURI = "https://www.krafttechexlab.com/sw/api/v1";
    }

/*
    TASK
    When user sends a GET request to /allusers/getbyid/{id}
    Given accept type is json
    And Path param user id is 111

    Then the status Code should be 200
    And Content type json should be "application/json; charset=UTF-8"
    And user's name should be Thomas Eduson
    And user's id should be 111
    And user's email should be thomas@test.com
   */

    @Test
    public void getUserAndVerifyWithJsonPath() {
        Response response = RestAssured
                .given()
                .pathParam("id", 111)
                .accept(ContentType.JSON)
                .when()
                .get("/allusers/getbyid/{id}");

        //verify status code
        Assert.assertEquals(response.statusCode(), 200);
        //verify content type
        Assert.assertEquals(response.contentType(), "application/json; charset=UTF-8");

        //verify id
        int actualId = response.path("id[0]");
        int expectedId = 111;
        Assert.assertEquals(actualId, expectedId);

        //verify name
        String actualName = response.path("name[0]");
        String expectedName = "Thomas Eduson";
        Assert.assertEquals(actualName, expectedName);

        //verify email
        String actualEmail = response.path("email[0]");
        String expectedEmail = "thomas@test.com";
        Assert.assertEquals(actualEmail, expectedEmail);

        //make same verifications with JsonPath
        //create a jsonpath object with response
        JsonPath jsonPath = response.jsonPath();

        //verify id
        int actualIdJsonPath = jsonPath.get("id[0]");
        Assert.assertEquals(actualIdJsonPath, expectedId);

        //verify name
        String actualNameJsonPath = jsonPath.get("name[0]");
        Assert.assertEquals(actualNameJsonPath, expectedName);

        //verify email
        String actualEmailJsonPath = jsonPath.get("email[0]");
        Assert.assertEquals(actualEmailJsonPath, expectedEmail);
    }


    /*
    TASK
    Given accept type is json
    And Path param user id is 112
    When user sends a GET request to /allusers/getbyid/{id}
    And Content type json should be "application/json; charset=UTF-8"
    And user's name should be Steve Jobs
    And user's id should be 112
    And user's email should be steve@test.com
    */
    @Test
    public void getUserAndVerifyWithJsonPath112() {
        Response response = RestAssured
                .given()
                .pathParam("id", 112)
                .accept(ContentType.JSON)
                .when()
                .get("/allusers/getbyid/{id}");

        //verify status code
        Assert.assertEquals(response.statusCode(),200);

        //verify content type
        Assert.assertEquals(response.contentType(),"application/json; charset=UTF-8");

        //create a jsonpath object that holds all response body
        JsonPath jsonPath = response.jsonPath();

        //verify id
        int actualId = jsonPath.get("id[0]");
        int expectedId = 112;
        Assert.assertEquals(actualId,expectedId);

        //verify name
        String actualName = jsonPath.get("name[0]");
        String expectedName = "Steve Jobs";
        Assert.assertEquals(actualName,expectedName);

        //verify email
        String actualEmail = jsonPath.get("email[0]");
        String expectedEmail = "steve@test.com";
        Assert.assertEquals(actualEmail,expectedEmail);
    }


    /*
    TASK
    Given accept type is json
    When user sends a GET request to /allusers/alluser
    Then the status Code should be 200
    And Content type json should be "application/json; charset=UTF-8"
    */
    @Test
    public void getAllUserWithJsonPath(){
        Response response = RestAssured
                .given()
                .queryParam("page", 1)
                .queryParam("pagesize", 5)
                .accept(ContentType.JSON)
                .when()
                .get("/allusers/alluser");
        //verify status code
        Assert.assertEquals(response.statusCode(),200);

        //verify content type
        Assert.assertEquals(response.contentType(),"application/json; charset=UTF-8");

        JsonPath jsonPath = response.jsonPath();

        //verify that first id is equal to 1
        int actualId = jsonPath.get("id[0]");
        int expectedId = 1;
        Assert.assertEquals(actualId,expectedId);

        //verify that the fifth id is equal to 33
        int fifthActualId = jsonPath.get("id[4]");
        int fifthExpectedId = 33;
        Assert.assertEquals(fifthActualId,fifthExpectedId);

        //verify that the fifth name is equal to Sebastian
        String fifthActualName = jsonPath.get("name[4]");
        String fifthExpectedName = "Sebastian";
        Assert.assertEquals(fifthActualName,fifthExpectedName);

        //verify all ids are 1, 5, 24, 29, 33
        List<Integer> actualIdList = jsonPath.getList("id");
        List<Integer> expectedIdList = new ArrayList<>(Arrays.asList(1,5,24,29,33));
        Assert.assertEquals(actualIdList,expectedIdList);
        //iterate all ids and print
        for (Integer integer : actualIdList) {
            System.out.println("integer = " + integer);
        }

        //verify that first skill of first user is PHP
        //first way
        String actualFirstSkillOfFirstUser = jsonPath.get("skills[0][0]");
        String expectedFirstSkillOfFirstUser = "PHP";
        Assert.assertEquals(actualFirstSkillOfFirstUser,expectedFirstSkillOfFirstUser);
        //second way
        List<String> firstUserSkills = jsonPath.getList("skills[0]");
        System.out.println("firstUserSkills.get(0) = " + firstUserSkills.get(0));
        String actualFirstSkillOfFirstUserWithJsonPath = firstUserSkills.get(0);
        Assert.assertEquals(actualFirstSkillOfFirstUserWithJsonPath,expectedFirstSkillOfFirstUser);

        //verify that School or Bootcamp (first user)
        Map<String, Object> map = jsonPath.getMap("education[0][0]");
        System.out.println(map.get("school"));
        String actualEducation = (String) map.get("school");
        String expectedEducation = "School or Bootcamp";
        Assert.assertEquals(actualEducation,expectedEducation);
        //second way
        String alternativeActualEducation = jsonPath.get("education[0].school[0]");
        System.out.println(alternativeActualEducation);
        Assert.assertEquals(alternativeActualEducation,expectedEducation);
        //third way
        List<Map<String,Object>> listOfMap = jsonPath.getList("education[0]");
        System.out.println(listOfMap.get(0).get("school"));
        String thirdWayOfActualEducation = (String) listOfMap.get(0).get("school");
        Assert.assertEquals(thirdWayOfActualEducation,expectedEducation);

        //date
        System.out.println(listOfMap.get(3).get("date"));
    }
}
