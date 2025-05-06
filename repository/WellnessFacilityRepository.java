package repository;

import entity.WellnessFacility;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WellnessFacilityRepository {
    private Map<String, List<WellnessFacility>> facilityInventory;

    public WellnessFacilityRepository() {
        facilityInventory = new HashMap<>();
        initializeFacilities();
    }

    private void initializeFacilities() {
        facilityInventory.put("Swimming Pool", new ArrayList<>());
        facilityInventory.put("Gym", new ArrayList<>());

        int defaultQuantity = 3;

        for (int i = 1; i <= defaultQuantity; i++) {
            facilityInventory.get("Swimming Pool")
                    .add(new WellnessFacility("SP" + i, "Swimming Pool", true, 200.0));
            facilityInventory.get("Gym")
                    .add(new WellnessFacility("GYM" + i, "Gym", true, 150.0));
        }
    }

    public boolean addFacility(WellnessFacility facility) {
        facilityInventory.putIfAbsent(facility.getType(), new ArrayList<>());

        for (WellnessFacility f : facilityInventory.get(facility.getType())) {
            if (f.getFacilityId().equalsIgnoreCase(facility.getFacilityId())) {
                return false; // Duplicate ID
            }
        }

        facilityInventory.get(facility.getType()).add(facility);
        return true;
    }

    public WellnessFacility bookFacility(String type) {
        List<WellnessFacility> facilities = facilityInventory.get(type);
        if (facilities != null) {
            for (WellnessFacility f : facilities) {
                if (f.isAvailable()) {
                    f.setAvailable(false);
                    System.out.println("Facility booked: " + f.getFacilityId());
                    return f;
                }
            }
        }
        System.out.println("No available facility for type: " + type);
        return null;
    }

    public boolean releaseFacility(String facilityId) {
        for (List<WellnessFacility> facilities : facilityInventory.values()) {
            for (WellnessFacility f : facilities) {
                if (f.getFacilityId().equalsIgnoreCase(facilityId)) {
                    if (!f.isAvailable()) {
                        f.setAvailable(true);
                        System.out.println("Facility released: " + facilityId);
                        return true;
                    } else {
                        System.out.println("Facility already available: " + facilityId);
                        return false;
                    }
                }
            }
        }
        System.out.println("Facility not found: " + facilityId);
        return false;
    }

    public List<WellnessFacility> getAllFacilities() {
        List<WellnessFacility> all = new ArrayList<>();
        for (List<WellnessFacility> list : facilityInventory.values()) {
            all.addAll(list);
        }
        return all;
    }

    public void showAvailableFacilities() {
        System.out.println("Available Wellness Facilities:");
        for (Map.Entry<String, List<WellnessFacility>> entry : facilityInventory.entrySet()) {
            long available = entry.getValue().stream().filter(WellnessFacility::isAvailable).count();
            System.out.println(entry.getKey() + ": " + available + " available");
        }
    }
}
