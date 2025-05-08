package repository;

import entity.WellnessFacility;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WellnessFacilityRepository {
    private final List<WellnessFacility> facilities;

    public WellnessFacilityRepository() {
        this.facilities = new ArrayList<>();
    }

    public boolean addFacility(WellnessFacility facility) {
        if (facility == null) {
            return false;
        }

        // Check if a facility with the same ID already exists
        boolean facilityExists = facilities.stream()
                .anyMatch(f -> f.getFacilityId().equals(facility.getFacilityId()));

        if (facilityExists) {
            return false;
        }

        facilities.add(facility);
        return true;
    }

    public boolean deleteFacility(String facilityId) {
        return facilities.removeIf(f -> f.getFacilityId().equals(facilityId));
    }

    public WellnessFacility bookFacility(String facilityType) {
        Optional<WellnessFacility> facilityToBook = facilities.stream()
                .filter(f -> f.getType().equalsIgnoreCase(facilityType) && f.isAvailable())
                .findFirst();

        if (facilityToBook.isPresent()) {
            WellnessFacility facility = facilityToBook.get();
            facility.setAvailable(false);
            return facility;
        }

        return null;
    }

    public boolean releaseFacility(String facilityId) {
        Optional<WellnessFacility> facilityToRelease = facilities.stream()
                .filter(f -> f.getFacilityId().equals(facilityId))
                .findFirst();

        if (facilityToRelease.isPresent()) {
            WellnessFacility facility = facilityToRelease.get();
            facility.setAvailable(true);
            return true;
        }

        return false;
    }

    public boolean updateFacility(WellnessFacility updatedFacility) {
        if (updatedFacility == null) {
            return false;
        }

        for (int i = 0; i < facilities.size(); i++) {
            if (facilities.get(i).getFacilityId().equals(updatedFacility.getFacilityId())) {
                facilities.set(i, updatedFacility);
                return true;
            }
        }

        // Facility not found
        return false;
    }

    public List<WellnessFacility> getAllFacilities() {
        return new ArrayList<>(facilities);
    }

    public WellnessFacility getFacilityById(String facilityId) {
        return facilities.stream()
                .filter(f -> f.getFacilityId().equals(facilityId))
                .findFirst()
                .orElse(null);
    }

    public void showAvailableFacilities() {
        System.out.println("Available Wellness Facilities:");

        facilities.stream()
                .filter(WellnessFacility::isAvailable)
                .forEach(facility -> {
                    System.out.println("ID: " + facility.getFacilityId() +
                            ", Type: " + facility.getType() +
                            ", Price per hour: ₹" + facility.getPricePerHour());
                });
    }
}