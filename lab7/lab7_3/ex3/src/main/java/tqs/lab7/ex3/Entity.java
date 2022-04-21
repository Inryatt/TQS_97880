package tqs.lab7.ex3;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name="Entity")
public class Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long entityId;

    @Value("thing")
    private String thing;
    @Value("other")
    private String other;

    public Entity(String thing, String other) {
        this.thing = thing;
        this.other = other;
    }

    public Entity() {

    }

    public String getThing() {
        return thing;
    }

    public void setThing(String thing) {
        this.thing = thing;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
