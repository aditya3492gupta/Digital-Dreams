package entity;

public class SilverEventSpace extends EventSpace {
    private double bookingCost;
    private boolean hasCatering;
    

    public SilverEventSpace(String spaceId, boolean isAvailable) {
        super(spaceId, "Silver", isAvailable);
        this.bookingCost = 2000.0; 
        this.hasCatering = false;   
        
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
