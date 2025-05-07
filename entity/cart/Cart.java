package entity.cart;


import java.util.ArrayList;
import java.util.List;

public class Cart {
    private String userId;
    private List<CartItem> items;
    
    public Cart(String userId) {
        this.userId = userId;
        this.items = new ArrayList<>();
    }
    
    public String getUserId() {
        return userId;
    }
    
    public List<CartItem> getItems() {
        return items;
    }
    
    public void addItem(CartItem item) {
        items.add(item);
    }
    
    public boolean removeItem(String itemId) {
        return items.removeIf(item -> item.getItemId().equals(itemId));
    }
    
    public void clearCart() {
        items.clear();
    }
    
    public double getTotalCost() {
        return items.stream().mapToDouble(CartItem::getCost).sum();
    }
    
    public int getItemCount() {
        return items.size();
    }
}