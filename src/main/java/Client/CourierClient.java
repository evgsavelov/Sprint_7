package Client;

import io.qameta.allure.Step;
import  io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends Client {

    private static final String PATH = "api/v1/courier";
    @Step("Создать курьера")
    public ValidatableResponse create(Courier courier){
        return given()
                .spec(getSpec()) // настраивает запрос
                .body(courier)
                .when()
                .post(PATH)
                .then().log().all();
    }
    @Step("Авторизация курьера")
    public ValidatableResponse login(CourierCredentials courierCredentials) {
        return given()
                .spec(getSpec()) // настраивает запрос
                .body(courierCredentials)
                .when()
                .post(PATH + "/login")
                .then().log().all();    }
    @Step("Удаление курьера")
    public ValidatableResponse delete(int id) {
        return given()
                .spec(getSpec()) // настраивает запрос
                .when()
                .delete(PATH + "/" + id)
                .then().log().all();
    }
    @Step("Удаление курьера без id")
    public ValidatableResponse deleteWithoutID(int id) {
        return given()
                .spec(getSpec()) // настраивает запрос
                .when()
                .delete(PATH + "/:")
                .then().log().all();
    }
    @Step("Удаление курьера с некорректным id")
    public ValidatableResponse deleteWithIncorrectId(int id) {
        return given()
                .spec(getSpec()) // настраивает запрос
                .when()
                .delete(PATH + "/" + (id - 1111111))
                .then().log().all();
    }
}
