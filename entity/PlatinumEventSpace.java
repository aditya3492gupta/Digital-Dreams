package entity;

public class PlatinumEventSpace extends EventSpace {
    private double bookingCost;
    private boolean hasCatering;
    

    public PlatinumEventSpace(String spaceId, boolean isAvailable) {
        super(spaceId, "Platinum", isAvailable);
        this.bookingCost = 6000.0; 
        this.hasCatering = true;   
        
    }

    // Getters and setters for new properties
    public double getBookingCost() {
        return bookingCost;
    }

    public void setBookingCost(double bookingCost) {
        this.bookingCost = bookingCost;
    }

    public boolean hasCatering() {
        return hasCatering;
    }

    public void setHasCatering(boolean hasCatering) {
        this.hasCatering = hasCatering;
    }

    

}