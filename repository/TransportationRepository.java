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

    // Method to book a vehicle by its ID (set its availability to false)
    public boolean bookVehicle(String id) {
        Transportation vehicle = getVehicleById(id);
        if (vehicle != null && vehicle.isAvailable()) {
            vehicle.setAvailable(false); // Set availability to false when booked
            return true;
        }
        return false; // Return false if the vehicle is not available
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

    // Method to show all available vehicles (vehicles that are not booked)
    public void showAvailableVehicles() {
        System.out.println("Available Vehicles:");
        for (Transportation v : vehicles) {
            if (v.isAvailable()) {
                System.out.println(v); // Print the vehicle if it is available
            }
        }
    }

    // Method to cancel a vehicle booking by vehicle ID (set availability to true)
    public boolean cancelBooking(String id) {
        return releaseVehicle(id); // Cancel booking by releasing the vehicle
    }
}
