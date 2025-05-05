package entity;
public class GoldEventSpace extends EventSpace {
    private double bookingCost;
    private boolean hasCatering;
    

    public GoldEventSpace(String spaceId, boolean isAvailable) {
        super(spaceId, "Gold", isAvailable);
        this.bookingCost = 4000.0; 
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
