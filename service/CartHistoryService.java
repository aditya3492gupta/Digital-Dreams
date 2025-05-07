package service;

import entity.cart.Cart;
import entity.cart.CartHistory;
import java.util.ArrayList;
import java.util.List;

public class CartHistoryService {

    private static List<CartHistory> cartHistoryList = new ArrayList<>();

    // Method to add cart history
    public void saveCartHistory(Cart cart) {
        CartHistory cartHistory = new CartHistory(cart);
        cartHistoryList.add(cartHistory);
    }

    // Method to retrieve cart history for a user
    public List<CartHistory> getCartHistoryByUserId(String userId) {
        List<CartHistory> userCartHistory = new ArrayList<>();
        for (CartHistory history : cartHistoryList) {
            if (history.getUserId().equals(userId)) {
                userCartHistory.add(history);
            }
        }
        return userCartHistory;
    }

    // Optional: You can add a method to save it into a database.
}
