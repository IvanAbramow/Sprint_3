import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CourierLoginTest {

    private CourierClient courierClient;
    Courier courier = Courier.getRandom();
    public int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Проверка, что курьер может авторизоваться")
    @Description("/api/v1/courier/login")
    public void checkCourierLogin(){
        courierClient.create(courier);

        ValidatableResponse validatableResponse = courierClient.login(new CourierCredentials(courier.login, courier.password));
        courierId = validatableResponse.extract().path("id");

        assertThat("Courier ID incorrect",courierId, is(not(0)));
        validatableResponse.assertThat().statusCode(200);
    }

    @Test
    @DisplayName("Проверка, что курьер не может авторизоваться без логина")
    @Description("/api/v1/courier/login")
    public void checkCourierLoginWithoutLogin(){
        courierClient.create(courier);

        ValidatableResponse validatableResponse = courierClient.login(new CourierCredentials("", courier.password));

        validatableResponse.assertThat().statusCode(400);
        validatableResponse.assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Проверка, что курьер не может авторизоваться с пустым логином")
    @Description("/api/v1/courier/login")
    public void checkCourierLoginNull(){
        courierClient.create(courier);

        ValidatableResponse validatableResponse = courierClient.login(new CourierCredentials(null, courier.password));

        validatableResponse.assertThat().statusCode(400);
        validatableResponse.assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Проверка, что курьер не может авторизоваться без пароля")
    @Description("/api/v1/courier/login")
    public void checkCourierLoginWithoutPassword(){
        courierClient.create(courier);

        ValidatableResponse validatableResponse = courierClient.login(new CourierCredentials(courier.login, ""));

        validatableResponse.assertThat().statusCode(400);
        validatableResponse.assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Проверка, что курьер не может авторизоваться с пустым паролем")
    @Description("/api/v1/courier/login")
    public void checkCourierLoginPasswordNull(){
        courierClient.create(courier);

        ValidatableResponse validatableResponse = courierClient.login(new CourierCredentials(courier.login, null));

        validatableResponse.assertThat().statusCode(400);
        validatableResponse.assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Проверка, если неправильно указан логин, возвращается ошибка")
    @Description("/api/v1/courier/login")
    public void checkIncorrectLoginReturnError(){
        courierClient.create(courier);
        String login = "Никита";

        ValidatableResponse validatableResponse = courierClient.login(new CourierCredentials(login, courier.password));

        validatableResponse.assertThat().statusCode(404);
        validatableResponse.assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Проверка, если неправильно указан пароль, возвращается ошибка.")
    @Description("/api/v1/courier/login")
    public void checkIncorrectPasswordReturnError(){
        courierClient.create(courier);
        String password = "12345";

        ValidatableResponse validatableResponse = courierClient.login(new CourierCredentials(courier.login, password));

        validatableResponse.assertThat().statusCode(404);
        validatableResponse.assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Проверка, если авторизоваться под несуществующим пользователем, запрос возвращает ошибку.")
    @Description("/api/v1/courier/login")
    public void checkCourierLoginNonExistent(){
        String login = "abcd";
        String password = "1234";

        ValidatableResponse validatableResponse = courierClient.login(new CourierCredentials(login, password));

        validatableResponse.assertThat().statusCode(404);
        validatableResponse.assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

}