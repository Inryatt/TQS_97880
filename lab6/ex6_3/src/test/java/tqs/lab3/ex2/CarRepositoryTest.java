package tqs.lab3.ex2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import tqs.lab3.ex2.data.Car;
import tqs.lab3.ex2.data.CarRepository;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;


@DataJpaTest
public class CarRepositoryTest {
    @Autowired
    private TestEntityManager entityManager; // the friendliest

    @Autowired
    private CarRepository carRepository;

    @Test
    void whenFindBatByValidId_returnBat(){
        Car bat = new Car("Barris","Batmobile");
        entityManager.persistAndFlush(bat);
        System.err.println(carRepository.findAll().toString());

        Car found = carRepository.findByCarId(bat.getCarId());
        assertThat(found).isEqualTo(bat);
    }

    @Test
    void whenFindByInvalidId_returnNull(){
        Car bat = new Car("Barris","Batmobile");
        entityManager.persistAndFlush(bat);

        Car found = carRepository.findByCarId(12L);
        assertThat(found).isEqualTo(null);
    }
    @Test
    void whenFindAll_thenReturnAllCars(){
        Car bat = new Car("Barris","Batmobile");
        Car teslatruck = new Car("Tesla","Truck");
        Car bttf = new Car("DMC","DeLorean");


        entityManager.persist(bat);
        entityManager.persist(teslatruck);
        entityManager.persist(bttf);
        List<Car> allCars = carRepository.findAll();

        assertThat(allCars).hasSize(3).extracting(Car::getModel).containsOnly(bat.getModel(),teslatruck.getModel(),bttf.getModel());
    }
}
