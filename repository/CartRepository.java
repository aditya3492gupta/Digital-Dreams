package repository;

import entity.cart.Cart;
import entity.cart.CartHistory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CartRepository {

    // In-memory storage for user carts
    private Map<String, Cart> userCarts = new HashMap<>();

    // In-memory storage for cart histories
    private Map<String, CartHistory> cartHistoryMap = new HashMap<>();

    public Cart getCartForUser(String userId) {
        return userCarts.computeIfAbsent(userId, Cart::new);
    }

    public void saveCart(Cart cart) {
        userCarts.put(cart.getUserId(), cart);
    }

    public void deleteCart(String userId) {
        // Save cart history before deleting the cart
        Cart cart = userCarts.get(userId);
        if (cart != null) {
            List<String> itemIds = cart.getItems().stream()
                    .map(item -> item.getItemId())
                    .collect(Collectors.toList());
            // Save cart history
            CartHistory cartHistory = new CartHistory(cart);
            cartHistoryMap.put(userId, cartHistory);
        }
        userCarts.remove(userId);
    }

    public CartHistory getCartHistory(String userId) {
        return cartHistoryMap.get(userId);
    }
}
