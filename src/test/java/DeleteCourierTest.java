import Client.Courier;
import Client.CourierClient;
import Client.CourierCredentials;
import Util.CourierGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

public class DeleteCourierTest {
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
    @Test
    @DisplayName("Check courier can be deleted")
    public void courierCanBeDeleted(){
        courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        id = loginResponse.extract().path("id");
        ValidatableResponse response = courierClient.delete(id);
        int statusCode = response.extract().statusCode();
        ok = response.extract().body().path("ok").toString();

        assertEquals(SC_OK, statusCode);
        assertEquals("true", ok);
    }

    @Test
    @DisplayName("Check courier can't be deleted without ID")
    public void courierDeleteWithoutID(){
        courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        id = loginResponse.extract().path("id");
        ValidatableResponse response = courierClient.deleteWithoutID(id);
        int statusCode = response.extract().statusCode();
        message = response.extract().body().path("message").toString();

        assertEquals(SC_INTERNAL_SERVER_ERROR, statusCode);
        assertEquals("invalid input syntax for type integer: \":\"", message);
    }

    @Test
    @DisplayName("Check courier can't be deleted with incorrect ID")
    public void courierDeleteWithIncorrectID(){
        courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        id = loginResponse.extract().path("id");
        ValidatableResponse response = courierClient.deleteWithIncorrectId(id);
        int statusCode = response.extract().statusCode();
        message = response.extract().body().path("message").toString();

        assertEquals(SC_NOT_FOUND, statusCode);
        assertEquals("Курьера с таким id нет.", message);
    }
}
