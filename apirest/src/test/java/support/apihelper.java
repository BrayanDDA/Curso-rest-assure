package support;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class apihelper {

    public Response get(String url){
        Response response = given().get(url);
        return response;
    }

    public  Response post(String url,Object payload){
        Response response=given().body(payload).contentType("aplication/json").post(url);
        return response;
    }
    public  Response put(String url,Object payload){
        Response response=given().body(payload).contentType("aplication/json").put(url);
        return response;
    }
    public  Response patch(String url,Object payload){
        Response response=given().body(payload).contentType("aplication/json").patch(url);
        return response;
    }
}
