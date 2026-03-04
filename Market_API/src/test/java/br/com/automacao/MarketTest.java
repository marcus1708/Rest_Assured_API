package br.com.automacao;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import java.io.File;
import java.util.Map;
import java.util.HashMap;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class MarketTest {
    private static String idUser,idProduto,idLoja,idLista,url="http://localhost:3333";

    /* AREA DE USUARIO */  

    @Test    @Order(1)
    public void Cria_Usuário() {
        File body = new File("src/test/resources/json/usuario.json");
        idUser = given()
            .header("Content-Type", "application/json")
            .body(body)
        .when()
            .post(url +"/usuarios")
        .then()
            .statusCode(201) 
            .body("message", is("Usuario gerado com sucesso"))
            .extract()
            .path("_id");   
    }
    @Test    @Order(2)
    public void Busca_Usuario_Lista() {
        given()
            .header("Content-Type", "application/json")
        .when()
            .get(url + "/usuarios")
        .then()
            .body("message", is("Listagem de todos os usuarios realizada"))
            .statusCode(200);
    }
    @Test    @Order(3)
    public void Busca_Usuario_ID() {
        given()
            .pathParam("id", idUser)
        .when()
            .get(url+"/usuarios/{id}")
        .then()
            .body("message", is("Usuario encontrado"))
            .statusCode(200);
    }
    @Test    @Order(4)
    public void Atlz_Usuário() {
        Map<String, Object> body = new HashMap<>();
        body.put("nome", "QA Sr");
        body.put("email", "qa@io.com");
        body.put("senha", "123");
        body.put("endereco", "rua a,123");
        given()
            .header("Content-Type", "application/json")
            .body(body)
            .pathParam("id", idUser)
        .when()
            .put(url + "/usuarios/{id}")
        .then()
            .statusCode(200) 
            .body("message", is("Dados do usuario atualizados"));
    } 
    @Test    @Order(5)
    public void Atlz_Parc_Usuario() {
        Map<String, Object> body = new HashMap<>();
        body.put("endereco", "rua a,000");

        given()
            .pathParam("id", idUser)
            .body(body)
        .when()
            .patch(url + "/usuarios/{id}")
        .then()
            .statusCode(200)
            .body("message", is("Dados do usuario atualizados"));
}
    @Test    @Order(23)
    public void Deleta_Usuario() {
        given()
            .pathParam("id", idUser)
        .when()
            .delete(url+"/usuarios/{id}")
        .then()
            .statusCode(200)
            .body("message", is("Usuario excluido"));
    }  


    /* AREA DE PRODUTOS */  

    @Test    @Order(6)
    public void Cria_Produto() {
        File body = new File("src/test/resources/json/produto.json");
        idProduto = given()
            .header("Content-Type", "application/json")
            .body(body)
        .when()
            .post(url+"/produtos")
        .then()
            .statusCode(201) 
            .body("message", is("Produto gerado com sucesso"))
            .extract()
            .path("_id");
    }
    @Test    @Order(7)
    public void Busca_Produto_Lista() {
        given()
            .header("Content-Type", "application/json")
        .when()
            .get(url+"/produtos")
        .then()
            .statusCode(200)
            .body("message", is("Listagem de todos os produtos realizada"));
    }
    @Test    @Order(8)
    public void Busca_Produto_ID() {
        given()
            .pathParam("id", idProduto)
        .when()
            .get(url+"/produtos/{id}")
        .then()
            .statusCode(200)
            .body("message", is("Produto encontrado"));
    }    
    @Test    @Order(8)
    public void Atlz_Produto() {
        Map<String, Object> body = new HashMap<>();
        body.put("nome", "Produto QA");
        body.put("descricao", "prod 2");
        body.put("preco", "300");
        body.put("quantidade", "500");
        given()
            .pathParam("id", idProduto)
            .header("Content-Type", "application/json")
            .body(body)
        .when()
            .put(url+"/produtos/{id}")
        .then()
            .statusCode(200)
            .body("message", is("Dados do produto atualizados"));
    }    
    @Test    @Order(9)
    public void Atlz_Parc_Produto() {
        Map<String, Object> body = new HashMap<>();
        body.put("nome", "Produto Teste");

        given()
            .pathParam("id", idProduto)
            .header("Content-Type", "application/json")
            .body(body)
        .when()
            .patch(url+"/produtos/{id}")
        .then()
            .statusCode(200)
            .body("message", is("Dados do produto atualizados"));
    } 
    @Test    @Order(22)
    public void Deleta_Produto() {
        given()
            .pathParam("id", idProduto)
        .when()
            .delete(url+"/produtos/{id}")
        .then()
            .statusCode(200)
            .body("message", is("Produto excluido"));
    }  

    /* AREA DE LOJA */ 

    @Test    @Order(10)
    public void Cria_Loja() {
        File body = new File("src/test/resources/json/loja.json");
        idLoja = given()
            .header("Content-Type", "application/json")
            .body(body)
        .when()
            .post(url+"/lojas")
        .then()
            .statusCode(201) 
            .body("message", is("Loja gerada com sucesso"))
            .extract()
            .path("_id");
    }
    @Test    @Order(11)
    public void Busca_Loja_Lista() {
        given()
            .header("Content-Type", "application/json")
        .when()
            .get(url+"/lojas")
        .then()
            .statusCode(200)
            .body("message", is("Listagem de todas as lojas realizadas"));
    }
    @Test    @Order(12)
    public void Busca_Loja_ID() {
        given()
            .pathParam("id", idLoja)
        .when()
            .get(url+"/lojas/{id}")
        .then()
            .statusCode(200)
            .body("message", is("Loja encontrada"));
    }    
    @Test    @Order(13)
    public void Atlz_Loja() {
        Map<String, Object> body = new HashMap<>();
        body.put("nome", "Loja Teste");
        body.put("endereco", "rua xpto,00");
        body.put("filial", true);

        given()
            .pathParam("id", idLoja)
            .header("Content-Type", "application/json")
            .body(body)
        .when()
            .put(url+"/lojas/{id}")
        .then()
            .statusCode(200)
            .body("message", is("Dados da loja atualizadas"));
    }    
    @Test    @Order(14)
    public void Atlz_Parc_Loja() {
        Map<String, Object> body = new HashMap<>();
        body.put("endereco", "rua xpto,200");
        given()
            .pathParam("id", idLoja)
            .header("Content-Type", "application/json")
            .body(body)
        .when()
            .patch(url+"/lojas/{id}")
        .then()
            .statusCode(200)
            .body("message", is("Dados da loja atualizadas"));
    } 
    @Test    @Order(21)
    public void Deleta_Loja() {
        given()
            .pathParam("id", idLoja)
        .when()
            .delete(url+"/lojas/{id}")
        .then()
            .statusCode(200)
            .body("message", is("Loja excluida"));
    }  

    /* AREA DE LISTA */ 

    @Test    @Order(15)
    public void Cria_Lista() {
        
        Map<String, Object> body = new HashMap<>();
        body.put("id_user", idUser);
        body.put("id_prod", idProduto);
        body.put("id_loja", idLoja);
        body.put("quantidade", 100);
        body.put("preco", 100);

        idLista = given()
            .header("Content-Type", "application/json")
            .body(body)
        .when()
            .post(url+"/listas")
        .then()
            .statusCode(201) 
            .body("message", is("Lista gerada com sucesso"))
            .extract()
            .path("_id");
    }
    @Test    @Order(16)
    public void Busca_Lista() {
        given()
            .header("Content-Type", "application/json")
        .when()
            .get(url+"/listas")
        .then()
            .statusCode(200)
            .body("message", is("Listagem de todas as listas realizada"));
    }
    @Test    @Order(17)
    public void Busca_Lista_ID() {
        given()
            .pathParam("id", idLista)
        .when()
            .get(url+"/listas/{id}")
        .then()
            .statusCode(200)
            .body("message", is("Lista encontrada"));
    }    
    @Test    @Order(18)
    public void Atlz_Lista() {
        Map<String, Object> body = new HashMap<>();
        body.put("idUser", idProduto);
        body.put("idProduto", idProduto);
        body.put("idLoja", idProduto);
        body.put("quantidade", 200);
        body.put("preco", 300);
        given()
            .pathParam("id", idLista)
            .header("Content-Type", "application/json")
            .body(body)
        .when()
            .put(url+"/listas/{id}")
        .then()
            .statusCode(200)
            .body("message", is("Dados da lista atualizadas"));
    }    
    @Test    @Order(19)
    public void Atlz_Parc_Lista() {
        Map<String, Object> body = new HashMap<>();
        body.put("quantidade", 800);
        body.put("preco", 900);
        given()
            .pathParam("id", idLista)
            .header("Content-Type", "application/json")
            .body(body)
        .when()
            .patch(url+"/listas/{id}")
        .then()
            .statusCode(200)
            .body("message", is("Dados da lista atualizadas"));
    } 
    @Test    @Order(20)
    public void Deleta_Lista() {
        given()
            .pathParam("id", idLista)
        .when()
            .delete(url+"/listas/{id}")
        .then()
            .statusCode(200)
            .body("message", is("Lista excluida"));
    } 
}