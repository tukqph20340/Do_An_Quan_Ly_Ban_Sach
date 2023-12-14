package WebProject.WebProject.repository;

import WebProject.WebProject.entity.ReturnsImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReturImageRepository extends JpaRepository<ReturnsImage,Integer> {
    List<ReturnsImage> findByReturnsId(Integer id);
}
