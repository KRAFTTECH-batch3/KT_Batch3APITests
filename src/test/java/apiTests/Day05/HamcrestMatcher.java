package apiTests.Day05;

import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class HamcrestMatcher {
    @BeforeClass
    public void beforeClass() {
        baseURI = "https://www.krafttechexlab.com/sw/api/v1";
    }

    @Test
    public void getOneUser() {
        /** Class Task
         * given accept type is JSON
         * And path param id is 111
         * When user send a get request to /allusers/getbyid/{id}
         * Then status code should be 200
         * And content type  should be application/json; charset=UTF-8
         *
         */

        given().accept(ContentType.JSON)
                .and()
                .pathParam("id", 111)
                .when()
                .get("allusers/getbyid/{id}")
                .then()
                .statusCode(200)
                .and()
                .assertThat()
                .contentType("application/json; charset=UTF-8");

    }

    @Test
    public void getOneUserWithHamcrestMatcher(){

        given().accept(ContentType.JSON)
                .and()
                .pathParam("id",111)
                .when()
                .get("allusers/getbyid/{id}")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .assertThat()
                .contentType("application/json; charset=UTF-8")
                .and()
                .body("id[0]", Matchers.equalTo(111),
                        "name[0]",Matchers.equalTo("Thomas Eduson"),
                        "job[0]",equalTo("Developer"),
                        "skills[0][2]",equalTo("Selenium"),
                        "[0].skills[2]",equalTo("Selenium"),
                       "education[0].school[1]",equalTo("Delft University"),
                        "[0].education[0].school",equalTo("ODTU"),
                        "education[0][1].school",equalTo("Delft University"));

    }

    @Test
    public void hamcrest2(){
        /**
         * given accept type is JSON
         * And path param id is 111
         * When user send a get request to /allusers/getbyid/{id}
         * Then status code 200
         * And content Type application/json; charset=UTF-8
         * And response header Content-Type should be application/json; charset=UTF-8
         * And response header Content-Length should be 606
         * And json data should have email equal "thomas@test.com"
         * And json data should have company equal "GHAN Software"
         *
         */
        given().accept(ContentType.JSON)
                .and()
                .pathParam("id",111)
                .when()
                .get("allusers/getbyid/{id}")
                .then()
                .statusCode(200)
                .and()
                .contentType("application/json; charset=UTF-8")
                .and()
                .header("Content-Type",equalTo("application/json; charset=UTF-8"))
                .and()
                .header("Content-Length",equalTo("606"))
                .and()
                .header("Date",notNullValue())
                .assertThat()
                .body("[0].email",equalTo("thomas@test.com"),
                        "company[0]",equalTo("GHAN Software"));
    }

    @Test
    public void hamcrest3() {
        given().accept(ContentType.JSON)
                .queryParam("pagesize",50)
                .and()
                .queryParam("page",1)
                .when()
                .log().all()
                .get("allusers/alluser")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType("application/json;")
                .and()
                .body("email",hasItem("ghan@krafttechexlab.com"))
                .log().all();

    }

    @Test
    public void hamcrest4() {
        given().accept(ContentType.JSON)
                .queryParam("pagesize",50)
                .and()
                .queryParam("page",1)
                .when()
                .log().all()  // to get request body
                .get("allusers/alluser")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType("application/json;")
                .and()
                .body("email",hasItems("ghan@krafttechexlab.com","blackuncle9599@gmail.com",
                        "user33@test.com","sekercikefe@gmail.com","eddiem@kraft.com","qateam@test.com"))
                .log().all(); // to get response body

    }

    @Test
    public void allUsersWithHamcrest() {
        /**
         * given accept type is JSON
         * And query param pagesize is 50
         * And query param page is 1
         * When user sends a get request to /allusers/alluser
         * Then status code 200
         * And content Type application/json; charset=UTF-8
         * And response header Content-Type should be application/json; charset=UTF-8
         * And response header Content-Length should be 9090
         * And response header Server should be Apache/2
         * And response header has Date
         * And json data should have name equal "GHAN","Aegon Targaryen HTU","Mansimmo"
         * And json data should have  "bilkent" for school
         * And json data should have  "Junior Developer1" for first user's first experience
         *
         */

        given().accept(ContentType.JSON)
                .queryParam("pagesize",50)
                .and()
                .queryParam("page",1)
                .when()
                //.log().all()
                .get("allusers/alluser")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType(equalTo("application/json; charset=UTF-8"))
                .and()
                .header("Content-Length",equalTo("9090"))
                .and()
                .header("Server",equalTo("Apache/2"))
                .headers("Date",notNullValue())
                .and()
                .body("name",hasItems("GHAN","Aegon Targaryen HTU","Mansimmo"),
                        "[5].education[0].school",equalTo("bilkent"),
                        "[0].experience[0].job",equalTo("Junior Developer1")
                        );

    }
}
