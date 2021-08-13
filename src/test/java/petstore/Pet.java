
package petstore;


import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

public class Pet {
    String uri = "https://petstore.swagger.io/v2/pet"; //Endereço da entidade Pet

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    // Incluir - Create - Post
    @Test(priority = 1) // Identifica o método ou função com oum teste par o TestNG
    public void incluirPet() throws IOException {
        String jsonBody = lerJson("db/pet1.json");

        // Sintaxe Gherkin
        // Dado - Quando - Então
        // Given - When - Then

        given() // Dado
                .contentType("application/json") // Comum em API REST - antigas eram "text/xml"
                .log().all()
                .body(jsonBody)
        .when() // Quando
                .post(uri)
        .then() // Então
                .log().all()
                .statusCode(200)
                .body("name",is("Snoopy")) // Compara tags dentro do corpo do Json do Given e do Then
                .body("status",is("available"))
                .body("category.name",is("AX1515LOTR"))
        ;
    }

    @Test(priority = 2)
    public void consultarPet(){

        String petId = "161744";
        String token=
        given() // Dado
                .contentType("application/json") // Comum em API REST - antigas eram "text/xml"
                .log().all()
                .when() // Quando
                .get(uri+"/"+petId)
                .then() // Então
                .log().all()
                .statusCode(200)
                .body("name",is("Snoopy")) // Compara tags dentro do corpo do Json do Given e do Then
                .body("status",is("available"))
                .body("category.name",is("AX1515LOTR"))
        .extract()
                .path("category.name")
        ;
        System.out.println("O token é: " + token);
    }

    @Test(priority = 3) // Identifica o método ou função com oum teste par o TestNG
    public void alterarPet() throws IOException {
        String jsonBody = lerJson("db/pet2.json");

        given() // Dado
                .contentType("application/json") // Comum em API REST - antigas eram "text/xml"
                .log().all()
                .body(jsonBody)
        .when() // Quando
                .put(uri)
        .then() // Então
                .log().all()
                .statusCode(200)
                .body("status",is("sold"))
        ;
    }


}
