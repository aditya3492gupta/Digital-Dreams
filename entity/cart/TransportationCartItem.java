package entity.cart;

public class TransportationCartItem extends CartItem {
    private String vehicleType;
    private int noOfDays;
    
    public TransportationCartItem(String resourceId, String vehicleType, double cost, int noOfDays) {
        super(resourceId, "Transportation", cost*noOfDays);
        this.vehicleType = vehicleType;
        this.noOfDays=noOfDays;
    }
    
    public String getVehicleType() {
        return vehicleType;
    }
    
    @Override
    public String getDescription() {
        return "Vehicle " + getResourceId() + " (" + vehicleType + ")";
    }
}
