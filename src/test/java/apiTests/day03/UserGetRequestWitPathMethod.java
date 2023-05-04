package apiTests.day03;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class UserGetRequestWitPathMethod {

    @BeforeClass
    public void beforeClass(){
       baseURI="https://www.krafttechexlab.com/sw/api/v1";
    }

    @Test
    public void testWithPathMethod(){
        /** Verify that all information in the body
         *          "id": 24,
         *         "name": "mike",
         *         "email": "mike@gmail.com",
         *         "password": "$2y$10$KWJ2f3iTUFvkvzTS7/O0AOBmfwYknjscuwdA8n4c25gkzFqi9tswW",
         *         "about": "Excellent QA",
         *         "terms": "2",
         *         "date": "2022-09-12 20:50:38",
         *         "job": "SDET",
         *         "company": "Amazon",
         *         "website": "Krafttechnologie",
         *         "location": "USD",
         *         "skills": [
         *             "Cucumber",
         *             "TestNG"
         *         ],
         */


        Response response = given().accept(ContentType.JSON)
                .pathParams("id", 24)
                .when()
                .get(baseURI + "/allusers/getbyid/{id}");
        assertEquals(response.statusCode(),200);

        //print each value
        System.out.println("response.body().path(\"name\").toString() = " + response.body().path("name").toString());
        System.out.println("response.path(\"name\").toString() = " + response.path("name").toString());
        System.out.println("response.path(\"name\") = " + response.path("name"));
        System.out.println("response.path(\"id\") = " + response.path("id"));
        System.out.println("response.path(\"email\") = " + response.path("email"));
        System.out.println("response.path(\"company\") = " + response.path("company"));
        System.out.println("response.path(\"websitewebsite\") = " + response.path("website"));

        int actualID=response.path("id[0]");

        assertEquals(actualID,24);
        assertEquals(response.path("email[0]"),"mike@gmail.com");
        assertEquals(response.path("name[0]"),"mike");
        assertEquals(response.path("company[0]"),"Amazon");

    }

    /**Class Task
     * Given accept type JSON
     * and Query parameter value pagesize 50
     * and Query parameter value page 1
     * When user send GET request to /allusers/alluser
     * Then response status code is 200
     * And response content type is "application/json; charset=UTF-8"
     * Verify that first id 1
     * verify that first name MercanS
     * verify that last id is 102
     * verify that last name is GHAN
     */

    @Test
   public void allUsersVerifyWithPath(){
        Response response = given().accept(ContentType.JSON)
                .queryParam("pagesize", 50)
                .queryParam("page", 1)
                .when().log().all()
                .get("/allusers/alluser");

        //verify status code
        assertEquals(response.statusCode(),200);
        //verify content type
        assertEquals(response.contentType(),"application/json; charset=UTF-8");
        assertEquals(response.getHeader("Content-Type"),"application/json; charset=UTF-8");

        int id=response.path("id[0]");
        System.out.println("id = " + id);
        assertEquals(id,1);

        String name=response.path("name[0]");
        System.out.println("name = " + name);
        assertEquals(name,"MercanS");

       // int lastID=response.path("id[49]");
        int lastID=response.path("id[-1]");
        System.out.println("lastID = " + lastID);
        assertEquals(lastID,102);

        String lastName=response.path("name[-1]");
        System.out.println("lastName = " + lastName);
        assertEquals(lastName,"GHAN");

        //get third company --> index 2 (mike company)
        String thirdCompany = response.path("company[2]");
        assertEquals(thirdCompany,"Amazon");

        //get third users skills
        List<String> thirdUserSkills= response.path("skills[2]");
        System.out.println("thirdUserSkills = " + thirdUserSkills);

        for(String skill: thirdUserSkills){
            System.out.println("skill = " + skill);
        }

        Object thirdUserSecondSkill = response.path("skills[2][1]");
        System.out.println("thirdUserSecondSkill = " + thirdUserSecondSkill);


    }




}
