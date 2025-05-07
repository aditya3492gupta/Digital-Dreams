package entity.cart;

// Ensure the correct package is imported
import service.CartHistoryService; // Verify this package path is correct
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private String userId;
    private List<CartItem> items;
    private CartHistoryService cartHistoryService;

    public Cart(String userId) {
        this.userId = userId;
        this.items = new ArrayList<>();
        this.cartHistoryService = new CartHistoryService();
    }

    public String getUserId() {
        return userId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void addItem(CartItem item) {
        items.add(item);
        cartHistoryService.saveCartHistory(this);
    }

    public boolean removeItem(String itemId) {
        boolean removed = items.removeIf(item -> item.getItemId().equals(itemId));
        if (removed) {
            cartHistoryService.saveCartHistory(this); // Save history whenever an item is removed
        }
        return removed;
    }

    public void clearCart() {
        items.clear();
        cartHistoryService.saveCartHistory(this);
    }

    public double getTotalCost() {
        return items.stream().mapToDouble(CartItem::getCost).sum();
    }

    public int getItemCount() {
        return items.size();
    }
}