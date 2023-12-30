package WebProject.WebProject.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import WebProject.WebProject.entity.Product;

public interface ProductService {
	List<Product> getAllProduct();
	
	Product saveProduct(Product product);

	Product getProductById(int id);

	Product updateProduct(Product product);


	void deleteProductById(int id);
	
	List<Product> findByProduct_NameContaining(String name);
	
	List<Product> findTop12ProductBestSellers();
	
	List<Product> findTop12ProductNewArrivals();

	Page<Product> findAll(Integer noPage, Integer size);

	Page<Product> findByProduct_NameContaining(String name, Pageable pageable);

	Page<Product> findByProduct_NameAndCategory_idContaining(String name, int category_id, Pageable pageable);

	List<Product> findTop4ProductByCategory_id(int name);


	Page<Product> timKiemTen(String name, Integer noPage, Integer size);

	Page<Product> timKiemTheoGia(Long a ,Long b, Integer noPage, Integer size);

	Page<Product> timKiemlonHon(Long a, Integer noPage, Integer size);

	Page<Product> timKiemnhoHon(Long b, Integer noPage, Integer size);
}
