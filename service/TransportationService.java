package service;

import java.util.List;

import entity.Transportation;
import repository.TransportationRepository;

public class TransportationService {
    private TransportationRepository transportationRepository;

    // Constructor to initialize the repository
    public TransportationService(TransportationRepository transportationRepository) {
        this.transportationRepository = transportationRepository;
    }

    // Method to add a vehicle to the repository
    public boolean addVehicle(String vehicleId, String vehicleType, boolean isAvailable) {
        Transportation vehicle = new Transportation(vehicleId, vehicleType, isAvailable);
        return transportationRepository.addVehicle(vehicle);
    }

    // Method to book a vehicle of a specific type
    public boolean bookVehicle(String type) {
        // Corrected to use the instance method
        return transportationRepository.bookVehicle(type);
    }

    // Method to release a vehicle based on its ID
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

    // Method to cancel a vehicle booking by vehicle ID
    public boolean cancelBooking(String vehicleId) {
        return transportationRepository.cancelBooking(vehicleId);
    }
}