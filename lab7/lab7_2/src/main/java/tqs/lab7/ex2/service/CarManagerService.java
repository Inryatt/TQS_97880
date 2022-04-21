package tqs.lab7.ex2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tqs.lab7.ex2.data.Car;
import tqs.lab7.ex2.data.CarRepository;

@Service
public class CarManagerService {
    @Autowired
    private CarRepository carRepository;
    
    public Car save(Car car){
        return carRepository.save(car);
    };

    public List<Car>  getAllCars(){
        return carRepository.findAll();
    }

    public Optional<Car> getCarDetails(Long num){
        return Optional.ofNullable(carRepository.findByCarId(num));
    }

}
