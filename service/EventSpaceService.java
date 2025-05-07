package service;

import entity.*;
import java.util.List;
import repository.EventSpaceRepository;

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

    /**
     * Book a specific event space by its ID
     * @param spaceId the ID of the event space to book
     * @return the booked EventSpace if successful, null if the space doesn't exist or is already booked
     */
    public EventSpace bookEventSpace(String spaceId) {
        EventSpace space = eventSpaceRepository.getEventSpace(spaceId);
        if (space != null && space.isAvailable()) {
            space.setAvailable(false);
            eventSpaceRepository.updateEventSpace(space);
            
            System.out.println("Event space booked: " + space.getSpaceId());
            printBookingDetails(space);
            return space;
        }
        
        if (space == null) {
            System.out.println("Event space with ID " + spaceId + " not found.");
        } else {
            System.out.println("Event space " + spaceId + " is already booked.");
        }
        return null;
    }

    /**
     * Book an available EventSpace of a given type
     * @param type the type of event space to book (e.g., "Gold", "Silver", "Platinum")
     * @return the booked EventSpace if successful, null if no spaces of that type are available
     */
    public EventSpace bookEventSpaceByType(String type) {
        List<EventSpace> available = eventSpaceRepository.findAvailableSpacesByType(type);

        if (available.isEmpty()) {
            System.out.println("No available " + type + " event spaces.");
            return null;
        }

        EventSpace selected = available.get(0);
        selected.setAvailable(false);
        eventSpaceRepository.updateEventSpace(selected);

        System.out.println("Event space booked: " + selected.getSpaceId());
        printBookingDetails(selected);
        return selected;
    }
    
    /**
     * Release a booked event space (make it available again)
     * @param spaceId the ID of the event space to release
     * @return true if the space was successfully released, false otherwise
     */
    public boolean releaseEventSpace(String spaceId) {
        EventSpace space = eventSpaceRepository.getEventSpace(spaceId);
        if (space != null && !space.isAvailable()) {
            space.setAvailable(true);
            eventSpaceRepository.updateEventSpace(space);
            System.out.println("Event space released: " + spaceId);
            return true;
        }
        
        if (space == null) {
            System.out.println("Event space with ID " + spaceId + " not found.");
        } else {
            System.out.println("Event space " + spaceId + " is already available.");
        }
        return false;
    }
    
    // Helper method to print booking details
    private void printBookingDetails(EventSpace space) {
        double cost = 0.0;
        boolean hasCatering = false;

        if (space instanceof GoldEventSpace gold) {
            cost = gold.getBookingCost();
            hasCatering = gold.hasCatering();
        } else if (space instanceof SilverEventSpace silver) {
            cost = silver.getBookingCost();
            hasCatering = silver.hasCatering();
        } else if (space instanceof PlatinumEventSpace platinum) {
            cost = platinum.getBookingCost();
            hasCatering = platinum.hasCatering();
        }

        System.out.println("Cost: ₹" + cost);
        System.out.println("Catering included: " + (hasCatering ? "Yes" : "No"));
    }
}