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