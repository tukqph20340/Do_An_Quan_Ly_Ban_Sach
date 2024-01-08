package WebProject.WebProject.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import WebProject.WebProject.entity.Cart;
import WebProject.WebProject.repository.CartRepository;
import WebProject.WebProject.service.CartService;

@Service
public class CartServicempl implements CartService{


	@Autowired
	CartRepository cartRepository;

	/**
	 *
	 */
	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub
		cartRepository.deleteById(id);
	}

//	@Override
//	public void deleteByIdUser(int user_id) {
//		cartRepository.deleteById(user_id);
//	}

	@Override
	public List<Cart> GetAllCartByUser_id(String user_id) {
		// TODO Auto-generated method stub
		return cartRepository.findAllByUser_id(user_id);
	}

	@Override
	public List<Cart> getListCartSelected(String user_id) {
		return cartRepository.getListCartSelected(user_id);
	}


	@Override
	public void saveCart(Cart cart) {
		// TODO Auto-generated method stub
		cartRepository.save(cart);
	}

	@Override
	public void updateCartItemSelection(String cartItemId, boolean isSelected) {
		try {
			Integer itemId = Integer.valueOf(cartItemId);
			Optional<Cart> cartOptional = cartRepository.findById(itemId);
			cartOptional.map(o -> {
				o.setSelected(isSelected);
				return cartRepository.save(o);
			}).orElse(null);
		} catch (NumberFormatException e) {
			// Handle the case where cartItemId is not a valid integer
			// Log an error, throw an exception, or handle it as appropriate for your application
			e.printStackTrace(); // This is just an example, logging or throwing an exception might be more suitable in a real application
		}
	}

	@Override
	public void updateCartItemQuantity(String cartItemId, Long quantity) {
		try {
			Integer itemId = Integer.valueOf(cartItemId);
			Optional<Cart> cartOptional = cartRepository.findById(itemId);
			cartOptional.map(o -> {
				if (quantity == 0) {
					o.setCount(Long.valueOf(1));
				} else {
					o.setCount(quantity);
				}
				return cartRepository.save(o);
			}).orElse(null);
		} catch (NumberFormatException e) {
			// Handle the case where cartItemId is not a valid integer
			// Log an error, throw an exception, or handle it as appropriate for your application
			e.printStackTrace(); // This is just an example, logging or throwing an exception might be more suitable in a real application
		}
	}
}
