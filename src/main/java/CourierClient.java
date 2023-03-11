import  io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends Client {

    private static final String PATH = "api/v1/courier";
    public ValidatableResponse create(Courier courier){
        return given()
                .spec(getSpec()) // настраивает запрос
                .body(courier)
                .when()
                .post(PATH)
                .then().log().all();
    }

    public ValidatableResponse login(CourierCredentials courierCredentials) {
        return given()
                .spec(getSpec()) // настраивает запрос
                .body(courierCredentials)
                .when()
                .post(PATH + "/login")
                .then().log().all();    }

    public ValidatableResponse delete(int id) {
        return given()
                .spec(getSpec()) // настраивает запрос
                .when()
                .delete(PATH + "/" + id)
                .then().log().all();
    }
    public ValidatableResponse deleteWithoutID(int id) {
        return given()
                .spec(getSpec()) // настраивает запрос
                .when()
                .delete(PATH + "/:")
                .then().log().all();
    }
    public ValidatableResponse deleteWithIncorrectId(int id) {
        return given()
                .spec(getSpec()) // настраивает запрос
                .when()
                .delete(PATH + "/" + (id - 1111111))
                .then().log().all();
    }
}
