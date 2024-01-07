package WebProject.WebProject.service.impl;

import java.util.List;
import java.util.Optional;

import WebProject.WebProject.entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import WebProject.WebProject.entity.Order_Item;
import WebProject.WebProject.repository.Order_ItemRepository;
import WebProject.WebProject.service.Order_ItemService;

@Service
public class Order_ItemServiceImpl implements Order_ItemService {

    @Autowired
    Order_ItemRepository order_ItemRepository;

    @Override
    public void saveOrder_Item(Order_Item order_Item) {
        // TODO Auto-generated method stub
        order_ItemRepository.save(order_Item);
    }

    @Override
    public List<Order_Item> getAllByOrder_Id(int id) {
        // TODO Auto-generated method stub
        return order_ItemRepository.findAllByOrder_id(id);
    }

    @Override
    public Order_Item detail(int id) {
        return order_ItemRepository.findById(id).get();
    }

    @Override
    public void deleteById(int id) {
        order_ItemRepository.deleteById(id);
    }

    @Override
    public List<Order_Item> find() {
        return order_ItemRepository.findAll();
    }

    @Override
    public void updateCartItemQuantity(String cartItemId, Long quantity) {
        try {
            Integer itemId = Integer.valueOf(cartItemId);
            Optional<Order_Item> cartOptional = order_ItemRepository.findById(itemId);

            cartOptional.ifPresent(orderItem -> {
                int updatedQuantity = (quantity < 1) ? 1 : Math.toIntExact(quantity);

                // Update quantity and unit price
                orderItem.setCount(updatedQuantity);
                orderItem.setUnit_price(orderItem.getUnit_price() * updatedQuantity);

                order_ItemRepository.save(orderItem);
            });
        } catch (NumberFormatException e) {
            // Handle the case where cartItemId is not a valid integer
            // Log an error, throw an exception, or handle it as appropriate for your application
            e.printStackTrace(); // This is just an example, logging or throwing an exception might be more suitable in a real application
        }
    }
}

