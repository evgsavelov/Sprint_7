package Client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrdersClient extends Client {
    private static final String PATH = "api/v1/orders";
    @Step("Создание заказа")
    public ValidatableResponse create(Order order) {
        return given()
                .spec(getSpec()) // настраивает запрос
                .body(order)
                .when()
                .post(PATH)
                .then().log().all();
    }
    @Step("Получение списка заказов")
    public ValidatableResponse getOrders() {
        return given()
                .spec(getSpec()) // настраивает запрос
                .when()
                .get(PATH)
                .then().log().all();
    }
}
