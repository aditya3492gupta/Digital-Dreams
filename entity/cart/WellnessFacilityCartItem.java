package entity.cart;

public class WellnessFacilityCartItem extends CartItem {
    private String facilityType;
    private int hours;
    
    public WellnessFacilityCartItem(String resourceId, String facilityType, int hours, double cost) {
        super(resourceId, "WellnessFacility", cost * hours);
        this.facilityType = facilityType;
        this.hours = hours;
    }
    
    public String getFacilityType() {
        return facilityType;
    }
    
    public int getHours() {
        return hours;
    }
    
    @Override
    public String getDescription() {
        return "Wellness Facility " + getResourceId() + " (" + facilityType + ") for " + hours + " hours";
    }
}
