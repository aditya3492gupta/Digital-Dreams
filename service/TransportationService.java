package service;

import entity.Transportation;
import java.util.List;
import repository.TransportationRepository;

public class TransportationService {
    private TransportationRepository transportationRepository;

    // Constructor to initialize the repository
    public TransportationService(TransportationRepository transportationRepository) {
        this.transportationRepository = transportationRepository;
    }

    // Method to add a vehicle to the repository
    // Add a vehicle with cost and type
    public boolean addVehicle(String vehicleId, String vehicleType, double cost, boolean isAvailable) {
        Transportation vehicle = new Transportation(vehicleId, vehicleType, cost, isAvailable);
        return transportationRepository.addVehicle(vehicle);
    }

    // Book a vehicle by ID and show cost
    public void bookVehicle(String vehicleId) {
        Double cost = transportationRepository.bookVehicle(vehicleId);
        if (cost != null) {
            System.out.println("Vehicle booked successfully! Cost: ₹" + cost);
        } else {
            System.out.println("Booking failed. Vehicle not found or already booked.");
        }
    }

    /**
     * Books a specific vehicle by ID.
     * 
     * @param vehicleId The ID of the vehicle to book
     * @return true if the booking was successful, false otherwise
     */
    public boolean bookSpecificVehicle(String vehicleId) {
        Transportation vehicle = transportationRepository.getVehicleById(vehicleId);
        if (vehicle != null && vehicle.isAvailable()) {
            vehicle.setAvailable(false);
            transportationRepository.updateVehicle(vehicle);
            System.out.println("Vehicle booked: " + vehicle.getVehicleId() + " - " + vehicle.getVehicleType());
            System.out.println("Cost: ₹" + vehicle.getCost());
            return true;
        }
        System.out.println("Vehicle " + vehicleId + " is not available for booking.");
        return false;
    }

    public boolean releaseVehicle(String vehicleId) {
        return transportationRepository.releaseVehicle(vehicleId);
    }

    // Method to get all vehicles in the repository
    public List<Transportation> getAllVehicles() {
        return transportationRepository.getAllVehicles();
    }

    // Method to show the available vehicles of a specific type
    public void showAvailableVehicles() {
        // Ensure this method exists in TransportationRepository
        transportationRepository.showAvailableVehicles();
    }

    // Method to get a vehicle by its ID
    public Transportation getVehicleById(String vehicleId) {
        return transportationRepository.getVehicleById(vehicleId);
    }

    // In TransportationService.java
    public void deleteVehicle(String vehicleId) {
        boolean isDeleted = transportationRepository.deleteVehicle(vehicleId);
        if (isDeleted) {
            System.out.println("Vehicle " + vehicleId + " deleted successfully.");
        } else {
            System.out.println("Vehicle " + vehicleId + " not found.");
        }
    }

    // Method to cancel a vehicle booking by vehicle ID
    public boolean cancelBooking(String vehicleId) {
        return transportationRepository.cancelBooking(vehicleId);
    }

    /**
     * Updates an existing vehicle in the system.
     * 
     * @param vehicle The vehicle with updated information
     * @return true if the vehicle was successfully updated, false otherwise
     */
    public boolean updateVehicle(Transportation vehicle) {
        if (vehicle == null) {
            System.out.println("Cannot update: vehicle is null");
            return false;
        }

        Transportation existingVehicle = transportationRepository.getVehicleById(vehicle.getVehicleId());

        if (existingVehicle == null) {
            System.out.println("Cannot update: vehicle not found with ID: " + vehicle.getVehicleId());
            return false;
        }

        boolean updated = transportationRepository.updateVehicle(vehicle);

        if (updated) {
            System.out.println("Successfully updated vehicle: " + vehicle.getVehicleId());
        } else {
            System.out.println("Failed to update vehicle: " + vehicle.getVehicleId());
        }

        return updated;
    }
}