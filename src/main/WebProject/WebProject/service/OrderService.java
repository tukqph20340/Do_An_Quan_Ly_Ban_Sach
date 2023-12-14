package WebProject.WebProject.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import WebProject.WebProject.entity.Order;

public interface OrderService {

	public void saveOrder(Order order);

	List<Order> getAllOrderByUser_Id(String id);

	List<Order> getOrderActive2(String id);

	List<Order> getOrderActive3(String id);

	List<Order> getOrderActive4(String id);

	List<Order> getOrderActive5(String id);

	List<Order> getOrderActive6(String id);

	List<Order> getOrderActive7(String id);


	Order findById(int id);

	List<Order> findOrderById(int id);

	List<Order> findAll();

	List<Order> findTop5RecentOrder();

	List<String> findTop5RecentCustomer();

	Page<Order> findAll(Pageable pageable);

	void deleteById(int id);

	List<Order> findAllByPayment_Method(String payment_Method);

	List<Order> findTop5OrderByPaymentMethod(String payment_method);
	Page<Order> findPages(Pageable pageable);

	Page<Order> trangThaiDonHang(Pageable pageable,String id);

}
