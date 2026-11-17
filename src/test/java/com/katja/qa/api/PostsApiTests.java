package com.katja.qa.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

/**
 * REST API tests against the free practice API https://jsonplaceholder.typicode.com
 * (the same kind of checks you would do in Postman, but automated).
 */
public class PostsApiTests {

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test(description = "GET one post returns 200 and the expected fields")
    public void getPost_returnsPost() {
        given()
                .when().get("/posts/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("userId", equalTo(1))
                .body("title", not(emptyOrNullString()));
    }

    @Test(description = "GET all posts returns 100 items")
    public void getAllPosts_returns100() {
        given()
                .when().get("/posts")
                .then()
                .statusCode(200)
                .body("size()", equalTo(100));
    }

    @Test(description = "POST creates a post and returns 201")
    public void createPost_returns201() {
        String newPost = """
        {
          "title": "QA test",
          "body": "Created by an automated test",
          "userId": 1
        }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(newPost)
                .when().post("/posts")
                .then()
                .statusCode(201)
                .body("title", equalTo("QA test"))
                .body("id", notNullValue());
    }

    @Test(description = "GET a post that does not exist returns 404")
    public void getUnknownPost_returns404() {
        given()
                .when().get("/posts/99999")
                .then()
                .statusCode(404);
    }
}
