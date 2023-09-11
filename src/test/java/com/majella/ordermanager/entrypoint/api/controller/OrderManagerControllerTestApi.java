package com.majella.ordermanager.entrypoint.api.controller;

import com.majella.ordermanager.entrypoint.api.controller.payload.request.OrderRequest;
import com.majella.ordermanager.entrypoint.api.controller.payload.request.PlateRequest;
import com.majella.ordermanager.entrypoint.api.controller.payload.response.OrderResponse;
import com.majella.ordermanager.helper.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureJsonTesters
public class OrderManagerControllerTestApi {

    @Autowired
    private BaseTest baseTest;

    @Autowired
    private JacksonTester<OrderRequest> orderRequestJacksonTester;

    @Autowired
    private JacksonTester<OrderResponse> orderResponseJacksonTester;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.baseURI = DEFAULT_URI + "/v1/orders";
        baseTest.clearAll();
        baseTest.generateData();
    }

    @Nested
    @DisplayName("Create Order Test")
    class CreateOrderTest {

        @Test
        @DisplayName("When create order then return order response and status 201")
        public void whenCreateOrderThenReturnOrderResponseAndStatus201() throws IOException {

            var orderRequest = OrderRequestGenerator.generate("64fe7441f968b2939fdd01c6");
            var orderResponse = OrderResponseGenerator.generate("64fe82fbf968b2939fdd01c7");

            String result = given()
                    .accept(ContentType.JSON)
                    .contentType((ContentType.JSON))
                    .body(orderRequestJacksonTester.write(orderRequest).getJson())
                .when()
                    .post()
                .then()
                    .body("id", notNullValue())
                    .body("plates", hasSize(1))
                    .statusCode(HttpStatus.CREATED.value())
                .extract().asString();

            var orderResult = orderResponseJacksonTester.parse(result).getObject();

            assertThat(orderResult)
                    .usingRecursiveComparison()
                    .ignoringFields("id")
                    .isEqualTo(orderResponse);
        }

        @Test
        @DisplayName("When create order with list plates is null then return validation message and status 400")
        public void whenCreateOrderWithListOfPlatesIsNullThenReturnValidationMessageAndStatus400() throws IOException {

            var orderRequest = OrderRequestGenerator.generateWithPlates(null);

            given()
                    .accept(ContentType.JSON)
                    .contentType((ContentType.JSON))
                    .body(orderRequestJacksonTester.write(orderRequest).getJson())
                .when()
                    .post()
                .then()
                    .body("descriptions", hasSize(1))
                    .body("descriptions", hasItem("plates is required"))
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        @DisplayName("When create order with list of empty plates then return validation message and status 400")
        public void whenCreateOrderWithListOfEmptyPlatesThenReturnValidationMessageAndStatus400() throws IOException {

            var orderRequest = OrderRequestGenerator.generateWithPlates(List.of());

            given()
                    .accept(ContentType.JSON)
                    .contentType((ContentType.JSON))
                    .body(orderRequestJacksonTester.write(orderRequest).getJson())
                .when()
                    .post()
                .then()
                    .body("descriptions", hasSize(1))
                    .body("descriptions", hasItem("plates size must be at least 1"))
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        @DisplayName("When create order with plate without information then return validation message and status 400")
        public void whenCreateOrderWithPlateWithoutInformationThenReturnValidationMessageAndStatus400() throws IOException {

            var orderRequest = OrderRequestGenerator.generateWithPlates(List.of(new PlateRequest()));

            given()
                    .accept(ContentType.JSON)
                    .contentType((ContentType.JSON))
                    .body(orderRequestJacksonTester.write(orderRequest).getJson())
                .when()
                    .post()
                .then()
                    .body("descriptions", hasSize(2))
                    .body("descriptions", hasItem("plates[0].id cannot be blank"))
                    .body("descriptions", hasItem("plates[0].quantity is required"))
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        @DisplayName("When create order with plate with negative quantitty or zero then return validation message and status 400")
        public void whenCreateOrderWithPlateWithNegativeQuantityOrZeroThenReturnValidationMessageAndStatus400() throws IOException {

            var plate1Request = PlateRequestGenerator.generate("64fe7441f968b2939fdd01c6", -4);
            var plate2Request = PlateRequestGenerator.generate("64f4d464b35055bb9b2576b9", 0);
            var orderRequest = OrderRequestGenerator.generateWithPlates(List.of(plate1Request, plate2Request));

            given()
                    .accept(ContentType.JSON)
                    .contentType((ContentType.JSON))
                    .body(orderRequestJacksonTester.write(orderRequest).getJson())
                .when()
                    .post()
                .then()
                    .body("descriptions", hasSize(2))
                    .body("descriptions", hasItem("plates[1].quantity must be greater than 0"))
                    .body("descriptions", hasItem("plates[0].quantity must be greater than 0"))
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        @DisplayName("When create order with plate that doesn't exist then return business message and status 400")
        public void whenCreateOrderWithPlateThatDoesntExistThenReturnBusinessMessageAndStatus400() throws IOException {

            var plateId = "44fb7691f368b2937fdd01a9";
            var plateRequest = PlateRequestGenerator.generate(plateId, 1);
            var orderRequest = OrderRequestGenerator.generateWithPlates(List.of(plateRequest));

            given()
                    .accept(ContentType.JSON)
                    .contentType((ContentType.JSON))
                    .body(orderRequestJacksonTester.write(orderRequest).getJson())
                .when()
                    .post()
                .then()
                    .body("descriptions", hasSize(1))
                    .body("descriptions", hasItem(String.format("There isn't plate for id %s", plateId)))
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

    }

    @Nested
    @DisplayName("Cancel Order Test")
    class CancelOrderTest {

        @Test
        @DisplayName("When cancel order then cancel order and return status 204")
        public void whenCancelOrderThenCancelOrderAndReturnStatus204() throws IOException {

            given()
                    .accept(ContentType.JSON)
                    .pathParam("id", "64fe82fbf968b2939fdd01c7")
                    .basePath("{id}/canceled")
                .when()
                    .put()
                .then()
                    .body(blankOrNullString())
                    .statusCode(HttpStatus.NO_CONTENT.value());
        }

        @Test
        @DisplayName("When cancel order with id that doesn't exist then return business message and return status 404")
        public void whenCancelOrderWithIdThatDoesntExistThenReturnBusinessMessageAndReturnStatus404() throws IOException {

            var orderId = "74fe6402f988b7939fdd01dc";

            given()
                    .accept(ContentType.JSON)
                    .pathParam("id", orderId)
                    .basePath("{id}/canceled")
                .when()
                    .put()
                .then()
                    .body("descriptions", hasSize(1))
                    .body("descriptions", hasItem(String.format("There isn't order for id %s", orderId)))
                    .statusCode(HttpStatus.NOT_FOUND.value());
        }

        @Test
        @DisplayName("When cancel an order that is ready then return business message and return status 400")
        public void whenCancelAnOrderThatIsReadyThenReturnBusinessMessageAndReturnStatus400() throws IOException {

            var orderId = "64fe90abf968b2939fdd01e1";

            given()
                    .accept(ContentType.JSON)
                    .pathParam("id", orderId)
                    .basePath("{id}/canceled")
                .when()
                    .put()
                .then()
                    .body("descriptions", hasSize(1))
                    .body("descriptions", hasItem(String.format("The order with id %s cannot be cancelled because it is ready", orderId)))
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

    }

}
