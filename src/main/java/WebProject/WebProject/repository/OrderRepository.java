package WebProject.WebProject.repository;

import java.util.List;
import java.util.Optional;

import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import WebProject.WebProject.entity.Order;
import WebProject.WebProject.entity.Product;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order,Integer>{

	@Query(value = "SELECT * FROM `order` WHERE active_order_id = '1' AND user_id = :user_id", nativeQuery = true)
	List<Order> findAllByUser_id(@Param("user_id") String user_id);

	@Query(value = "select * from `order` WHere active_order_id = '2'AND user_id = :user_id ",nativeQuery = true)
	List<Order> findAllByUser_idAndActiveOrder2(@Param("user_id") String user_id);

	@Query(value = "select * from `order` WHere active_order_id = '3'AND user_id = :user_id ",nativeQuery = true)
	List<Order> findAllByUser_idAndActiveOrder3(@Param("user_id") String user_id);

	@Query(value = "select * from `order` WHere active_order_id = '4'AND user_id = :user_id ",nativeQuery = true)
	List<Order> findAllByUser_idAndActiveOrder4(@Param("user_id") String user_id);

	@Query(value = "select * from `order` WHere active_order_id = '5'AND user_id = :user_id ",nativeQuery = true)
	List<Order> findAllByUser_idAndActiveOrder5(@Param("user_id") String user_id);

	@Query(value = "select * from `order` WHere active_order_id = '6'AND user_id = :user_id ",nativeQuery = true)
	List<Order> findAllByUser_idAndActiveOrder6(@Param("user_id") String user_id);

	@Query(value = "select * from `order` WHere active_order_id = '7'AND user_id = :user_id ",nativeQuery = true)
	List<Order> findAllByUser_idAndActiveOrder7(@Param("user_id") String user_id);

	@Query(value = "select * from `order` WHere active_order_id BETWEEN  '8' AND '10' AND user_id = :user_id ",nativeQuery = true)
	List<Order> findAllByUser_idAndActiveOrder(@Param("user_id") String user_id);

	Order findById(int id);

	List<Order> findOrderById(int id);

	@Query(value="Select * From `order` o ORDER BY o.id DESC LIMIT 5;",nativeQuery = true)
	List<Order> findTop5RecentOrder();

	@Query(value="Select distinct o.user_id From `order` o ORDER BY o.id DESC LIMIT 5;",nativeQuery = true)
	List<String> findTop5RecentCustomer();

	Page<Order> findAll(Pageable pageable);

	Page<Order> findByActiveOrder_Id(Pageable pageable,String id);

	List<Order> findByActiveOrder_Id(String id);

	@Query(value = "select * from `order` WHere active_order_id BETWEEN  '8' AND '10'",nativeQuery = true)
	Page<Order> findAllByUser_idAndActiveOrder11(Pageable pageable);

	void deleteById(int id);


	@Query(value="select * from `order` o where o.payment_method = ?1",nativeQuery = true)
	List<Order> findAllByPayment_Method(String payment_Method);

	@Query(value="Select * From `order` o where o.payment_method = ?1 ORDER BY o.id DESC LIMIT 5;",nativeQuery = true)
	List<Order> findTop5OrderByPaymentMethod(String payment_method);

	List<Order> findByUserId(String id);


	@Query("SELECT c FROM Order c WHERE STR_TO_DATE(c.booking_Date, '%Y-%m-%d %H:%i:%s') BETWEEN :startDate AND :endDate AND c.activeOrder.id LIKE :id")
	List<Order> findByBookingDateBetweenAndActiveOrderId(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("id") String id);







}
