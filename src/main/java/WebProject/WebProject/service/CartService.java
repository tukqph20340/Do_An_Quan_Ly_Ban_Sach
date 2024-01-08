package WebProject.WebProject.service;

import WebProject.WebProject.entity.Cart;

import java.util.List;

public interface CartService {
	void deleteById(int id);

//	void deleteByIdUser(int user_id);

	List<Cart> GetAllCartByUser_id(String user_id);

	List<Cart> getListCartSelected(String user_id);

	void saveCart(Cart cart);

	void updateCartItemSelection(String cartItemId, boolean isSelected);

	void updateCartItemQuantity(String cartItemId, Long uantity);


}
