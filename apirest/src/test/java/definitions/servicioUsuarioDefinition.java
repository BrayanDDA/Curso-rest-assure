package definitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
import org.junit.Assert;
import support.requestUser;

import java.util.List;
import java.util.Map;

public class servicioUsuarioDefinition {

    requestUser user;

    public servicioUsuarioDefinition() {
        user=new requestUser();
    }

    @Given("Listar usuario")
    public void listarUsuario() {
        user.getUsers();
    }

    @When("mostrar el listado de usuarios")
    public void mostrarElListadoDeUsuarios() {
        ResponseBody body= requestUser.responseuser;
        System.out.print(body.asString());
    }

    @And("validar códigos de respuesta {string}")
    public void validarCodigosDeRespuesta(String codigo) {
        Assert.assertEquals(Integer.parseInt(codigo),requestUser.responseuser.getStatusCode());

    }

    @Then("validar número de registros")
    public void validarNumeroDeRegistros() {
        ResponseBody body= requestUser.responseuser;
        JsonPath json= new JsonPath((body.asString()));
        List<String> listado = JsonPath.with(body.asString()).get("data");
        int cantidad= json.getInt("per_page");
        Assert.assertEquals(cantidad,listado.size());
    }

    @Given("listar usuario con id {string}")
    public void listarUsuarioConId(String id) {
        user.getUser(id);
    }

    @When("mostrar información del usuario")
    public void mostrarInformaciónDelUsuario() {
        mostrarElListadoDeUsuarios();
    }

    @Then("validar informacion de la consulta")
    public void validarInformacionDeLaConsulta(DataTable user) {
        ResponseBody body=requestUser.responseuser;
        JsonPath json= new JsonPath((body.asString())).setRootPath("data");
        List<Map<String,String>> data = user.asMaps(String.class, String.class);
        for (int i=0;i <data.size(); i++){
            Assert.assertEquals(data.get(i).get("email"),json.getString("email"));
            Assert.assertEquals(data.get(i).get("nombre"),json.getString("first_name"));
            Assert.assertEquals(data.get(i).get("apellido"),json.getString("last_name"));

        }
    }

    @Given("que no existe usuario registrado")
    public void queNoExisteUsuarioRegistrado() {

    }

    @When("registrar datos del usuario")
    public void registrarDatosDelUsuario(DataTable dt) {
        List<Map<String, String>> data = dt.asMaps(String.class, String.class);
        for (int i = 0; i < data.size(); i++) {
            user.createUser(data.get(i).get("nombre"), data.get(i).get("puesto"));
            validarCodigosDeRespuesta(data.get(i).get("codigo"));
            mostrarElListadoDeUsuarios();
        }
    }

    @And("mostrar los datos del registro")
    public void mostrarLosDatosDelRegistro() {
        mostrarElListadoDeUsuarios();
    }

    @Given("que el usuario esté registrado")
    public void queElUsuarioEstéRegistrado() {

    }

    @When("actualizar datos del usuario")
    public void actualizarDatosDelUsuario(DataTable dt) {
        List<Map<String, String>> data = dt.asMaps(String.class, String.class);
        for (int i = 0; i < data.size(); i++) {
            user.updateUserPut(data.get(i).get("id"),data.get(i).get("nombre"), data.get(i).get("puesto"));
            validarCodigosDeRespuesta(data.get(i).get("codigo"));
            mostrarElListadoDeUsuarios();
        }
    }
}
