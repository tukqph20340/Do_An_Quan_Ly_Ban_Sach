package WebProject.WebProject.service;

import WebProject.WebProject.entity.ProductImage;

import java.util.List;

public interface ProductImageService {

	void save(ProductImage productImage);

	void deleteById(ProductImage id);

	List<ProductImage> finalIdSP(Integer id);

	List<ProductImage> findById(int id);

	void deleteImg(ProductImage productImage);

}
