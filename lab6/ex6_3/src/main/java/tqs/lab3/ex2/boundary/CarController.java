package tqs.lab3.ex2.boundary;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import tqs.lab3.ex2.data.Car;
import tqs.lab3.ex2.data.CarDTO;
import tqs.lab3.ex2.service.CarManagerService;



@RestController
@RequestMapping("/api")
public class CarController {
    private final CarManagerService carManagerService;


    /**
    @param CarManagerService
    */
    public CarController(CarManagerService carManagerService){
        this.carManagerService=carManagerService;
    }
    
    @PostMapping("/car" )
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<Car> createCar(@RequestBody CarDTO car){
        Car persistentCar = new Car(car.getMaker(),car.getModel());
        Car saved = carManagerService.save(persistentCar);
        return new ResponseEntity<>(saved,HttpStatus.CREATED);
    }
    
    @GetMapping("/cars")
    List<Car>  getAllCars(){return carManagerService.getAllCars();}


    ResponseEntity<Car> getCarById(Long num){
        Car car = carManagerService.getCarDetails(num).orElse(null);
        return new ResponseEntity<>(car,HttpStatus.OK);
    }
    
}
