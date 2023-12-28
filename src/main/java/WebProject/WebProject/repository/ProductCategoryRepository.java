package WebProject.WebProject.repository;

import WebProject.WebProject.entity.Product;

import WebProject.WebProject.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {


    List<ProductCategory> findByProduct(Product product);
    List<ProductCategory> findByProductId(Integer id);
    ProductCategory findByProductIdAndCategory_Id(Integer idsp, Integer id);
}
