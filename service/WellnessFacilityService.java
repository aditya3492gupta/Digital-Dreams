package service;

import entity.BookingDetails;
import entity.WellnessFacility;
import java.util.List;
import repository.WellnessFacilityRepository;

public class WellnessFacilityService {
    private final WellnessFacilityRepository repository;

    public WellnessFacilityService() {
        this.repository = new WellnessFacilityRepository();
    }

    /**
     * Registers a new wellness facility in the system.
     * 
     * @param id           The unique identifier for the facility
     * @param type         The type of wellness facility
     * @param pricePerHour The hourly rate for the facility
     * @return true if registration was successful, false otherwise
     */
    public boolean registerNewFacility(String id, String type, double pricePerHour) {
        WellnessFacility newFacility = new WellnessFacility(id, type, true, pricePerHour);
        boolean added = repository.addFacility(newFacility);
        if (added) {
            System.out.println("Successfully added facility: " + id);
        } else {
            System.out.println("Failed to add facility (duplicate ID exists): " + id);
        }
        return added;
    }

    /**
     * Books a wellness facility of the specified type for a given number of hours.
     * 
     * @param type  The type of facility to book
     * @param hours The number of hours to book the facility for
     * @return BookingDetails if booking was successful, null otherwise
     */
    public BookingDetails bookFacilityWithHours(String type, int hours) {
        List<WellnessFacility> availableFacilities = getAvailableFacilitiesByType(type);

        if (availableFacilities.isEmpty()) {
            System.out.println("No available facilities of type: " + type);
            return null;
        }

        WellnessFacility facilityToBook = availableFacilities.get(0);
        boolean success = repository.bookFacility(type) != null;

        if (success) {
            BookingDetails booking = new BookingDetails(facilityToBook, hours);
            System.out.println("Facility successfully booked: " + booking);
            return booking;
        } else {
            System.out.println("Facility booking failed.");
            return null;
        }
    }

    public boolean deleteFacility(String facilityId) {
        boolean deleted = repository.deleteFacility(facilityId);
        if (deleted) {
            System.out.println("Successfully deleted facility: " + facilityId);
        } else {
            System.out.println("Failed to delete facility (not found): " + facilityId);
        }
        return deleted;
    }

    public boolean bookSpecificFacility(String facilityId, int hours) {
        WellnessFacility facility = repository.getFacilityById(facilityId);
        if (facility != null && facility.isAvailable()) {
            facility.setAvailable(false);
            repository.updateFacility(facility);
            double totalCost = facility.getPricePerHour() * hours;
            System.out.println("Facility booked: " + facility.getFacilityId() + " - " + facility.getType());
            System.out.println("Hours: " + hours);
            System.out.println("Total cost: ₹" + totalCost);
            return true;
        }
        System.out.println("Facility " + facilityId + " is not available for booking.");
        return false;
    }

    public boolean releaseFacility(String facilityId) {
        return repository.releaseFacility(facilityId);
    }

    public boolean updateFacility(WellnessFacility facility) {
        if (facility == null) {
            System.out.println("Cannot update: facility is null");
            return false;
        }

        WellnessFacility existingFacility = repository.getFacilityById(facility.getFacilityId());

        if (existingFacility == null) {
            System.out.println("Cannot update: facility not found with ID: " + facility.getFacilityId());
            return false;
        }

        boolean updated = repository.updateFacility(facility);

        if (updated) {
            System.out.println("Successfully updated facility: " + facility.getFacilityId());
        } else {
            System.out.println("Failed to update facility: " + facility.getFacilityId());
        }

        return updated;
    }

    public List<WellnessFacility> getAllFacilities() {
        return repository.getAllFacilities();
    }

    public void showAvailableFacilities() {
        repository.showAvailableFacilities();
    }

    public List<WellnessFacility> getAvailableFacilitiesByType(String type) {
        return repository.getAllFacilities().stream()
                .filter(f -> f.getType().equalsIgnoreCase(type) && f.isAvailable())
                .toList();
    }

    public WellnessFacility getFacilityById(String facilityId) {
        WellnessFacility facility = repository.getFacilityById(facilityId);
        if (facility != null) {
            System.out.println("Facility found: " + facility.getFacilityId());
        } else {
            System.out.println("Facility not found: " + facilityId);
        }
        return facility;
    }
}