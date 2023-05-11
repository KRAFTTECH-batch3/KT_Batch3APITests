package apiTests.day08;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class Post_Put_Patch_Delete_Experience {

    @BeforeClass
    public void beforeClass() {
        baseURI = "https://www.krafttechexlab.com/sw/api/v1";
    }
    String email="eddiem@kraft.com";
    String password="eddiem12";
    Response response;
    int id;


    @Test (priority = 0)
    public void addNewExperience() {

        String jsonBody = "{\n" +
                "  \"job\": \"Junior Developer\",\n" +
                "  \"company\": \"Kraftech\",\n" +
                "  \"location\": \"Istanbul\",\n" +
                "  \"fromdate\": \"2015-01-01\",\n" +
                "  \"todate\": \"2016-01-01\",\n" +
                "  \"current\": \"false\",\n" +
                "  \"description\": \"Description\"\n" +
                "}";

        Response response = given().accept(ContentType.JSON)
                .and()
               // .header("token", Authorization.getToken())
                .headers( Authorization.getToken(email,password))
                .and()
                .body(jsonBody)
                .when().log().all()
                .post("experience/add").prettyPeek();

        id = response.path("id");
        System.out.println("id = " + id);


    }

    @Test  (priority = 1)
    public void updateExperienceWithPUT() {

        /**
         * {
         *   "job": "Junior Developer",
         *   "company": "Kraft Techex",
         *   "location": "USA",
         *    "fromdate": "2015-01-01",
         *   "todate": "2016-01-01",
         *   "current": "false",
         *   "description": "Updated"
         * }
         */

        Map<String, Object> experienceBody = new HashMap<>();
        experienceBody.put("job", "Junior Developer");
        experienceBody.put("company", "Google");
        experienceBody.put("location", "NY");
        experienceBody.put("fromdate", "2016-01-01");
        experienceBody.put("todate", "2017-01-01");
        experienceBody.put("current", "false");
        experienceBody.put("description", "Update experience");

        response =given().accept(ContentType.JSON)
                .and()
               // .header("token", Authorization.getToken())
                .headers(Authorization.getToken(email,password))
                .and()
                .queryParam("id", id)
                //.queryParam("id", 888)
                .and()
                .body(experienceBody)
                .when().log().all()
                .put("experience/updateput").prettyPeek();


    }
    @Test (priority = 2)
    public void updateExperienceWithPatch() {

        /**
         * {
         *   "job": "Junior Developer",
         *   "company": "Kraft Techex",
         *   "location": "USA",
         *    "fromdate": "2015-01-01",
         *   "todate": "2016-01-01",
         *   "current": "false",
         *   "description": "Updated"
         * }
         */

        Map<String, Object> experienceBody = new HashMap<>();
        experienceBody.put("company", "IBM");
        experienceBody.put("location", "Texas");


        response =given().accept(ContentType.JSON)
                .and()
                .headers(Authorization.getToken(email,password))
                .and()
                .pathParam("id", id)
               // .pathParam("id", 888)
                .and()
                .body(experienceBody)
                .when().log().all()
                .patch("experience/updatepatch/{id}").prettyPeek();

        Assert.assertEquals(response.statusCode(),200);


    }

    @Test (priority = 3)
    public void deleteExperiences() {


        response =given().accept(ContentType.JSON)
                .and()
                .headers(Authorization.getToken(email,password))
                .and()
                .pathParam("id", id)
                //.pathParam("id", 888)
                .and()
                .when().log().all()
                .delete("experience/delete/{id}").prettyPeek();

        Assert.assertEquals(response.statusCode(),200);


    }





}
