package WebProject.WebProject.repository;

import WebProject.WebProject.entity.Returns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnsRepository extends JpaRepository<Returns,Integer> {
    Returns findByOrder_Id(Integer id);
}
