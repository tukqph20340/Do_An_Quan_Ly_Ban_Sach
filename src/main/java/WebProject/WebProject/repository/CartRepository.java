package WebProject.WebProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import WebProject.WebProject.entity.Cart;

public interface CartRepository extends JpaRepository<Cart,Integer>{

//	@Query(value="DELETE FROM `cart` e WHERE e.id= ?1",nativeQuery = true)
//	void deleteById(int id);


	@Query(value = "SELECT * from cart c WHERE c.is_selected = true and (user_id=?1 or (user_id is null and ?1 is null))",nativeQuery = true)
	List<Cart> getListCartSelected(String user_id);

	@Query(value = "SELECT * FROM cart WHERE user_id = ?1 or (user_id is null and ?1 is null)", nativeQuery = true)
	List<Cart> findAllByUser_id(String user_id);


}
