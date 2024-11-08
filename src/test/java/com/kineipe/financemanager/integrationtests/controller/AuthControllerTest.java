package com.kineipe.financemanager.integrationtests.controller;

import com.kineipe.financemanager.config.TestConfig;
import com.kineipe.financemanager.domain.dto.AccountCredentials;
import com.kineipe.financemanager.domain.dto.Token;
import com.kineipe.financemanager.integrationtests.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonMappingException;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql(statements = "INSERT INTO `users` (`user_name`, `first_name`, `last_name`, `password`, `account_non_expired`, `account_non_locked`, `credentials_non_expired`, `enabled`) VALUES ('admin', 'Admin', 'Test', 'ebde8399fb4c61737b329d45f9a9286212bdeb71a19b7098e9ff36a820870bf493d4fa610692ef91', true, true, true, true);",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class AuthControllerTest extends AbstractIntegrationTest {

    private static Token token;

    @Test
    @Order(1)
    public void testSignin() throws JsonMappingException, JsonProcessingException {

        AccountCredentials user =
                new AccountCredentials("admin", "123");

        token = given()
                .basePath("/auth/signin")
                .port(TestConfig.SERVER_PORT)
                .contentType(TestConfig.CONTENT_TYPE_JSON)
                .body(user)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(Token.class);

        assertNotNull(token.getAccessToken());
        assertNotNull(token.getRefreshToken());
    }

    @Test
    @Order(2)
    public void testRefresh() throws JsonMappingException, JsonProcessingException {

        var newToken = given()
                .basePath("/auth/refresh")
                .port(TestConfig.SERVER_PORT)
                .contentType(TestConfig.CONTENT_TYPE_JSON)
                .pathParam("username", token.getUsername())
                .header(TestConfig.HEADER_PARAM_AUTHORIZATION, "Bearer " + token.getRefreshToken())
                .when()
                .put("{username}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(Token.class);

        assertNotNull(newToken.getAccessToken());
        assertNotNull(newToken.getRefreshToken());
    }
}
