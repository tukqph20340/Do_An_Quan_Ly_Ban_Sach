package WebProject.WebProject.service.impl;

import java.util.List;

import WebProject.WebProject.entity.Promotion;
import WebProject.WebProject.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import WebProject.WebProject.entity.Order;
import WebProject.WebProject.repository.OrderRepository;
import WebProject.WebProject.service.OrderService;
@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	OrderRepository orderRepository;
	@Override
	public void saveOrder(Order order) {
		// TODO Auto-generated method stub
		orderRepository.save(order);
	}
	@Override
	public List<Order> getAllOrderByUser_Id(String id) {
		// TODO Auto-generated method stub
		return orderRepository.findAllByUser_id(id);
	}

	@Override
	public List<Order> getOrderActive2(String id) {
		return orderRepository.findAllByUser_idAndActiveOrder2(id);
	}
	@Override
	public List<Order> getOrderActive3(String id) {
		return orderRepository.findAllByUser_idAndActiveOrder3(id);
	}
	@Override
	public List<Order> getOrderActive4(String id) {
		return orderRepository.findAllByUser_idAndActiveOrder4(id);
	}

	@Override
	public List<Order> getOrderActive6(String id) {
		return orderRepository.findAllByUser_idAndActiveOrder6(id);
	}
	@Override
	public List<Order> getOrderActive5(String id) {
		return orderRepository.findAllByUser_idAndActiveOrder5(id);
	}
	public List<Order> getOrderActive7(String id) {
		return orderRepository.findAllByUser_idAndActiveOrder7(id);
	}

	@Override
	public Order findById(int id) {
		return orderRepository.findById(id);
	}

	@Override
	public List<Order> findOrderById(int id) {
		return orderRepository.findOrderById(id);
	}

	@Override
	public List<Order> findAll() {
		return orderRepository.findAll();
	}
	@Override
	public List<Order> findTop5RecentOrder() {
		return orderRepository.findTop5RecentOrder();
	}
	@Override
	public List<String> findTop5RecentCustomer() {
		return orderRepository.findTop5RecentCustomer();
	}
	@Override
	public Page<Order> findAll(Pageable pageable) {
		return orderRepository.findAll(pageable);
	}
	@Override
	public void deleteById(int id) {
		orderRepository.deleteById(id);
	}
	@Override
	public List<Order> findAllByPayment_Method(String payment_Method) {
		return orderRepository.findAllByPayment_Method(payment_Method);
	}
	@Override
	public List<Order> findTop5OrderByPaymentMethod(String payment_method) {
		return orderRepository.findTop5OrderByPaymentMethod(payment_method);
	}

	@Override
	public Page<Order> findPages(Pageable pageable) {
		return orderRepository.findAll(pageable);
	}

	@Override
	public Page<Order> trangThaiDonHang(Pageable pageable, String id) {
		return orderRepository.findByActiveOrder_Id(pageable,id);
	}

}
