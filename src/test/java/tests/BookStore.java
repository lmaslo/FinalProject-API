package tests;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import models.CredentialsLombok;
import models.GenerateTokenResponseLombok;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static listeners.CustomAllureListener.withCustomTemplates;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.*;

public class BookStore {

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "https://demoqa.com";
    }

    @Test
    void AuthSuccess() {
        CredentialsLombok credentials = new CredentialsLombok();
        credentials.setUserName("LenaTest");
        credentials.setPassword("LenaTest123!");

        given()
                .filter(withCustomTemplates())
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(credentials)
                .when()
                .post("/Account/v1/Authorized")
                .then()
                .log().status()
                .log().body()
                .statusCode(200);
    }

    @Test
    void AuthInvalidUser() {
        CredentialsLombok credentials = new CredentialsLombok();
        credentials.setUserName("LenaTest1");
        credentials.setPassword("LenaTest123!");

        given()
                .filter(withCustomTemplates())
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(credentials)
                .when()
                .post("/Account/v1/Authorized")
                .then()
                .log().status()
                .log().body()
                .statusCode(404)
                .body("message", is("User not found!"))
                .body("code", is("1207"));
    }

    @Test
    void AuthInvalidPassword() {
        CredentialsLombok credentials = new CredentialsLombok();
        credentials.setUserName("LenaTest");
        credentials.setPassword("LenaTest123");

        given()
                .filter(withCustomTemplates())
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(credentials)
                .when()
                .post("/Account/v1/Authorized")
                .then()
                .log().status()
                .log().body()
                .statusCode(404)
                .body("message", is("User not found!"))
                .body("code", is("1207"));
    }

    @Test
    void generateToken (){
        CredentialsLombok credentials = new CredentialsLombok();
        credentials.setUserName("LenaTest");
        credentials.setPassword("LenaTest123!");

        GenerateTokenResponseLombok tokenResponse =
                given()
                        .filter(withCustomTemplates())
                        .log().uri()
                        .log().body()
                        .contentType(JSON)
                        .body(credentials)
                        .when()
                        .post("Account/v1/GenerateToken")
                        .then()
                        .log().status()
                        .log().body()
                        .statusCode(200)
                        .body(matchesJsonSchemaInClasspath("schemas/GenerateToken_response_scheme.json"))
                        .extract().as(GenerateTokenResponseLombok.class);

        assertThat(tokenResponse.getStatus()).isEqualTo("Success");
        assertThat(tokenResponse.getResult()).isEqualTo("User authorized successfully.");
        assertThat(tokenResponse.getExpires()).hasSizeGreaterThan(10);
        assertThat(tokenResponse.getToken()).hasSizeGreaterThan(10).startsWith("eyJ");
    }


    //Список книг запрос без параметров схема /BookStore/v1/Books
    // Просмотре информации о книги /BookStore/v1/Book



}
