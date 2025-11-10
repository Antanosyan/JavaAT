package api.homework12_05;

import api.homework09_05.helper.Data;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import record_classes.PostDto;

import java.util.List;
import java.util.Random;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CreateAndVerifyUserPostTest {
    private RequestSpecification requestSpec;
    private static final String BASE_URI = "https://gorest.co.in";

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = BASE_URI;
        requestSpec = new RequestSpecBuilder().setBasePath("/publicii/v2").build();
    }

    @Test
    public void getPosts() {
        List<PostDto> posts = RestAssured
                .given()
                .spec(requestSpec)
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList(".", PostDto.class);

        int index = new Random().nextInt(posts.size());
        int randomUserId = posts.get(index).user_id();

        PostDto newPost = new PostDto( null,randomUserId, "new title", "new body");

        PostDto createdPost =  RestAssured
                .given()
                .spec(requestSpec)
                .header("Authorization", Data.TOKEN)
                .contentType("application/json")
                .body(newPost)
                .when()
                .post("/users/" + randomUserId+"/posts")
                .then()
                .statusCode(201)
                .extract().as(PostDto.class);

        List<PostDto> userPosts = RestAssured
                .given()
                .header("Authorization", Data.TOKEN)
                .spec(requestSpec)
                .when()
                .get("/users/" + randomUserId + "/posts")
                .then().log().body()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList(".", PostDto.class);

        boolean postExists = userPosts.stream()
                .anyMatch(p -> p.id().equals(createdPost.id()) &&
                        p.title().equals(createdPost.title()) &&
                        p.body().equals(createdPost.body()));
        Assertions.assertTrue(postExists, "The new post should exist in the user's posts");
    }
}