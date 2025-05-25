package api.homework09_05;

import api.homework09_05.helper.Data;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestApi {
    private int deletedUserId;
    private RequestSpecification requestSpec;
    private static final String BASE_URI = "https://gorest.co.in";

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = BASE_URI;
        requestSpec = new RequestSpecBuilder().setBasePath("/public-api").build();
    }

    @Test
    @Tag("api")
    @Order(1)
    public void testGetUsersDefault() {
        RestAssured
                .given()
                .spec(requestSpec)
                .when()
                .get("/users")
                .then()
                .log().body()
                .statusCode(200)
                .body("data.size()", Matchers.is(10));
    }

    @Test
    @Order(2)
    public void testGetUsersWithPerPage() {
        int randomValue = ThreadLocalRandom.current().nextInt(1, 201);
        int perPage = randomValue > 100 ? 10 : randomValue;

        RestAssured
                .given()
                .spec(requestSpec)
                .queryParam("per_page", perPage)
                .when()
                .get("/users")
                .then().log().body()
                .statusCode(200)
                .body("data.size()", equalTo(perPage));
    }

    @Test
    @Order(3)
    public void testValidateGender() {
        RestAssured
                .given()
                .spec(requestSpec)
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .body("data.gender", everyItem(is(oneOf("male", "female"))));
    }

    @Test
    @Order(4)
    public void testDeleteUser() {
        Response response = RestAssured
                .given()
                .spec(requestSpec)
                .when()
                .get("/users");

        int userCount = response.jsonPath().getList("data").size();
        int randomIndex = new Random().nextInt(userCount);
        deletedUserId = response.jsonPath().getInt("data[" + randomIndex + "].id");
        RestAssured
                .given()
                .spec(requestSpec)
                .header("Authorization", Data.TOKEN)
                .when()
                .delete("/users/" + deletedUserId)
                .then()
                .statusCode(200);
    }

    @Test
    @Order(5)
    public void testGetDeletedUser() {
        RestAssured
                .given()
                .spec(requestSpec)
                .when()
                .get("/users/" + deletedUserId)
                .then()
                .statusCode(200);
    }
}