package com.majella.ordermanager.entrypoint.api.controller;

import com.majella.ordermanager.entrypoint.api.controller.payload.response.MenuPlateResponse;
import com.majella.ordermanager.helper.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureJsonTesters
public class MenuTestControllerApi {

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

            var menuPlateResponse1 = MenuPlateResponseGenerator.generate("64fe7441f968b2939fdd01c6", "Filé de frango à parmegiana", new BigDecimal(40));
            var menuPlateResponse2 = MenuPlateResponseGenerator.generate("64f4d464b35055bb9b2576b9", "Bife", new BigDecimal(60));

            String result = given()
                    .accept(ContentType.JSON)
                    .contentType((ContentType.JSON))
                    .body(pageableJacksonTester.write(pageable).getJson())
                .when()
                    .get()
                .then()
                    .body("", hasSize(2))
                    .statusCode(HttpStatus.OK.value())
                    .extract().asString();

            var menuResult = menuResponseJacksonTester.parse(result).getObject();

            assertThat(menuResult)
                    .isEqualTo(List.of(menuPlateResponse1, menuPlateResponse2));
        }


        @Test
        @DisplayName("When get menu plates then return menu plates")
        public void whenGetMenuPlatesWithoutSendingPaginationThenReturnMenuPlates() throws IOException {
            var menuPlateResponse1 = MenuPlateResponseGenerator.generate("64fe7441f968b2939fdd01c6", "Filé de frango à parmegiana", new BigDecimal(40));
            var menuPlateResponse2 = MenuPlateResponseGenerator.generate("64f4d464b35055bb9b2576b9", "Bife", new BigDecimal(60));

            String result = given()
                    .accept(ContentType.JSON)
                .when()
                    .get()
                .then()
                    .body("", hasSize(2))
                    .statusCode(HttpStatus.OK.value())
                    .extract().asString();

            var menuResult = menuResponseJacksonTester.parse(result).getObject();

            assertThat(menuResult)
                    .isEqualTo(List.of(menuPlateResponse1, menuPlateResponse2));
        }
    }
}
