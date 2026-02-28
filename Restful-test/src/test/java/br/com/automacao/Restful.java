package br.com.automacao;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.restassured.path.json.JsonPath;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import java.io.File;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class Restful {
    private static String idObjeto,baseURI = "https://api.restful-api.dev";

    @Test    @Order(1)
    public void Cria_Objeto() {
        
        File body = new File("src/test/resources/json/objeto.json");
        idObjeto = given()
            .header("Content-Type", "application/json")
            .body(body)
        .when()
            .post(baseURI + "/objects")
        .then()
            .statusCode(200) 
            .extract()
            .path("id");
    }
    @Test    @Order(2)
    public void Busca_Objeto_Lista() {
        given()
            .header("Content-Type", "application/json")
        .when()
            .get(baseURI + "/objects")
        .then()
            .statusCode(200);
    }
    @Test    @Order(3)
    public void Busca_Objeto_ID() {
        given()
            .pathParam("id", idObjeto)
        .when()
            .get(baseURI + "/objects/{id}")
        .then()
            .statusCode(200);
    }
    @Test    @Order(4)
    public void Atlz_Objeto() {
        File body = new File("src/test/resources/json/objeto.json");
        given()
            .header("Content-Type", "application/json")
            .body(body)
            .pathParam("id", idObjeto)
        .when()
            .put(baseURI + "/objects/{id}")
        .then()
            .statusCode(200);
    } 
    @Test    @Order(5)
    public void Atlz_Parc_Objeto() {
        File body = new File("src/test/resources/json/objeto.json");
        JsonPath jsonPath = new JsonPath(body);
        String name = jsonPath.getString("name");
        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("name", name);
        given()
            .header("Content-Type", "application/json")
            .body(body)
            .pathParam("id", idObjeto)
        .when()
            .patch(baseURI + "/objects/{id}")
        .then()
            .statusCode(200);
    } 
    @Test    @Order(6)
    public void Deleta_Objeto() {
        given()
            .pathParam("id", idObjeto)
        .when()
            .delete(baseURI + "/objects/{id}")
        .then()
            .statusCode(200);
    }  

}