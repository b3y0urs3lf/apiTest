import com.google.common.base.Charsets;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.when;
import static org.hamcrest.core.IsEqual.equalTo;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import com.google.common.io.Resources;


import static io.restassured.RestAssured.given;

public class apiTest {
    public String userId;
    public String updatedAt;

    public String generateStringFromResource(String path) throws IOException {
        String newString = Resources.toString(Resources.getResource(path), Charsets.UTF_8);
        return newString;
    }


    @Before
    public void setup() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test
    public void create() throws IOException {

        String jsonBody = generateStringFromResource("create.json");
        userId =
        given().
                contentType("application/json").
                body(jsonBody).
        when().
                post("/api/users").
        then().
                statusCode(201).
                assertThat().body("name", equalTo("morpheus")).
                assertThat().body("job", equalTo("engineer")).
        extract().
                path("id");
        System.out.println(userId);
    }

    @Test
    public void update() throws IOException {

        String jsonBody = generateStringFromResource("update.json");
        updatedAt =
                given().
                        contentType("application/json").
                        body(jsonBody).
                when().
                        patch("/api/users" + userId).
                then().
                        statusCode(200).
                        assertThat().body("name", equalTo("morpheus")).
                        assertThat().body("job", equalTo("qa engineer")).
                extract().
                        path("updatedAt");
        System.out.println(updatedAt);
    }

    @Test
    public void delete(){
        when().
                delete("/api/users" + userId).
        then().
                statusCode(204);
    }
}
