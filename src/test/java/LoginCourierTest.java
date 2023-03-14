import Client.Courier;
import Client.CourierClient;
import Client.CourierCredentials;
import Util.CourierGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/*
+курьер может авторизоваться;
+для авторизации нужно передать все обязательные поля;
+система вернёт ошибку, если неправильно указать логин или пароль;
+если какого-то поля нет, запрос возвращает ошибку;
если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
успешный запрос возвращает id.
 */
public class LoginCourierTest {
    private Courier courier;
    private CourierClient courierClient;
    private int id;
    private String message;


    @Before
    public void setUp() {
        courier = CourierGenerator.getRandom();
        courierClient = new CourierClient();
    }

    @After
    public void cleanUp() {
        courierClient.delete(id);
    }

    @Test
    @DisplayName("Client.Courier can successfully login with correct credentials")
    @Description("Basic test for /api/v1/courier/login endpoint. Response: 200")
    public void courierCanLoginWithCorrectCredentials() {

        courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        int statusCode = loginResponse.extract().statusCode();
        id = loginResponse.extract().path("id");

        assertEquals(SC_OK, statusCode);
        Assert.assertNotNull(id);
    }

    //система вернёт ошибку, если неправильно указать логин или пароль;
    @Test
    @DisplayName("Client.Courier can't login without password'")
    @Description("Basic test for /api/v1/courier/login endpoint. Response: 400")
    public void courierCanNotLoginWithoutPassword() {
        courier = CourierGenerator.getRandomOnlyLogin();
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        message = loginResponse.extract().path("message");
        int statusCode = loginResponse.extract().statusCode();
        assertEquals(SC_BAD_REQUEST, statusCode);
        assertEquals("Недостаточно данных для входа", message);
    }

    @Test
    @DisplayName("Client.Courier can't login without login'")
    @Description("Basic test for /api/v1/courier/login endpoint. Response: 400")
    public void courierCanNotLoginWithoutLogin() {
        courier = CourierGenerator.getRandomOnlyPassword();
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        message = loginResponse.extract().path("message");
        int statusCode = loginResponse.extract().statusCode();
        assertEquals(SC_BAD_REQUEST, statusCode);
        assertEquals("Недостаточно данных для входа", message);
    }

}
