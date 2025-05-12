package api.homework12_05;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import record_classes.ApiResponseItem;

import java.util.List;

public class ApiResponseTest {
    private static final String BASE_URI = "https://api.restful-api.dev";

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = BASE_URI;
    }

    @Test
    public void testDeserialize() {
        List<ApiResponseItem> userPosts = RestAssured
                .when()
                .get("/objects")
                .then().log().body()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList(".", ApiResponseItem.class);
    }
}
