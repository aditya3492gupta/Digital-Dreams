package service;

import entity.WellnessFacility;
import repository.WellnessFacilityRepository;

import java.util.List;

public class WellnessFacilityService {
    private final WellnessFacilityRepository repository;

    public WellnessFacilityService() {
        this.repository = new WellnessFacilityRepository();
    }

    public boolean registerNewFacility(String id, String type) {
        WellnessFacility newFacility = new WellnessFacility(id, type, true);
        boolean added = repository.addFacility(newFacility);
        if (added) {
            System.out.println("Successfully added facility: " + id);
        } else {
            System.out.println("Failed to add facility (duplicate ID exists): " + id);
        }
        return added;
    }

    public WellnessFacility bookFacility(String type) {
        List<WellnessFacility> availableFacilities = getAvailableFacilitiesByType(type);

        if (availableFacilities.isEmpty()) {
            System.out.println("No available facilities of type: " + type);
            return null;
        }

        // Book the first available one
        WellnessFacility facilityToBook = availableFacilities.get(0);
        boolean success = !facilityToBook.isAvailable() ? false : repository.bookFacility(type) != null;

        if (success) {
            System.out.println("Facility successfully booked: " + facilityToBook.getFacilityId());
            return facilityToBook;
        } else {
            System.out.println("Facility booking failed due to overlap or unavailability.");
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
