package com.vicariustest.integrationtest.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.withArgs;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIT {

    private final String USER_ID = "9337ac4b-108f-47d3-8c2e-4fb5287f7e0e";

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void testCreateUser() {
        postUser()
                .then()
                .statusCode(201);
    }

    @Test
    public void testGetUser() {
        given()
                .when()
                .get("/users/" + USER_ID)
                .then()
                .statusCode(200)
                .body("id", is(USER_ID))
                .body("firstName", is("Updated"))
                .body("lastName", is("Name"));
    }

    @Test
    public void testUpdateUser() {
        given()
                .contentType(JSON)
                .body("{\"firstName\": \"Updated\", \"lastName\": \"Name\"}")
                .when()
                .put("/users/" + USER_ID)
                .then()
                .statusCode(200);
    }

    @Test
    public void testDeleteUser() {
        var userIdToDelete = postUser().path("id");

        given()
                .when()
                .delete("/users/" + userIdToDelete)
                .then()
                .statusCode(200);
    }

    @Test
    public void consumeQuota() {
        var createdUserId = postUser().path("id");

        given()
                .when()
                .post("/users/" + createdUserId + "/quotas/consume")
                .then()
                .statusCode(200);
    }

    @Test
    public void consumeQuotaShouldThrowTooManyRequest() {
        given()
                .when()
                .post("/users/" + USER_ID + "/quotas/consume")
                .then()
                .statusCode(429)
                .body("status", is(429))
                .body("error", is("Too Many Requests"));
    }

    @Test
    public void getQuotas() {
        given()
                .when()
                .get("/users/quotas")
                .then()
                .statusCode(200)
                .root("find { it.user.id == '%s' }")
                .body("id", withArgs(USER_ID), is("b5aac576-aafc-4eae-b83b-e7f3a7531f05"))
                .body("count", withArgs(USER_ID), is(5))
                .body("user.id", withArgs(USER_ID), is(USER_ID))
                .body("user.lastLoginTimeUtc", withArgs(USER_ID), is("2022-01-01T09:00:00"))
        ;
    }

    private static Response postUser() {
        return given()
                .contentType(JSON)
                .body("{\"firstName\": \"John\", \"lastName\": \"Doe\"}")
                .when()
                .post("/users");
    }
}
