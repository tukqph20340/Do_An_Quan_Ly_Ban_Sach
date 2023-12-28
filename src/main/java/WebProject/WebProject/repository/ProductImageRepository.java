package WebProject.WebProject.repository;

import WebProject.WebProject.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import WebProject.WebProject.entity.ProductImage;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage,Integer>{
	List<ProductImage> findByProduct(Product id);
	List<ProductImage> findByProductId(Integer id);



}
