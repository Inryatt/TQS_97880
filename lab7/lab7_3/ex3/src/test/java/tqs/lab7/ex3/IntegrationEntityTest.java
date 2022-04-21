package tqs.lab7.ex3;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest
public class IntegrationEntityTest {


    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer("postgres:11.1")
            .withUsername("yes")
            .withPassword("password")
            .withDatabaseName("test");

    @Autowired
    private EntityRepository entityRepository;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @Test
    void contextLoads() {

        Entity entity = new Entity();
        entity.setOther("characteristic");

        entityRepository.save(entity);
        System.out.println("Context loads!");
    }

    @Test
    @Order(1)
    public void insertContent(){
        int size= entityRepository.findAll().size();
        entityRepository.save(new Entity("specific","ability"));
        int size2= entityRepository.findAll().size();


        assertEquals(size + 1, size2);
    }

}


