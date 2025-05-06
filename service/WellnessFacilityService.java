package service;

import entity.BookingDetails;
import entity.WellnessFacility;
import repository.WellnessFacilityRepository;

import java.util.List;

public class WellnessFacilityService {
    private final WellnessFacilityRepository repository;

    public WellnessFacilityService() {
        this.repository = new WellnessFacilityRepository();
    }

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

    public boolean releaseFacility(String facilityId) {
        return repository.releaseFacility(facilityId);
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
}
