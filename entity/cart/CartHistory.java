package entity.cart;

import java.time.LocalDateTime;
import java.util.List;

public class CartHistory {
    private String userId;  // Use userId instead of userEmail
    private List<CartItem> items;
    private LocalDateTime timestamp;

    // Constructor to initialize CartHistory with Cart object
    public CartHistory(Cart cart) {
        this.userId = cart.getUserId();
        this.items = cart.getItems();
        this.timestamp = LocalDateTime.now();
    }

    // Getters
    public String getUserId() {
        return userId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
