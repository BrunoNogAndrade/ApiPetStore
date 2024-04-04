package Testes;

import JsonObjects.Category;
import JsonObjects.Pet;
import JsonObjects.Tag;
import JsonObjects.User;
import Utils.Utils;
import Base.TestBase;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.Iterator;
import static Utils.Constants.UrlBase;
import static Utils.Constants.statusCode200;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class TestesComDataDriven extends TestBase {
    public TestesComDataDriven() {
        enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @DataProvider(name = "dataPetObjectPrvider")
    public Object[] dataPetProvider() {
        Pet pet1 = new Pet(11111,
                new Category(11111, "felino"),
                "Shepherd",
                new String[]{"http://photogato.com/foto1"},
                new Tag[]{new Tag(11111, "Amarelo")},
                "Available");

        Pet pet2 = new Pet(22222,
                new Category(22222, "catioro"),
                "Auau",
                new String[]{"http://photocatioro.com/foto1"},
                new Tag[]{new Tag(22222, "Caramelo")},
                "Pending");

        Pet pet3 = new Pet(33333,
                new Category(33333, "ave"),
                "Fenix",
                new String[]{"http://photoave.com/foto1"},
                new Tag[]{new Tag(33333, "Azul")},
                "Available");

        return new Pet[]{pet1, pet2, pet3};
    }

    @DataProvider(name = "petDataProviderCSV")
    public Iterator<Object[]> petDataProviderCSV() {
        return Utils.csvProvider("src/test/java/org/example/petDataCSV.csv");
    }

    @Test(dataProvider = "dataPetObjectPrvider")
    public void CadastrarPetComSucessoDataDriven(Pet petData) {
        given()
                .baseUri(UrlBase)
                .basePath("/pet")
                .header("content-type", "application/json")
                .body(petData)
                .when()
                .post()
                .then()
                .statusCode(statusCode200)
                .body("id", equalTo(petData.getId()),
                        "category.id", equalTo(petData.getCategory().getId()),
                        "category.name", equalTo(petData.getCategory().getName()),
                        "name", equalTo(petData.getName()),
                        "photoUrls[0]", equalTo(petData.getPhotoUrls()[0]),
                        "tags[0].id", equalTo(petData.getTags()[0].getId()),
                        "tags[0].name", equalTo(petData.getTags()[0].getName()),
                        "status", equalTo(petData.getStatus()));
    }

    //Cadastro de Usuarios
    @DataProvider(name = "dataUsertObjectProvider")
    public Object[]
    dataUsuarioProvider() {
        User Usuario1 = new User(
                01, "Bruno", "andrade", "nogueira",
                "bruno.andrade@gmail.com.br", "senha", "37991412535", 1
        );

        User Usuario2 = new User(02, "Bruno2", "andrade2", "nogueira2",
                "bruno.andrade2@gmail.com.br", "senha2", "31991412535", 1

        );

        User Usuario3 = new User(03,
                "Bruno3","andrade3","nogueira3","bruno.andrade3@gmail.com.br",
                "senha3", "35991412535", 1

        );

        return new User[]{Usuario1, Usuario2, Usuario3};
    }

    @Test(dataProvider = "dataUsertObjectProvider")
    public void CadastroUsuariosDataDriven(User novoUsuario) {
        given()
                .baseUri("https://petstore.swagger.io/v2")
                .basePath("/user")
                .header("content-type", "application/json")
                .body(novoUsuario)
                .when()
                .post()
                .then()
                .statusCode(statusCode200);
    }







}
