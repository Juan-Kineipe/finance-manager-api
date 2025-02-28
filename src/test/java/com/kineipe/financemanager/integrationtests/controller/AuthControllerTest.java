package com.kineipe.financemanager.integrationtests.controller;

import com.kineipe.financemanager.config.TestConfig;
import com.kineipe.financemanager.domain.dto.LoginRequestDTO;
import com.kineipe.financemanager.integrationtests.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonMappingException;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql(statements = "INSERT INTO `users` (`user_name`, `first_name`, `last_name`, `password`, `account_non_expired`, `account_non_locked`, `credentials_non_expired`, `enabled`) VALUES ('admin', 'Admin', 'Test', '$2a$10$8791PPc0iYD36p.lcX.J1OZXXnAjhE28u1SSf3gJ/Od3xssgocgUO', true, true, true, true);",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class AuthControllerTest extends AbstractIntegrationTest {

    @Test
    public void testSignin() throws JsonMappingException, JsonProcessingException {

        LoginRequestDTO user =
                new LoginRequestDTO("admin", "senha1");

        var token = given()
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
                .asString();

        assertNotNull(token);
    }
}
