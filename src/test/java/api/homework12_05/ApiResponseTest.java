package api.homework12_05;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import record_classes.ApiResponseItemDto;

import java.io.File;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ApiResponseTest {
    private static final String BASE_URI = "https://api.restful-api.dev";

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = BASE_URI;
    }

    @Test
    public void testDeserialize() {
        File schemaFile = new File("C:\\Data\\javaAT2025\\MyProjectAT\\src\\test\\resources\\scheme.json");
        List<ApiResponseItemDto> items = RestAssured
                .when()
                .get("/objects")
                .then().log().body()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema(schemaFile))
                .extract()
                .jsonPath()
                .getList(".", ApiResponseItemDto.class);

        assertFalse(items == null || items.isEmpty(), "The list of deserialized items is null or empty!");
    }
}
