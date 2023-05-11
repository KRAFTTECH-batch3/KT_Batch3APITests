package apiTests.day08;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;

public class Authorization {
    @BeforeClass
    public void beforeClass() {
        baseURI = "https://www.krafttechexlab.com/sw/api/v1";
    }

    @Test
    public void test1() {

        String email="eddiem@kraft.com";
        String password="eddiem12";

        Response response = RestAssured.given().accept(ContentType.MULTIPART)
                .and()
                .formParam("email", email)
                .and()
                .formParam("password", password)
                .and()
                .log().all()
                .when()
                .post("allusers/login");

        String token = response.path("token");
        System.out.println("token = " + token);
    }


    public static String getToken() {

        String email="eddiem@kraft.com";
        String password="eddiem12";

        Response response = RestAssured.given().accept(ContentType.MULTIPART)
                .and()
                .formParam("email", email)
                .and()
                .formParam("password", password)
                .and()
                .log().all()
                .when()
                .post("allusers/login");
        String token = response.path("token");

        return token;

    }

    public static Map<String,Object> getToken(String email, String password) {

        Response response = RestAssured.given()
                //.accept(ContentType.ANY)
                .accept(ContentType.MULTIPART)
                .and()
                .formParam("email", email)
                .and()
                .formParam("password", password)
                .and()
                .log().all()
                .when()
                .post("/allusers/login");
        String token = response.path("token");

        Map<String,Object> authorization=new HashMap<>();
        authorization.put("token",token);
        return authorization;
    }


}
