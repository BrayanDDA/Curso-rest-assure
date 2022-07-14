package definitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
import org.junit.Assert;
import support.requestResource;

import java.util.List;
import java.util.Map;

public class servicioRecursoDefinition {

    requestResource resources;

    public servicioRecursoDefinition() {
        resources = new requestResource();
    }

    @Given("Listar recursos")
    public void listarRecursos() {
        resources.getResources();
    }

    @When("mostrar el listado de recursos")
    public void mostrarElListadoDeRecursos() {
        ResponseBody body=requestResource.responseResource;
        System.out.print(body.asString());
    }

    @And("validar código de respuesta {string}")
    public void validarCódigoDeRespuesta(String codigo) {
        Assert.assertEquals(Integer.parseInt(codigo), requestResource.responseResource.getStatusCode());

    }

    @Then("validar número de recursos")
    public void validarNúmeroDeRecursos() {
        ResponseBody body= requestResource.responseResource;
        JsonPath json= new JsonPath((body.asString()));
        List<String> listado = json.with(body.asString()).get("data");
        int cantidad= json.getInt("per_page");
        Assert.assertEquals(cantidad,listado.size());
    }

    @Given("listar recurso con id {string}")
    public void listarRecursoConId(String id) {
        resources.getResource(id);
    }

    @When("mostrar información del recurso")
    public void mostrarInformaciónDelRecurso() {
        mostrarElListadoDeRecursos();
    }

    @Then("validar informacion de la consulta del recurso")
    public void validarInformacionDeLaConsultaDelRecurso(DataTable recurso) {
        ResponseBody body = requestResource.responseResource;
        JsonPath json = new JsonPath((body.asString())).setRootPath("data");
        List<Map<String, String>> data = recurso.asMaps(String.class, String.class);
        for (int i = 0; i < data.size(); i++) {
            Assert.assertEquals(data.get(i).get("nombre"), json.getString("name"));
            Assert.assertEquals(data.get(i).get("año"), json.getString("year"));
            Assert.assertEquals(data.get(i).get("color"), json.getString("color"));
            Assert.assertEquals(data.get(i).get("pantone"), json.getString("pantone_value"));

        }
    }
}
