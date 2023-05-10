package apiTests.day06;

import apiPOJOTemplates.Education;
import apiPOJOTemplates.Experience;
import apiPOJOTemplates.KraftUser1;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class JsonToJavaDeserilization_POJO_Kraft {

    /* TASK
    base url = https://www.krafttechexlab.com/sw/api/v1
    end point /allusers/getbyid/{id}
    id parameter value is 1
    send the GET request
    then status code should be 200
    get all data into a custom class (POJO) by de-serilization
    */

    @Test
    public void test(){
        Response response = RestAssured
                .given()
                .pathParam("id", "1")
                .when()
                .get("https://www.krafttechexlab.com/sw/api/v1/allusers/getbyid/{id}");

        //verify status code
        Assert.assertEquals(response.statusCode(),200);

        //de-serilization
        //Json to Custom Java Class(POJO)
        KraftUser1[] kraftUser1 = response.as(KraftUser1[].class);

        //get the length of array
        System.out.println(kraftUser1.length);

        //get id
        System.out.println(kraftUser1[0].getId());

        //get name
        System.out.println(kraftUser1[0].getName());

        //get the location
        System.out.println(kraftUser1[0].getLocation());

        //get the second skill
        List<String> skills = kraftUser1[0].getSkills();
        System.out.println(skills.get(1));


        //get the whole education
        List<Education> education = kraftUser1[0].getEducation();
        Education education1 = education.get(0);
        //get the id of first education json
        System.out.println(education1.getId());

        //get the description of first education
        System.out.println(education1.getDescription());

        //get the whole experience
        List<Experience> experience = kraftUser1[0].getExperience();

        //get the third json of experience
        Experience experience1 = experience.get(2);

        //get the date of experience1
        System.out.println(experience1.getDate());
    }
}
