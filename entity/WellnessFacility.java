package entity;

public class WellnessFacility {
    private String facilityId;
    private String type; // Swimming Pool, Gym
    private boolean isAvailable;
    private double pricePerHour; // 💰 Price per hour

    public WellnessFacility(String facilityId, String type, boolean isAvailable, double pricePerHour) {
        this.facilityId = facilityId;
        this.type = type;
        this.isAvailable = isAvailable;
        this.pricePerHour = pricePerHour;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }
}
