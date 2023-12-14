package WebProject.WebProject.service;

import java.util.List;

import WebProject.WebProject.entity.Cart;

public interface CartService {
	void deleteById(int id);

//	void deleteByIdUser(int user_id);

	List<Cart> GetAllCartByUser_id(String user_id);

	List<Cart> getListCartSelected();

	void saveCart(Cart cart);

	void updateCartItemSelection(String cartItemId, boolean isSelected);

	void updateCartItemQuantity(String cartItemId, Long uantity);


}
