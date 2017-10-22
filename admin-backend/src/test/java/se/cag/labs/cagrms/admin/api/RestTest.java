package se.cag.labs.cagrms.admin.api;


import io.restassured.RestAssured.*;
import io.restassured.matcher.RestAssuredMatchers.*;
import io.restassured.response.Response;
import org.junit.Test;


import static io.restassured.RestAssured.given;

/**
 * Created by andersenglesson on 2017-09-18.
 */
public class RestTest {
    private String token = "";

    @Test public void TestaPing(){
        Response response = given().header("Accept" ,"*/*").when().get("http://localhost:10580/ping").then().log().ifValidationFails().extract().response();
        System.out.println("svaret är :" + response.statusCode());
    }

    @Test
    public void TestLogin(){
        User user = new User();
        User responseUser = new User();
        user.displayName = "testsson";
        user.password = "testa";
        user.userId = "testsson";

        Response response = given().
                header("Content-Type", "application/json").
                header("Accept", "*/*").
                body(user).
                when().
                post("http://localhost:10580/login").
                then().
                extract().response();

        responseUser = response.as(User.class);

        System.out.println("userid: " + responseUser.userId + ", displayName: " + responseUser.displayName + ", password: " + responseUser.password);
        System.out.println("token: " + response.header("x-authtoken"));
        token = response.header("x-authtoken");

    }

    @Test
public void UserQueue(){
        User user = new User();
        user.userId = "testsson33";
        user.displayName = "Bad madda fakka";
        User res = new User();

        Response addQueue = given().
                //header("Content-Type", "application/json").
                header("Accept", "*/*").
                header("x-authtoken", token).
                body(user).
                when().
                post("http://localhost:10580/userqueue").
                then().
                log().ifValidationFails().
                extract().response();

        Response getQueue = given().
                //header("Content-Type", "application/json").
                        header("Accept", "*/*").
                        header("x-authtoken", token).
                        body(user).
                        when().
                        post("http://localhost:10580/userqueue").
                        then().
                        log().ifValidationFails().
                        extract().response();
        //res = getQueue.as(User.class);
        //System.out.println("bodyn är:  "+ res.toString());
    }
}
