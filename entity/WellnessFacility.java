//package Digital-Dreams.entity;
package entity;

public class WellnessFacility {
    private String facilityId;
    private String type; // Swimming Pool, Gym
    private boolean isAvailable;

    public WellnessFacility(String facilityId, String type, boolean isAvailable) {
        this.facilityId = facilityId;
        this.type = type;
        this.isAvailable = isAvailable;
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
}
