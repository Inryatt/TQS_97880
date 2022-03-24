package tqs.lab3.ex2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import tqs.lab3.ex2.data.Car;
import tqs.lab3.ex2.data.CarRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)

@AutoConfigureMockMvc
//@AutoConfigureTestDatabase
@TestPropertySource(locations="application-integrationtest.properties")
public class APITestIT {
    
    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarRepository repository;

    @AfterEach
    public void resetRepository(){
        repository.deleteAll();
    }

    @Test
    void whenValidInput_thenCreateCar(){
        Car bat = new Car("Carris","Batmobile");
        
        ResponseEntity<Car> entity = restTemplate.postForEntity("/api/car", bat, Car.class);
        
        List<Car> found = repository.findAll();
        assertThat(found).extracting(Car::getModel).containsOnly("Batmobile");
    }

    @Test
    void givenCar_whenGetCar_thereIsCar(){
        gibCar("Barris", "Batmobile");
        gibCar("CarMaker","TotallyNotABike");
        gibCar("TrucksTrucksTrucks","TRUCK");

        ResponseEntity<List<Car>> response = restTemplate
                        .exchange("/api/cars", HttpMethod.GET, null, new ParameterizedTypeReference<List<Car>>(){});
    
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).extracting(Car::getModel).containsExactly("Batmobile","TotallyNotABike","TRUCK");

    }



    private void gibCar(String maker,String model){
        Car car = new Car(maker,model);
        repository.saveAndFlush(car);
    }
}
