
package tqs.lab3.ex2;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.hamcrest.CoreMatchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import tqs.lab3.ex2.boundary.CarController;
import tqs.lab3.ex2.data.Car;
import tqs.lab3.ex2.service.CarManagerService;


@WebMvcTest(CarController.class)
public class CarControllerTest {



    @Autowired
    private MockMvc mvc;
    @MockBean
    private CarManagerService service;

    
    @BeforeEach
    public void setUp() throws Exception {
    }
    
    @Test
    void whenCreateCar_getCar() throws Exception {
        Car car = new Car("Barris", "Batmobile");

        when(service.save(Mockito.any())).thenReturn(car);
        mvc.perform(
                post("/api/car").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(car)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.model", is("Batmobile")));

        verify(service, times(1)).save(Mockito.any());
    }

}
