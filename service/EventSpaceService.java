package service;

import entity.EventSpace;
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
}
