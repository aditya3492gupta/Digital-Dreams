package repository;

import entity.EventSpace;

import java.util.*;

public class EventSpaceRepository {
    private Map<String, EventSpace> eventSpaceMap = new HashMap<>();

    // Add a new EventSpace
    public void addEventSpace(EventSpace eventSpace) {
        eventSpaceMap.put(eventSpace.getSpaceId(), eventSpace);
    }

    // Get an EventSpace by spaceId
    public EventSpace getEventSpace(String spaceId) {
        return eventSpaceMap.get(spaceId);
    }

    // Get all EventSpaces
    public List<EventSpace> getAllEventSpaces() {
        return new ArrayList<>(eventSpaceMap.values());
    }

    // Update an existing EventSpace
    public void updateEventSpace(EventSpace eventSpace) {
        if (eventSpaceMap.containsKey(eventSpace.getSpaceId())) {
            eventSpaceMap.put(eventSpace.getSpaceId(), eventSpace);
        }
    }

    // Delete an EventSpace by spaceId
    public void deleteEventSpace(String spaceId) {
        eventSpaceMap.remove(spaceId);
    }

    // Find available EventSpaces by type
    public List<EventSpace> findAvailableSpacesByType(String type) {
        List<EventSpace> result = new ArrayList<>();
        for (EventSpace space : eventSpaceMap.values()) {
            if (space.getType().equalsIgnoreCase(type) && space.isAvailable()) {
                result.add(space);
            }
        }
        return result;
    }
}
