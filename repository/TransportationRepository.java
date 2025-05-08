package repository;

import entity.Transportation;
import java.util.ArrayList;
import java.util.List;

public class TransportationRepository {
    private List<Transportation> vehicles;

    // Constructor to initialize the vehicles list
    public TransportationRepository() {
        vehicles = new ArrayList<>();
    }

    // Method to add a vehicle to the repository
    public boolean addVehicle(Transportation vehicle) {
        // Check if the vehicle already exists by vehicleId
        for (Transportation v : vehicles) {
            if (v.getVehicleId().equalsIgnoreCase(vehicle.getVehicleId())) {
                return false; // Return false if vehicle already exists
            }
        }
        // Add the vehicle if it does not exist
        vehicles.add(vehicle);
        return true;
    }

    public boolean deleteVehicle(String vehicleId) {
        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).getVehicleId().equalsIgnoreCase(vehicleId)) {
                vehicles.remove(i); // Removes the vehicle from the list
                return true; // Successfully deleted
            }
        }
        return false; // Vehicle with the given ID not found
    }

    // Method to get all vehicles in the repository
    public List<Transportation> getAllVehicles() {
        return new ArrayList<>(vehicles); // Return a defensive copy of the list
    }

    // Method to get a specific vehicle by its ID
    public Transportation getVehicleById(String id) {
        for (Transportation v : vehicles) {
            if (v.getVehicleId().equalsIgnoreCase(id)) {
                return v; // Return the vehicle if found
            }
        }
        return null; // Return null if no vehicle found with the given ID
    }

    public Double bookVehicle(String id) {
        Transportation vehicle = getVehicleById(id);
        if (vehicle != null && vehicle.isAvailable()) {
            vehicle.setAvailable(false);
            return vehicle.getCost();
        }
        return null; // Booking failed
    }

    // Method to release a vehicle by its ID (set its availability to true)
    public boolean releaseVehicle(String id) {
        Transportation vehicle = getVehicleById(id);
        if (vehicle != null && !vehicle.isAvailable()) {
            vehicle.setAvailable(true); // Set availability to true when released
            return true;
        }
        return false; // Return false if the vehicle was not found or already available
    }

    public void showAvailableVehicles() {
        System.out.println("Available Vehicles:");
        for (Transportation v : vehicles) {
            if (v.isAvailable()) {
                System.out.println(v); // Print the vehicle if it is available
            }
        }
    }

    public boolean cancelBooking(String id) {
        return releaseVehicle(id);
    }

    public boolean updateVehicle(Transportation updatedVehicle) {
        if (updatedVehicle == null) {
            return false;
        }

        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).getVehicleId().equalsIgnoreCase(updatedVehicle.getVehicleId())) {
                vehicles.set(i, updatedVehicle);
                return true;
            }
        }

        // Vehicle not found
        return false;
    }
}