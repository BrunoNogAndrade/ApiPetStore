package Testes;

import JsonObjects.Category;
import JsonObjects.Order;
import JsonObjects.Pet;
import JsonObjects.Tag;
import Utils.Utils;
import Base.TestBase;
import com.aventstack.extentreports.Status;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;
import static Utils.Constants.*;
import static Utils.ExtentReportsUtils.TEST;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.junit.Assert.assertEquals;

public class TestesDesafioTecnico extends TestBase {
    @Test
    public void CadastrarNovoPedidoDePetComSucesso(){
        int id = Utils.getRandomNumber(5);
        int petId = 99998;
        int quantity = 1;
        String shipDate = Utils.getNowDate("yyyy-MM-dd");
        boolean complete = true;
        Order order = new Order(id, petId, quantity, shipDate, StatusPlaced, complete);

        TEST.log(Status.INFO, "agora vou executar o teste para o pet de id: "+ petId);

        given()
                .baseUri(UrlBase)
                .basePath("/store/order")
                .header("content-type", "application/json")
                .body(order)
                .when()
                .post()
                .then()
                .statusCode(statusCode200)
                .body("id", equalTo(id),
                        "petId", equalTo(petId),
                        "quantity", equalTo(quantity),
                        "shipDate", containsString(shipDate),
                        "status", equalTo("placed"),
                        "complete", equalTo(complete));
    }

    @Test
    public void CadastrarNovoPedidoDePetComSucesso2() {
        Response response = given()
                .baseUri(UrlBase)
                .header("Content-type", "application/json")
                .and()
                .body("{\n" +
                        "  \"id\": 15131620,\n" +
                        "  \"petId\": 1428,\n" +
                        "  \"quantity\": 2,\n" +
                        "  \"shipDate\": \"2024-04-04T11:44:18.601Z\",\n" +
                        "  \"status\": \"placed\",\n" +
                        "  \"complete\": true\n" +
                        "}")
                .when()
                .post("/store/order")
                .then()
                .statusCode(statusCode200)
                .extract().response();
        assertEquals("15131620", response.jsonPath().getString("id"));
        assertEquals("1428", response.jsonPath().getString("petId"));
        assertEquals("2", response.jsonPath().getString("quantity"));
        assertEquals("2024-04-04T11:44:18.601+0000", response.jsonPath().getString("shipDate"));
        assertEquals("placed", response.jsonPath().getString("status"));
        assertEquals("true", response.jsonPath().getString("complete"));
    }

    JSONObject pet = new JSONObject();
    JSONObject category = new JSONObject();
    JSONObject tag1 = new JSONObject();
    JSONObject tag2 = new JSONObject();
    JSONArray photoUrls = new JSONArray();
    JSONArray tags = new JSONArray();

    public TestesDesafioTecnico(){
        pet.put("id", 99998);

        category.put("id", 99998);
        category.put("name", "felino");
        pet.put("category", category);

        pet.put("name", "Shepherd");

        photoUrls.add("http://fotosdegato.com.br/foto1.png");
        photoUrls.add("http://fotosdegato.com.br/foto2.png");
        pet.put("photoUrls", photoUrls);

        tag1.put("id", 99998);
        tag1.put("name", "Sem raça definida");
        tag2.put("id", 99999);
        tag2.put("name", "Amarelo");
        tags.add(tag1);
        tags.add(tag2);
        pet.put("tags", tags);

        pet.put("status", StatusAvailable);
    }

    @Test
    public void PesquisarPorPetInexistente(){
        int petId = 595751;

        given()
                .baseUri(UrlBase)
                .basePath("/pet/{petId}")
                .pathParam("petId", petId)
                .when()
                .get()
                .then()
                .statusCode(statusCode404)
                .body("type", equalTo(TypeErrorEsperado),
                        "message", equalTo(MenssagemPetNotFoundEsperada));
    }

    @Test
    public void AtualizarDadosDeUmPetExistente(){
        int petId = 88888;
        int categoryId = 77777;
        String categoryName = "felino atualizado";
        String name = "Teló atualizado";
        String photoURL = "https://blog-static.petlove.com.br/wp-content/uploads/2019/06/shutterstock_632318627.jpg";
        int tagId = 77777;
        String tagName = "persa atualizado";
        Pet pet = new Pet(petId,
                new Category(categoryId, categoryName),
                name,
                new String[]{photoURL},
                new Tag[]{new Tag(tagId, tagName)},
                statusPending);
        int tamanhoPhotoUrlsEsperado = 1;
        int tamanhoTagsEsperado = 1;

        given()
                .baseUri(UrlBase)
                .basePath("/pet")
                .header("content-type", "application/json")
                .body(pet)
                .when()
                .put()
                .then()
                .statusCode(statusCode200)
                .body("id", equalTo(petId),
                        "category.id", equalTo(categoryId),
                        "category.name", equalTo(categoryName),
                        "name", equalTo(name),
                        "photoUrls", hasSize(tamanhoPhotoUrlsEsperado),
                        "photoUrls[0]", equalTo(photoURL),
                        "tags", hasSize(tamanhoTagsEsperado),
                        "tags[0].id", equalTo(tagId),
                        "tags[0].name", equalTo(tagName),
                        "status", equalTo(statusPending));
    }

    @Test
    public void PesquisarPorPetsComStatusPending(){
        given()
                .baseUri(UrlBase)
                .basePath("/pet/findByStatus")
                .queryParam("status", statusPending)
                .when()
                .get()
                .then()
                .statusCode(statusCode200)
                .body("status", everyItem(equalToIgnoringCase(statusPending)));
    }

    @Test
    public void CadastrarNovoPetComSucesso(){
        given()
                .baseUri(UrlBase)
                .basePath("/pet")
                .header("content-type", "application/json")
                .body(pet)
                .when()
                .post()
                .then()
                .statusCode(statusCode200)
                .header("content-type", equalToIgnoringCase("Application/json"))
                .body("id", equalTo(99998),
                        "category.id", equalTo(99998),
                        "category.name", equalTo("felino"),
                        "name", equalTo("Shepherd"),
                        "photoUrls[0]", equalTo("http://fotosdegato.com.br/foto1.png"),
                        "photoUrls[1]", equalTo("http://fotosdegato.com.br/foto2.png"),
                        "tags[0].id", equalTo(99998),
                        "tags[0].name", equalTo("Sem raça definida"),
                        "tags[1].id", equalTo(99999),
                        "tags[1].name", equalTo("Amarelo"),
                        "status", equalTo(StatusAvailable));
    }
}
