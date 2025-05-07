package repository;

import entity.cart.Cart;
import java.util.HashMap;
import java.util.Map;

public class CartRepository {
    private Map<String, Cart> userCarts = new HashMap<>();
    
    public Cart getCartForUser(String userId) {
        return userCarts.computeIfAbsent(userId, Cart::new);
    }
    
    public void saveCart(Cart cart) {
        userCarts.put(cart.getUserId(), cart);
    }
    
    public void deleteCart(String userId) {
        userCarts.remove(userId);
    }
}

// S
