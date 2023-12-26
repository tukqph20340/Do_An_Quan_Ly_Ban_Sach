package WebProject.WebProject.repository;

import WebProject.WebProject.entity.Author;
import WebProject.WebProject.entity.Producer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Integer> {

    Producer findById(int id);

    @Query("SELECT c from Producer c WHERE c.nameProducer LIKE %?1%")
    Page<Producer> findAllByNameProducer(Pageable p, String ten);

}
