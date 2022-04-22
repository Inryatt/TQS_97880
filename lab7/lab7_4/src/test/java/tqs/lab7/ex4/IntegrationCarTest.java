package tqs.lab7.ex4;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.springframework.http.ResponseEntity;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.PostgreSQLContainer;

import org.junit.jupiter.api.*;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.DynamicPropertyRegistry;

import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;


import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=create")
@AutoConfigureMockMvc
public class IntegrationCarTest {

    @Container
    public static PostgreSQLContainer container =  new PostgreSQLContainer("postgres:12")
            .withUsername("yes")
            .withPassword("password")
            .withDatabaseName("test");
          // .withInitScript("db/migration/V001__INIT.sql");
    @LocalServerPort
    int localPortForTestServer;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private MockMvc mvc;
    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @BeforeEach
    void setUp(){
        RestAssuredMockMvc.mockMvc(mvc);
    }

    @Test
    @Order(1)
    void whenValidInput_thenCreateCar() throws Exception {
        Car bat = new Car("Carris","Batmobile");
        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("127.0.0.1")
                .port(localPortForTestServer)
                .pathSegment("api", "car" )
                .build()
                .toUriString();

        mvc.perform(post(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJson(bat)))
                .andExpect(status().isCreated());


        //ResponseEntity<Car> entity = restTemplate.postForEntity("/api/car", bat, Car.class);

        //List<Car> found = carRepository.findAll();
        //assertThat(found).extracting(Car::getModel).containsOnly("Batmobile");
    }

    @Test
    @Order(2)
    void givenCar_whenGetCar_thereIsCar(){

        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("127.0.0.1")
                .pathSegment("api", "cars" )
                .port(localPortForTestServer)
                .build()
                .toUriString();

        gibCar("CarMaker","TotallyNotABike");
        gibCar("TrucksTrucksTrucks","TRUCK");
        gibCar("Barris","Batmobile");


       // mvc.perform(get(endpoint)
       //         .contentType(MediaType.APPLICATION_JSON)
       //         .content(JsonUtils.toJson(bat))).andExpect(status().isCreated());


        RestAssuredMockMvc.get(endpoint).
                then().statusCode(200).
                and().
                contentType(ContentType.JSON).
                body("model",hasItems("Batmobile","TotallyNotABike","TRUCK"));

    }

    private void gibCar(String maker,String model){
        Car car = new Car(maker,model);
        carRepository.saveAndFlush(car);
    }

}