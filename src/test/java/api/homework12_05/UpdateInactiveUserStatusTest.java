package api.homework12_05;

import api.homework09_05.helper.Data;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import record_classes.UserDto;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class UpdateInactiveUserStatusTest {
    private RequestSpecification requestSpec;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "https://gorest.co.in";
        requestSpec = new RequestSpecBuilder().setBasePath("/public/v2").build();
    }

    @Test
    public void updateInactiveUserToActive() {

        List<UserDto> users = given()
                .spec(requestSpec)
                .when()
                .get("/users")
                .then().log().body()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList(".", UserDto.class);

        UserDto inactiveUser = users.stream()
                .filter(u -> u.status().equalsIgnoreCase("inactive"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No inactive user found"));

        int userId = inactiveUser.id();

        UserDto updatedUser = new UserDto(
                userId,
                inactiveUser.name(),
                inactiveUser.email(),
                inactiveUser.gender(),
                "active"
        );

        given()
                .spec(requestSpec)
                .header("Authorization", Data.TOKEN)
                .contentType("application/json")
                .body(updatedUser)
                .when()
                .put("/users/" + userId)
                .then()
                .statusCode(200)
                .body("status", equalTo("active"));

        UserDto verifiedUser = given()
                .spec(requestSpec)
                .header("Authorization", Data.TOKEN)
                .when()
                .get("/users/" + userId)
                .then()
                .statusCode(200)
                .extract()
                .as(UserDto.class);

        Assertions.assertEquals("active", verifiedUser.status());
    }
}
