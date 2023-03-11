import io.restassured.builder.RequestSpecBuilder;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;


public class Client {
    private static final String BASE_URL = "http://qa-scooter.praktikum-services.ru/";

    protected RequestSpecification getSpec(){ // собрали настройки, кладем их в .spec()
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URL)
                .build();

    }
}
