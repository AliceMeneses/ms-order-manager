package com.majella.ordermanager.entrypoint.api.controller;

import com.majella.ordermanager.entrypoint.api.controller.payload.response.MenuPlateResponse;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import com.majella.ordermanager.helper.BaseTest;
import com.majella.ordermanager.helper.MenuPlateResponseGenerator;

import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureJsonTesters
public class MenuControllerApiTest {

    @Autowired
    private BaseTest baseTest;

    @Autowired
    private JacksonTester<Pageable> pageableJacksonTester;

    @Autowired
    private JacksonTester<List<MenuPlateResponse>> menuResponseJacksonTester;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.baseURI = DEFAULT_URI + "/v1/menu";
        baseTest.clearAll();
        baseTest.generateData();
    }

    @Nested
    @DisplayName("List menu test")
    class ListMenuTest {

        @Test
        @DisplayName("When get menu plates then return menu plates")
        public void whenGetMenuPlatesThenReturnMenuPlates() throws IOException {
            var pageable = PageRequest.of(0,2);

            var menuPlateWithGrilledChickenResponse = MenuPlateResponseGenerator.generateWithGrilledChicken();
            var menuPlateBeefResponse = MenuPlateResponseGenerator.generateWithBeef();

            String result = given()
                    .accept(JSON)
                    .contentType((JSON))
                    .body(pageableJacksonTester.write(pageable).getJson())
                .when()
                    .get()
                .then()
                    .body("", hasSize(2))
                    .statusCode(OK.value())
                    .extract().asString();

            var menuResult = menuResponseJacksonTester.parse(result).getObject();

            assertThat(menuResult)
                    .isEqualTo(List.of(menuPlateWithGrilledChickenResponse, menuPlateBeefResponse));
        }


        @Test
        @DisplayName("When get menu plates without sending pagination then return menu plates")
        public void whenGetMenuPlatesWithoutSendingPaginationThenReturnMenuPlates() throws IOException {
            var menuPlateWithGrilledChickenResponse = MenuPlateResponseGenerator.generateWithGrilledChicken();
            var menuPlateBeefResponse = MenuPlateResponseGenerator.generateWithBeef();

            String result = given()
                    .accept(JSON)
                .when()
                    .get()
                .then()
                    .body("", hasSize(2))
                    .statusCode(OK.value())
                    .extract().asString();

            var menuResult = menuResponseJacksonTester.parse(result).getObject();

            assertThat(menuResult)
                    .isEqualTo(List.of(menuPlateWithGrilledChickenResponse, menuPlateBeefResponse));
        }
    }
}
