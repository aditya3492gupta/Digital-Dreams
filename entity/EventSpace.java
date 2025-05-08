package entity;

public class EventSpace {
    private String spaceId;
    private String type; // Gold/Silver/Platinum
    private boolean isAvailable;
    private int noOfDays;

    public EventSpace(String spaceId, String type, boolean isAvailable) {
        this.spaceId = spaceId;
        this.type = type;
        this.isAvailable = isAvailable;
    }

    public EventSpace(String spaceId, String type, boolean isAvailable,int noOfDays) {
        this.spaceId = spaceId;
        this.type = type;
        this.isAvailable = isAvailable;
        this.noOfDays=noOfDays;
    }


    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
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

    public int getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(int noOfDays) {
        this.noOfDays = noOfDays;
    }
}