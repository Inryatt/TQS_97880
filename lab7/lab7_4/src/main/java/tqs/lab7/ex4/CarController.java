package tqs.lab7.ex4;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import tqs.lab7.ex4.Car;
import tqs.lab7.ex4.CarManagerService;


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
    ResponseEntity<Car> createCar(@RequestBody Car car){
        Car saved = carManagerService.save(car);
        return new ResponseEntity<>(saved,HttpStatus.CREATED);
    }
    
    @GetMapping("/cars")
    List<Car>  getAllCars(){return carManagerService.getAllCars();}


    ResponseEntity<Car> getCarById(Long num){
        Car car = carManagerService.getCarDetails(num).orElse(null);
        return new ResponseEntity<>(car,HttpStatus.OK);
    }
    
}
