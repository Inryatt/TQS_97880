package tqs.lab7.ex2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import tqs.lab7.ex2.data.Car;
import tqs.lab7.ex2.data.CarRepository;
import tqs.lab7.ex2.service.CarManagerService;


@ExtendWith(MockitoExtension.class)
public class CarServiceTest {
    static Long id = 123L;
    @Mock( lenient = true)
    private CarRepository carRepository;
    
    @InjectMocks
    private CarManagerService carManagerService;

    @BeforeEach
    public void setUp() {
        Car bat = new Car("Barris","Batmobile");
        bat.setCarId(id);
        Car teslatruck = new Car("Tesla","Truck");
        teslatruck.setCarId(321L);
        Car bttf = new Car("DMC","DeLorean");

        List<Car> allCars = Arrays.asList(bat,bttf,teslatruck);
        

        Mockito.when(carRepository.findByCarId(id)).thenReturn(bat);
        Mockito.when(carRepository.findByCarId(321L)).thenReturn(teslatruck);
        Mockito.when(carRepository.findByCarId(-123L)).thenReturn(null);

        Mockito.when(carRepository.findAll()).thenReturn(allCars);

    }

    @Test
    void whenSearchValidId_FindCar(){
        Car foundCar = carManagerService.getCarDetails(id).orElse(null);

        assertThat(foundCar.getCarId()).isEqualTo(id);
        Mockito.verify(carRepository, times(1)).findByCarId(Mockito.anyLong());
    }

    @Test
    void whenSearchInvalidId_FindNothing(){
        Long badId = -123L;
        Car foundCar = carManagerService.getCarDetails(badId).orElse(null);

        assertThat(foundCar).isEqualTo(null);
    }
    
    @Test
    void whenGetAllCars_GetAllCars(){
        List<Car> allcar = carManagerService.getAllCars();

        Car bat = new Car("Barris","Batmobile");
        bat.setCarId(123L);
        Car teslatruck = new Car("Tesla","Truck");
        teslatruck.setCarId(321L);
        Car bttf = new Car("DMC","DeLorean");

        List<Car> allCars = Arrays.asList(bat,bttf,teslatruck);
        
        assertThat(allCars).hasSize(3).extracting(Car::getModel).containsOnly(bat.getModel(),teslatruck.getModel(),bttf.getModel());

        Mockito.verify(carRepository, times(1)).findAll();

    }

}
