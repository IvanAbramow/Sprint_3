import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends RestAssuredClient {

    private static final String COURIER_PATH = "api/v1/courier/";

    @Step("Создание курьера")
    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    @Step("Авторизация курьера")
    public ValidatableResponse login (CourierCredentials credentials) {
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(COURIER_PATH + "login")
                .then();
    }

    @Step("Удаление курьера по его id")
    public ValidatableResponse delete(int courierId) {
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + courierId)
                .then();
    }
} 