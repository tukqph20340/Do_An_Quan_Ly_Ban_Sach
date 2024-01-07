package WebProject.WebProject.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import WebProject.WebProject.entity.Product;
/**
 * @author HOAN HAO
 *
 */
@Repository
public interface ProductRepository extends JpaRepository<Product,Long>{
	
	@Query(value="select * from product p where p.product_name like %?1%",nativeQuery = true)
	List<Product> findByProduct_NameContaining(String name);

	@Query(value = "SELECT * FROM\n" +
			"    product p\n" +
			"INNER JOIN product_author pa ON p.id = pa.product_id\n" +
			"INNER JOIN author a ON pa.author_id = a.id\n" +
			"INNER JOIN product_category pc ON p.id = pc.product_id\n" +
			"INNER JOIN category c ON pc.category_id = c.id group by p.id", nativeQuery = true)
	List<Product> getAllProduct();

	@Query(value = "SELECT p.*, a.*, c.* FROM product p " +
			"INNER JOIN product_author pa ON p.id = pa.product_id " +
			"INNER JOIN author a ON pa.author_id = a.id " +
			"INNER JOIN product_category pc ON p.id = pc.product_id " +
			"INNER JOIN category c ON pc.category_id = c.id " +
			"GROUP BY p.id", nativeQuery = true)
	Page<Product> findAllWithJoins(Pageable pageable);


	@Query(value="Select * From product p ORDER BY p.quantity DESC LIMIT 12;",nativeQuery = true)
	List<Product> findTop12ProductBestSellers();
	
	@Query(value="Select * From product p ORDER BY p.created_at DESC LIMIT 12;",nativeQuery = true)
	List<Product> findTop12ProductNewArrivals();
	
	Page<Product> findAllByCategory_id(int id, Pageable pageable);

	Page<Product> findAllByAuthorId(int id, Pageable pageable);

	Page<Product> findAllByProducerId(int id, Pageable pageable);

	Page<Product> findAllByPriceBetween(Pageable pageable,long giaThapNhat ,long giaCaoNhat);


	@Query(value="select * from product where  product.price >= 500000",nativeQuery = true)
	Page<Product> findByPrice(Pageable pageable);
	
	Product findById(int id);
	
	@Query(value="select * from product where product.product_name like %?1% and product.category_id= ?2",nativeQuery = true)
	Page<Product> findByProduct_NameAndCategory_idContaining(String name, int category_id, Pageable pageable);
	
	@Query(value="select * from product where product.product_name like %?1%",nativeQuery = true)
	Page<Product> findByProduct_NameContaining(String name, Pageable pageable);
	
	@Query(value="select * from product p where p.category_id = ?1 ORDER BY p.sold DESC LIMIT 4;",nativeQuery = true)
	List<Product> findTop4ProductByCategory_id(int id);


	@Query("SELECT c from Product c WHERE c.product_Name LIKE %?1%")
	Page<Product> findByProduct_Name(Pageable p, String name);


	@Query("SELECT c FROM Product c WHERE c.price >=:price")
	Page<Product> findByPriceBefore(@Param("price")long gia, Pageable pageable);



	@Query("SELECT c FROM Product c WHERE c.price <=:price")
	Page<Product> findByPriceAfter(@Param("price") long gia, Pageable pageable);










}
