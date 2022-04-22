package tqs.lab7.ex3;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityRepository extends JpaRepository<Entity,Long> {
    Entity findByEntityId(Long id);

    List<Entity> findAll();
}
