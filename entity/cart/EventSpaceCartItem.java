package entity.cart;

public class EventSpaceCartItem extends CartItem {
    private String eventType;
    
    public EventSpaceCartItem(String resourceId, String eventType, double cost) {
        super(resourceId, "EventSpace", cost);
        this.eventType = eventType;
    }
    
    public String getEventType() {
        return eventType;
    }
    
    @Override
    public String getDescription() {
        return "Event Space " + getResourceId() + " (" + eventType + ")";
    }
}
