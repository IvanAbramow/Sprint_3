import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Order extends RestAssuredClient {

    Response response;
    private static final String ORDER_PATH = "api/v1/orders/";

    @Step("Оформить заказ")
    public Response createOrder(String [] color) throws JsonProcessingException {

        Map<String, Object> data = new HashMap<>();
        data.put( "firstName", "Naruto" );
        data.put( "lastName", "Uchiha" );
        data.put( "address", "Konoha, 142 apt." );
        data.put( "metroStation", 4 );
        data.put( "phone", "+7 800 355 35 35" );
        data.put( "rentTime", 5 );
        data.put( "deliveryDate", "2021-06-10" );
        data.put( "comment", "Uchiha" );
        data.put( "color", color );
        String json = new ObjectMapper().writeValueAsString(data);

        return response = given()
                .spec(getBaseSpec())
                .body(json)
                .when()
                .post(ORDER_PATH);
    }

    @Step("Отменить заказ")
    public ValidatableResponse deleteOrder(int orderTrack) {
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(ORDER_PATH + "cancel/")
                .then();
    }

    @Step("Получить список заказов")
    public ValidatableResponse getOrderList() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH)
                .then();
    }
}
