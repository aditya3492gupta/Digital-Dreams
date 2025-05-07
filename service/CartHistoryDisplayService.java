package service;

import java.util.List;

import entity.cart.CartHistory;
import entity.cart.CartItem;

public class CartHistoryDisplayService {

    private CartHistoryService cartHistoryService;

    public CartHistoryDisplayService() {
        cartHistoryService = new CartHistoryService();
    }

    public void displayCartHistory(String userId) {
        List<CartHistory> historyList = cartHistoryService.getCartHistoryByUserId(userId);
        if (historyList.isEmpty()) {
            System.out.println("No cart history available for user: " + userId);
        } else {
            for (CartHistory history : historyList) {
                System.out.println("Timestamp: " + history.getTimestamp());
                System.out.println("Items: ");
                for (CartItem item : history.getItems()) {
                    System.out.println(" - " + item.getItemId() + ": " + item.getCost());
                }
            }
        }
    }
}
