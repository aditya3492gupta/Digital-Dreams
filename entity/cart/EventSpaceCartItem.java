package entity.cart;

public class EventSpaceCartItem extends CartItem {
    private String eventType;
    private int noOfDays;

    public EventSpaceCartItem(String resourceId, String eventType, double cost, int noOfDays) {
        super(resourceId, "EventSpace", cost * noOfDays);
        this.eventType = eventType;
        this.noOfDays = noOfDays;
    }

    public String getEventType() {
        return eventType;
    }

    @Override
    public String getDescription() {
        return "Event Space " + getResourceId() + " (" + eventType + ")";
    }

    public int getNoOfDays() {
        return this.noOfDays;
    }

    public void setNoOfDays(int noOfDays) {
        this.noOfDays = noOfDays;
    }
}
