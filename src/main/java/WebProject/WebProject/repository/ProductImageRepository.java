package WebProject.WebProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import WebProject.WebProject.entity.ProductImage;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage,Integer>{
	List<ProductImage> findByProduct(int id);
	List<ProductImage> findByProductId(Integer id);


}
