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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

/*
+курьера можно создать;
+нельзя создать двух одинаковых курьеров;
+чтобы создать курьера, нужно передать в ручку все обязательные поля;
+запрос возвращает правильный код ответа;
+успешный запрос возвращает ok: true;
+если одного из полей нет, запрос возвращает ошибку;
+если создать пользователя с логином, который уже есть, возвращается ошибка.
 */
public class CreateCourierTest {

    private Courier courier;
    private CourierClient courierClient;
    private int id;
    private String message;
    private String ok;

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
    @DisplayName("Check courier can be created")
    @Description("Basic test for /api/v1/courier endpoint. Response: 201 Created")
    public void courierCanBeCreated() {
        ValidatableResponse response = courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        id = loginResponse.extract().path("id");
        int statusCode = response.extract().statusCode();
        assertEquals(SC_CREATED, statusCode);
        Assert.assertNotNull(id);
    }

    @Test
    @DisplayName("Check that you can't create the same courier")
    @Description("Basic test for /api/v1/courier endpoint. Response: 409 Сonflict")
    public void courierCantBeTheSame() {
        ValidatableResponse response = courierClient.create(courier);
        int statusCode = response.extract().statusCode();
        message = response.extract().body().path("message").toString();
        assertEquals(SC_CONFLICT, statusCode);
        assertEquals("Этот логин уже используется.", message);
    }

    @Test
    @DisplayName("Check that courier doesn't create without password")
    @Description("Basic test for /api/v1/courier endpoint. Response: 400 Bad Request")
    public void courierCantBeWithoutPassword() {
        courier = CourierGenerator.getRandomOnlyLogin();
        ValidatableResponse response = courierClient.create(courier);
        int statusCode = response.extract().statusCode();
        message = response.extract().path("message");
        assertEquals(SC_BAD_REQUEST, statusCode);
        assertEquals("Недостаточно данных для создания учетной записи", message);
    }

    @Test
    @DisplayName("Check that courier doesn't create without login")
    @Description("Basic test for /api/v1/courier endpoint. Response: 400 Bad Request")
    public void courierCantBeWithoutLogin() {
        courier = CourierGenerator.getRandomOnlyPassword();
        ValidatableResponse response = courierClient.create(courier);
        int statusCode = response.extract().statusCode();
        message = response.extract().path("message");
        assertEquals(SC_BAD_REQUEST, statusCode);
        assertEquals("Недостаточно данных для создания учетной записи", message);
    }

    @Test
    @DisplayName("Check that courier doesn't create without firstName")
    @Description("Basic test for /api/v1/courier endpoint. Response: 400 Bad Request")
    public void courierCantBeWithoutFirstName() {
        courier = CourierGenerator.getRandomWithoutFirstName();
        ValidatableResponse response = courierClient.create(courier);
        int statusCode = response.extract().statusCode();
        message = response.extract().path("message");
        assertEquals(SC_BAD_REQUEST, statusCode);
        assertEquals("Недостаточно данных для создания учетной записи", message);
    }

    @Test
    @DisplayName("Check that courier doesn't create with the same login")
    @Description("Basic test for /api/v1/courier endpoint. Response: 400 Bad Request")
    public void courierCantBeWithTheSameLogin() {
        courier = CourierGenerator.getDefaultWithTheSameLogin();
        ValidatableResponse response = courierClient.create(courier);
        int statusCode = response.extract().statusCode();
        message = response.extract().path("message");
        assertEquals(SC_CONFLICT, statusCode);
        assertEquals("Этот логин уже используется", message);
    }
}
