package tqs.lab7.ex2;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import tqs.lab7.ex2.boundary.CarController;
import tqs.lab7.ex2.data.Car;
import tqs.lab7.ex2.service.CarManagerService;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

@WebMvcTest(CarController.class)
public class Lab7CarControllerTest {
    Car car = new Car("Barris", "Batmobile");

    @Autowired
    private MockMvc mvc;
      @MockBean
      CarManagerService service;

      @BeforeEach
      void setUp(){
          RestAssuredMockMvc.mockMvc(mvc);
      }

    @Test
    void whenValidInput_thenCreateCar() throws Exception {

        RestAssuredMockMvc.given().
                contentType(ContentType.JSON).
                body(JsonUtils.toJson(car)).
                when().
                post("/api/car").
                then().
                statusCode(201);

    }

    @Test
    void whenGetCar_GetCar(){
        Mockito.when(service.getAllCars()).thenReturn(List.of(car));

        RestAssuredMockMvc.given().get("api/cars").
                then().statusCode(200).
                and().
                contentType(ContentType.JSON).
                body("[0].maker", equalTo("Barris")).
                body("[0].model", equalTo("Batmobile"));
    }
}
