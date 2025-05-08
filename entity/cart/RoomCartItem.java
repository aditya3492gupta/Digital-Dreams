package entity.cart;

public class RoomCartItem extends CartItem {
    private String roomType;
    private int noOfDays;
    
    public RoomCartItem(String resourceId, String roomType, double cost, int noOfDays) {
        super(resourceId, "Room", cost*noOfDays);
        this.roomType = roomType;
        this.noOfDays= noOfDays;
    }
    
    public String getRoomType() {
        return roomType;
    }
    
    @Override
    public String getDescription() {
        return "Room " + getResourceId() + " (" + roomType + ")";
    }
}