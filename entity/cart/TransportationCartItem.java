package entity.cart;

public class TransportationCartItem extends CartItem {
    private String vehicleType;
    
    public TransportationCartItem(String resourceId, String vehicleType, double cost) {
        super(resourceId, "Transportation", cost);
        this.vehicleType = vehicleType;
    }
    
    public String getVehicleType() {
        return vehicleType;
    }
    
    @Override
    public String getDescription() {
        return "Vehicle " + getResourceId() + " (" + vehicleType + ")";
    }
}
