package api.homework09_05;

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
public class TestApiV1 {
    private RequestSpecification requestSpec;
    private int deletedUserId;
    private static final String TOKEN = "Bearer d17163e3268695238ea007fd57d8b00191c2bfa2e84a5f9be9b1662784c7f84c";
    private static final String BASE_URI = "https://gorest.co.in";

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = BASE_URI;
        requestSpec = new RequestSpecBuilder().setBasePath("/public/v1").build();
    }

    @Test
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
                .header("Authorization", TOKEN)
                .when()
                .delete("/users/" + deletedUserId)
                .then()
                .statusCode(204);
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
                .statusCode(404);
    }
}
