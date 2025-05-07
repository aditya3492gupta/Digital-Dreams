package entity.cart;

public class RoomCartItem extends CartItem {
    private String roomType;
    
    public RoomCartItem(String resourceId, String roomType, double cost) {
        super(resourceId, "Room", cost);
        this.roomType = roomType;
    }
    
    public String getRoomType() {
        return roomType;
    }
    
    @Override
    public String getDescription() {
        return "Room " + getResourceId() + " (" + roomType + ")";
    }
}