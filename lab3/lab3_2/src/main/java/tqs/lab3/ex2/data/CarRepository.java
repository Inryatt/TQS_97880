package tqs.lab3.ex2.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car,Long> {
    Car findByCarId(Long id);
    
    List<Car> findAll();
}
