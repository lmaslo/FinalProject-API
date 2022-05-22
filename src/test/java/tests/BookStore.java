package tests;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import models.CredentialsLombok;
import models.GenerateTokenResponseLombok;
import models.GetBookLombok;
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
    @DisplayName("Запрос /Account/v1/Authorized")
    @Description(
            "Проверка авторизации с корректными данными"
    )
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
    @DisplayName("Запрос /Account/v1/Authorized")
    @Description(
            "Проверка авторизации с не существующим UserName"
    )
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
    @DisplayName("Запрос /Account/v1/Authorized")
    @Description(
            "Проверка авторизации с не некорректным паролем"
    )
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
    @DisplayName("Запрос Account/v1/GenerateToken")
    @Description(
            "Проверка генерации токена. " +
                    "Код ответа. " +
                    "Соответствие схеме json"
    )
    void generateToken() {
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


    @Test
    @DisplayName("Запрос /BookStore/v1/Books")
    @Description(
            "Проверка запроса - список книг. " +
                    "Код ответа. " +
                    "Соответствие схеме json"
    )
    void getBooks() {
        given()
                .filter(withCustomTemplates())
                .log().uri()
                .log().body()
                .get("/BookStore/v1/Books")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/GetBooks_scheme.json"));
    }

    @Test
    @DisplayName("Запрос /BookStore/v1/Book")
    @Description(
            "Проверка запроса с заданным параметром ISBN=9781449331818. " +
                    "Код ответа. " +
                    "Проверка параметров в ответе Isbn, Title, Author"
    )
    void getBook() {

        String idBook = "9781449331818";

        GetBookLombok getBookResponse =
                given()
                        .filter(withCustomTemplates())
                        .log().uri()
                        .log().body()
                        .get("/BookStore/v1/Book?ISBN={idBook}", idBook)
                        .then()
                        .log().status()
                        .log().body()
                        .statusCode(200)
                        .extract().as(GetBookLombok.class);

        assertThat(getBookResponse.getIsbn()).isEqualTo(idBook);
        assertThat(getBookResponse.getTitle()).isEqualTo("Learning JavaScript Design Patterns");
        assertThat(getBookResponse.getAuthor()).isEqualTo("Addy Osmani");
    }


}
