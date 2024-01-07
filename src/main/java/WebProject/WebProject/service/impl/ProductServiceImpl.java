package WebProject.WebProject.service.impl;

import WebProject.WebProject.entity.Product;
import WebProject.WebProject.repository.ProductRepository;
import WebProject.WebProject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;

	@Override
	public List<Product> getAllProduct() {
		// TODO Auto-generated method stub
		return productRepository.getAllProduct();
	}

	@Override
	public Product saveProduct(Product product) {
		// TODO Auto-generated method stub
		return productRepository.save(product);
	}

	@Override
	public Product getProductById(int id) {
		// TODO Auto-generated method stub
		return productRepository.findById(id);
	}

	@Override
	public Product updateProduct(Product product) {
		// TODO Auto-generated method stub
		return productRepository.save(product);
	}



	@Override
	public void deleteProductById(int id) {
		// TODO Auto-generated method stub
		productRepository.deleteById((long) id);
	}

	@Override
	public List<Product> findByProduct_NameContaining(String name) {
		// TODO Auto-generated method stub
		return productRepository.findByProduct_NameContaining(name);
	}

	@Override
	public List<Product> findTop12ProductBestSellers() {
		// TODO Auto-generated method stub
		return productRepository.findTop12ProductBestSellers();
	}

	@Override
	public List<Product> findTop12ProductNewArrivals() {
		// TODO Auto-generated method stub
		return productRepository.findTop12ProductNewArrivals();
	}

	@Override
	public Page<Product> findAll(Integer noPage, Integer size) {
		Pageable p = PageRequest.of(noPage,size);
		return productRepository.findAll(p);
	}


	@Override
	public Page<Product> findByProduct_NameAndCategory_idContaining(String name, int category_id, Pageable pageable) {
		return productRepository.findByProduct_NameAndCategory_idContaining(name, category_id, pageable);
	}

	@Override
	public Page<Product> findByProduct_NameContaining(String name, Pageable pageable) {
		return productRepository.findByProduct_NameContaining(name, pageable);
	}

	@Override
	public List<Product> findTop4ProductByCategory_id(int id) {
		return productRepository.findTop4ProductByCategory_id(id);
	}

	@Override
	public Page<Product> timKiemTen(String name, Integer noPage, Integer size) {
		Pageable pageable = PageRequest.of(noPage,size);
		return productRepository.findByProduct_Name(pageable,name);
	}

	@Override
	public Page<Product> timKiemTheoGia(Long a, Long b, Integer noPage, Integer size) {
		Pageable pageable = PageRequest.of(noPage,size);
		return productRepository.findAllByPriceBetween(pageable,a,b);
	}

	@Override
	public Page<Product> timKiemlonHon(Long a, Integer noPage, Integer size) {
		Pageable pageable = PageRequest.of(noPage,size);
		return productRepository.findByPriceBefore(a,pageable);
	}

	@Override
	public Page<Product> timKiemnhoHon(Long b, Integer noPage, Integer size) {
		Pageable pageable = PageRequest.of(noPage,size);
		return productRepository.findByPriceAfter(b,pageable);
	}

}
