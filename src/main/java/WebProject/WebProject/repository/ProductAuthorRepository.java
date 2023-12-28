package WebProject.WebProject.repository;

import WebProject.WebProject.entity.ProductAuthor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductAuthorRepository extends JpaRepository<ProductAuthor, Long> {
    List<ProductAuthor> findByProductId(Integer id);
    ProductAuthor findByProductIdAndAuthor_Id(Integer idsp,Integer id);

}
