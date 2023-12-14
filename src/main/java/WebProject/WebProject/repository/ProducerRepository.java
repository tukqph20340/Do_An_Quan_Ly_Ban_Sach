package WebProject.WebProject.repository;

import WebProject.WebProject.entity.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Integer> {

    Producer findById(int id);

}
