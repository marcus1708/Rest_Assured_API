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

public class ServeRestTest {
    private static String idUsuario,idProduto,idCarrinho,Token;

    /* AREA DE USUARIO */  

    @Test    @Order(1)
    public void Cria_Usuário() {
        baseURI = "https://serverest.dev";
        File body = new File("src/test/resources/json/usuario.json");
        idUsuario = given()
            .header("Content-Type", "application/json")
            .body(body)
        .when()
            .post("/usuarios")
        .then()
            .statusCode(201) 
            .body("message", is("Cadastro realizado com sucesso"))
            .extract()
            .path("_id");
    }
    @Test    @Order(2)
    public void Busca_Usuario_Lista() {
        baseURI = "https://serverest.dev";
        given()
            .header("Content-Type", "application/json")
        .when()
            .get("/usuarios")
        .then()
            .statusCode(200);
    }
    @Test    @Order(3)
    public void Busca_Usuario_ID() {
        baseURI = "https://serverest.dev";
        given()
            .pathParam("id", idUsuario)
        .when()
            .get("/usuarios/{id}")
        .then()
            .statusCode(200);
    }
    @Test    @Order(4)
    public void Atlz_Usuário() {
        baseURI = "https://serverest.dev";
        File body = new File("src/test/resources/json/usuario.json");
        given()
            .header("Content-Type", "application/json")
            .body(body)
            .pathParam("id", idUsuario)
        .when()
            .put("/usuarios/{id}")
        .then()
            .statusCode(200) 
            .body("message", is("Registro alterado com sucesso"));
    } 
    @Test    @Order(5)
    public void Login_Usuario() {
        
        baseURI = "https://serverest.dev";

        File file = new File("src/test/resources/json/usuario.json");
        JsonPath jsonPath = new JsonPath(file);

         String email = jsonPath.getString("email");
         String password = jsonPath.getString("password");

         Map<String, String> login = new HashMap<>();
         login.put("email", email);
         login.put("password", password);

         Token=given()
            .header("Content-Type", "application/json")
            .body(login)
        .when()
            .post("/login")
        .then()
            .statusCode(200)
            .body("message", is("Login realizado com sucesso"))
            .extract()
            .path("authorization");
}
    @Test    @Order(14)
    public void Deleta_Usuario() {
        baseURI = "https://serverest.dev";
        given()
            .pathParam("id", idUsuario)
        .when()
            .delete("/usuarios/{id}")
        .then()
            .statusCode(200)
            .body("message", is("Registro excluído com sucesso"));
    }  


    /* AREA DE PRODUTOS */  

    @Test    @Order(6)
    public void Cria_Produto() {
        baseURI = "https://serverest.dev";
        File body = new File("src/test/resources/json/produto.json");
        idProduto = given()
            .header("Content-Type", "application/json")
            .header("Authorization", Token)
            .body(body)
        .when()
            .post("/produtos")
        .then()
            .statusCode(201) 
            .body("message", is("Cadastro realizado com sucesso"))
            .extract()
            .path("_id");
    }
    @Test    @Order(7)
    public void Busca_Produto_Lista() {
        baseURI = "https://serverest.dev";
        given()
            .header("Content-Type", "application/json")
            .header("Authorization", Token)
        .when()
            .get("/produtos")
        .then()
            .statusCode(200);
    }
    @Test    @Order(8)
    public void Busca_Produto_ID() {
        baseURI = "https://serverest.dev";
        given()
            .pathParam("id", idProduto)
            .header("Authorization", Token)
        .when()
            .get("/produtos/{id}")
        .then()
            .statusCode(200);
    }    
    @Test    @Order(13)
    public void Deleta_Produto() {
        baseURI = "https://serverest.dev";
        given()
            .pathParam("id", idProduto)
            .header("Authorization", Token)
        .when()
            .delete("/produtos/{id}")
        .then()
            .statusCode(200)
            .body("message", is("Registro excluído com sucesso"));
    }  


    /* AREA DE CARRINHOS */ 
    @Test    @Order(9)
    public void Cria_Carrinho() {
        baseURI = "https://serverest.dev";
        Map<String, Object> produto = new HashMap<>();
        produto.put("idProduto", idProduto);
        produto.put("quantidade", 1);

        List<Map<String, Object>> produtos = new ArrayList<>();
        produtos.add(produto);

        Map<String, Object> carrinho = new HashMap<>();
        carrinho.put("produtos", produtos);
        idCarrinho = given()
            .header("Content-Type", "application/json")
            .header("Authorization", Token)
            .body(carrinho)
        .when()
            .post("/carrinhos")
        .then()
            .statusCode(201) 
            .body("message", is("Cadastro realizado com sucesso"))
            .extract()
            .path("_id");
    }
    @Test    @Order(10)
    public void Busca_Carrinho_Lista() {
        baseURI = "https://serverest.dev";
        given()
            .header("Content-Type", "application/json")
            .header("Authorization", Token)
        .when()
            .get("/carrinhos")
        .then()
            .statusCode(200);
    }
    @Test    @Order(11)
    public void Busca_Carrinho_ID() {
        baseURI = "https://serverest.dev";
        given()
            .pathParam("id", idCarrinho)
            .header("Authorization", Token)
        .when()
            .get("/carrinhos/{id}")
        .then()
            .statusCode(200);
    }
    @Test    @Order(12)
    public void Deleta_Carrinho() {
        baseURI = "https://serverest.dev";
        given()
            .header("Authorization", Token)
        .when()
            .delete("/carrinhos/cancelar-compra")
        .then()
            .statusCode(200)
            .body("message", is("Registro excluído com sucesso. Estoque dos produtos reabastecido"));
    } 
}