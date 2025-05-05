package repository;

import entity.Transportation;

import java.util.ArrayList;
import java.util.List;

public class TransportationRepository {
    private List<Transportation> vehicles;

    public TransportationRepository() {
        vehicles = new ArrayList<>();
    }

    public boolean addVehicle(Transportation vehicle) {
        for (Transportation v : vehicles) {
            if (v.getVehicleId().equalsIgnoreCase(vehicle.getVehicleId())) {
                return false; // Duplicate vehicle ID
            }
        }
        vehicles.add(vehicle);
        return true;
    }

    public List<Transportation> getAllVehicles() {
        return new ArrayList<>(vehicles); // Defensive copy
    }

    public Transportation getVehicleById(String id) {
        for (Transportation v : vehicles) {
            if (v.getVehicleId().equalsIgnoreCase(id)) {
                return v;
            }
        }
        return null;
    }

    public boolean bookVehicle(String id) {
        Transportation vehicle = getVehicleById(id);
        if (vehicle != null && vehicle.isAvailable()) {
            vehicle.setAvailable(false);
            return true;
        }
        return false;
    }
}
