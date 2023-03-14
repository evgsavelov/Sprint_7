import Client.OrdersClient;
import Model.OrderData;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;


import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;


public class GetOrdersTest {
    private OrdersClient ordersClient = new OrdersClient();

    @Test
    @DisplayName("Check list of orders")
    public void orderListTest() {
        List<OrderData> orders = ordersClient.getOrders()
                .extract().body().jsonPath().getList("orders", OrderData.class);

        orders.forEach(x -> Assert.assertThat(x.getId(), notNullValue()));
        orders.forEach(x -> Assert.assertThat(x.getTrack(), notNullValue()));

    }
}
