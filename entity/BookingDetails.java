package entity;

public class BookingDetails {
    private WellnessFacility facility;
    private int hoursBooked;
    private double totalCost;

    public BookingDetails(WellnessFacility facility, int hoursBooked) {
        this.facility = facility;
        this.hoursBooked = hoursBooked;
        this.totalCost = facility.getPricePerHour() * hoursBooked;
    }

    public WellnessFacility getFacility() {
        return facility;
    }

    public int getHoursBooked() {
        return hoursBooked;
    }

    public double getTotalCost() {
        return totalCost;
    }

    @Override
    public String toString() {
        return "Booked " + facility.getType() + " (" + facility.getFacilityId() + ") for " + hoursBooked +
               " hour(s). Total Cost: ₹" + totalCost;
    }
}
