package tqs.lab7.ex1;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

@WebMvcTest()
public class JsonPlaceholderTest {
    @Autowired
    private MockMvc mvc;


    @BeforeEach
    void setUp(){
        RestAssuredMockMvc.mockMvc(mvc);
    }

    @Test
    void testEndpoint_isAvailable(){
        RestAssuredMockMvc.given().
        when().
                get("https://jsonplaceholder.typicode.com/todos").
                then().
                statusCode(200);
    }

    @Test
    void queryTODO4_GetsCorrectTitle(){
        RestAssuredMockMvc.given().
        when().
                get("https://jsonplaceholder.typicode.com/todos/4").
                then().
                statusCode(200).and().body("title",equalTo("et porro tempora"));
    }

    @Test
    void doigeticorrectids(){
        RestAssuredMockMvc.given().
                when().
                get("https://jsonplaceholder.typicode.com/todos").
                then().
                statusCode(200).and().body(".id", Matchers.contains(198,199));
    }
}
