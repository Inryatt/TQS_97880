package tqs.lab7.ex4;

import javax.persistence.*;

import org.springframework.beans.factory.annotation.Value;

@Entity
@Table(name= "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long carId;

    @Value("maker")
    private String maker;
    @Value("model")
    private String model;

    public Car(){};
    public Car(String maker, String model){
        this.maker=maker;
        this.model=model;
    };
    
   


    public Long getCarId() {
        return this.carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getMaker() {
        return this.maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }
    
    @Override
    public String toString(){
        return this.carId+" : "+ this.model +", "+this.maker;
    }
}
