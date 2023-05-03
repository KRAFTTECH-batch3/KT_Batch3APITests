package apiTests.day02;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SimpleGetRequest {

    String petStoreBaseUrl = "https://petstore.swagger.io/v2";

    @Test
    public void simpleGetRequest_Basic(){
        Response response = RestAssured
                .given()
                .when()
                .get(petStoreBaseUrl + "/store/inventory");
        //print status code
        System.out.println("response.statusCode() = " + response.statusCode());

        //print whole body
        response.prettyPrint();
    }

    @Test
    public void simpleGetRequest_Header(){
        Response response = RestAssured
                .given()
                .accept(ContentType.JSON)
                .when()
                .get(petStoreBaseUrl + "/store/inventory");
        System.out.println("response.statusCode() = " + response.statusCode());
        response.prettyPrint();

        //assert status code
        Assert.assertEquals(response.statusCode(),200);
        //verify content type
        Assert.assertEquals(response.contentType(), "application/json");
    }

    @Test
    public void simpleGetRequest_HamCrestMatchers(){
        RestAssured
                .given()
                .accept(ContentType.JSON)
                .when()
                .get(petStoreBaseUrl + "/store/inventory")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType("application/json");
    }

    @Test
    public void simpleGetRequest_asStringMethod(){
        Response response = RestAssured
                .given()
                .accept(ContentType.JSON)
                .when()
                .get(petStoreBaseUrl + "/store/inventory");
        //status code
        System.out.println("response.statusCode() = " + response.statusCode());
        //print whole body with asString()
        System.out.println("response.body().asString() = " + response.body().asString());
        //assert status code
        Assert.assertEquals(response.statusCode(),200);
        Assert.assertTrue(response.body().asString().contains("available"));
    }
}