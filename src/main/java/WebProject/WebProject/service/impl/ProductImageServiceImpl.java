package WebProject.WebProject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import WebProject.WebProject.entity.ProductImage;
import WebProject.WebProject.repository.ProductImageRepository;
import WebProject.WebProject.service.ProductImageService;

import java.util.List;

@Service
public class ProductImageServiceImpl implements ProductImageService{
	@Autowired
	ProductImageRepository productImageRepository;

	@Override
	public void save(ProductImage productImage) {
		productImageRepository.save(productImage);
	}

	@Override
	public void deleteById(ProductImage id) {
		productImageRepository.delete(id);
	}

	@Override
	public List<ProductImage> finalIdSP(Integer id) {
		return productImageRepository.findByProductId(id);
	}

	@Override
	public List<ProductImage> findById(int id) {
		return productImageRepository.findByProduct(id);
	}

	@Override
	public void deleteImg(Integer id) {
		productImageRepository.deleteById(id);
	}


}
