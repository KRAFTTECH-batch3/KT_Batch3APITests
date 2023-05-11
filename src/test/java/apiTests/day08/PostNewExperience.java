package apiTests.day08;

import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class PostNewExperience {

    @BeforeClass
    public void beforeClass() {
        baseURI = "https://www.krafttechexlab.com/sw/api/v1";
    }

    @Test
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

        given().accept(ContentType.JSON)
                .and()
                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJhdXQiOiJhRm0iLCJsaW5rIjoia3JhZnR0ZWNoZXhsYWIuY29tIiwidXNlcmlkIjo5OCwic3RhcnQiOjE2ODM4MjMwNDEsImVuZHMiOjE2ODQ0Mjc4NDF9.A7Ugj8Oer_dDqqsXy0fHSHI1zzTxyXrQ96ATfsdC8TKQF_ID_blN0D0miepARjEGjSZVO5jONbnp2mYC6tRoLA")
                .and()
                .body(jsonBody)
                .when().log().all()
                .post("experience/add").prettyPeek()
                .then()
                .assertThat()
                .statusCode(200);
    }
}
