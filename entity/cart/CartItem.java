package entity.cart;

import java.util.UUID;

// This class represents a cart item - abstract base class for all bookable items
public abstract class CartItem {
    private String itemId;
    private String resourceId;
    private String resourceType;
    private double cost;
    
    public CartItem(String resourceId, String resourceType, double cost) {
        this.itemId = UUID.randomUUID().toString();
        this.resourceId = resourceId;
        this.resourceType = resourceType;
        this.cost = cost;
    }
    
    public String getItemId() {
        return itemId;
    }
    
    public String getResourceId() {
        return resourceId;
    }
    
    public String getResourceType() {
        return resourceType;
    }
    
    public double getCost() {
        return cost;
    }
    
    public abstract String getDescription();
}