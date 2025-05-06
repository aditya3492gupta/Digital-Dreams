package service;

import entity.*;
import repository.EventSpaceRepository;

import java.util.List;

public class EventSpaceService {
    private final EventSpaceRepository eventSpaceRepository;

    public EventSpaceService() {
        this.eventSpaceRepository = new EventSpaceRepository();
    }

    public EventSpaceService(EventSpaceRepository repository) {
        this.eventSpaceRepository = repository;
    }

    // Add a new EventSpace
    public void createEventSpace(EventSpace eventSpace) {
        eventSpaceRepository.addEventSpace(eventSpace);
    }

    // Retrieve an EventSpace by spaceId
    public EventSpace getEventSpaceById(String spaceId) {
        return eventSpaceRepository.getEventSpace(spaceId);
    }

    // Retrieve all EventSpaces
    public List<EventSpace> getAllEventSpaces() {
        return eventSpaceRepository.getAllEventSpaces();
    }

    // Update an EventSpace
    public boolean updateEventSpace(EventSpace eventSpace) {
        if (eventSpaceRepository.getEventSpace(eventSpace.getSpaceId()) != null) {
            eventSpaceRepository.updateEventSpace(eventSpace);
            return true;
        }
        return false;
    }

    // Delete an EventSpace
    public boolean deleteEventSpace(String spaceId) {
        if (eventSpaceRepository.getEventSpace(spaceId) != null) {
            eventSpaceRepository.deleteEventSpace(spaceId);
            return true;
        }
        return false;
    }

    // Find available EventSpaces by type
    public List<EventSpace> searchAvailableSpacesByType(String type) {
        return eventSpaceRepository.findAvailableSpacesByType(type);
    }

    // Book an available EventSpace of a given type and show cost
    public void bookEventSpaceByType(String type) {
        List<EventSpace> available = eventSpaceRepository.findAvailableSpacesByType(type);

        if (available.isEmpty()) {
            System.out.println("No available " + type + " event spaces.");
            return;
        }

        EventSpace selected = available.get(0);
        selected.setAvailable(false);
        eventSpaceRepository.updateEventSpace(selected);

        double cost = 0.0;
        boolean hasCatering = false;

        if (selected instanceof GoldEventSpace gold) {
            cost = gold.getBookingCost();
            hasCatering = gold.hasCatering();
        } else if (selected instanceof SilverEventSpace silver) {
            cost = silver.getBookingCost();
            hasCatering = silver.hasCatering();
        } else if (selected instanceof PlatinumEventSpace platinum) {
            cost = platinum.getBookingCost();
            hasCatering = platinum.hasCatering();
        }

        System.out.println("Event space booked: " + selected.getSpaceId());
        System.out.println("Cost: ₹" + cost);
        System.out.println("Catering included: " + (hasCatering ? "Yes" : "No"));
    }
}
