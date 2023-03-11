import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;


public class CreateOrderTest {
    private final OrdersGenerator ordersGenerator = new OrdersGenerator();
    private final OrdersClient ordersClient = new OrdersClient();
    @Parameterized.Parameter
    public String[] color;

    public CreateOrderTest() {

    }

    @Parameterized.Parameters
    public static Object[][] getColor() {
        return new Object[][]{
                {new String[]{"BLACK", "GRAY"}},
                {new String[]{"BLACK"}},
                {new String[]{"GRAY"}},
                {new String[]{"GRAY", "BLACK"}},
                {new String[]{}}
        };
    }

    @Test
    @DisplayName("Check order creation")
    public void successOrderCreateResponseContainsTrack() {
        Order order = ordersGenerator.randomOrderWithSpecificColor(color);
        ValidatableResponse response = ordersClient.create(order);
        int statusCode = response.extract().statusCode();
        int track = response.extract().path("track");
        assertEquals(SC_CREATED, statusCode);
        Assert.assertNotNull(track);
    }


}
