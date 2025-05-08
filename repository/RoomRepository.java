package repository;

import entity.Room;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomRepository {
    private Map<String, List<Room>> roomInventory;

    public RoomRepository() {
        roomInventory = new HashMap<>();
        initializeRooms();
    }

    private void initializeRooms() {
        roomInventory.put("2AC", new ArrayList<>());
        roomInventory.put("2NAC", new ArrayList<>());
        roomInventory.put("4AC", new ArrayList<>());
        roomInventory.put("4NAC", new ArrayList<>());

        int defaultQuantity = 5;

        for (int i = 1; i <= defaultQuantity; i++) {
            roomInventory.get("2AC").add(new Room("2AC" + i, "2AC", true, 1200.0));
            roomInventory.get("2NAC").add(new Room("2NAC" + i, "2NAC", true, 800.0));
            roomInventory.get("4AC").add(new Room("4AC" + i, "4AC", true, 1800.0));
            roomInventory.get("4NAC").add(new Room("4NAC" + i, "4NAC", true, 1400.0));
        }
    }

    public boolean addRoom(Room room) {
        roomInventory.putIfAbsent(room.getType(), new ArrayList<>());

        for (Room r : roomInventory.get(room.getType())) {
            if (r.getRoomId().equalsIgnoreCase(room.getRoomId())) {
                return false;
            }
        }

        roomInventory.get(room.getType()).add(room);
        return true;
    }

    public List<Room> getRoomsByType(String type) {
        return roomInventory.getOrDefault(type, new ArrayList<>());
    }

    // Book an available room of the given type
    public Room bookRoom(String type) {
        List<Room> rooms = roomInventory.get(type);
        if (rooms != null) {
            for (Room room : rooms) {
                if (room.isAvailable()) {
                    room.setAvailable(false);
                    System.out.println("Room booked: " + room.getRoomId());
                    return room;
                }
            }
        }
        System.out.println("No available room for type: " + type);
        return null;
    }

    // Release a room based on roomId
    public boolean releaseRoom(String roomId) {
        for (List<Room> rooms : roomInventory.values()) {
            for (Room room : rooms) {
                if (room.getRoomId().equals(roomId)) {
                    if (!room.isAvailable()) {
                        room.setAvailable(true);
                        System.out.println("Room released: " + roomId);
                        return true;
                    } else {
                        System.out.println("Room " + roomId + " is already available.");
                        return false;
                    }
                }
            }
        }
        System.out.println("Room not found: " + roomId);
        return false;
    }

    // Delete room by roomId
    public boolean deleteRoom(String roomId) {
        for (List<Room> rooms : roomInventory.values()) {
            rooms.removeIf(room -> room.getRoomId().equalsIgnoreCase(roomId));
            System.out.println("Room deleted: " + roomId);
            return true;
        }
        System.out.println("Room not found: " + roomId);
        return false;
    }

    // Show current availability
    public void showAvailableRooms() {
        System.out.println("Available Rooms:");
        for (Map.Entry<String, List<Room>> entry : roomInventory.entrySet()) {
            long available = entry.getValue().stream().filter(Room::isAvailable).count();
            System.out.println(entry.getKey() + ": " + available + " available");
        }
    }

    // Get all rooms in the inventory
    public List<Room> getAllRooms() {
        List<Room> allRooms = new ArrayList<>();
        for (List<Room> rooms : roomInventory.values()) {
            allRooms.addAll(rooms);
        }
        return allRooms;
    }

    // Get a room by its ID
    public Room getRoomById(String roomId) {
        for (List<Room> rooms : roomInventory.values()) {
            for (Room room : rooms) {
                if (room.getRoomId().equalsIgnoreCase(roomId)) {
                    return room;
                }
            }
        }
        return null;
    }

    /**
     * Updates an existing room in the repository.
     * If the room type is changed, the room will be moved to the new type's list.
     * 
     * @param updatedRoom The room with updated information
     * @return true if the room was successfully updated, false otherwise
     */
    public boolean updateRoom(Room updatedRoom) {
        if (updatedRoom == null) {
            return false;
        }

        // First, find and remove the existing room
        Room existingRoom = null;
        String existingType = null;

        for (Map.Entry<String, List<Room>> entry : roomInventory.entrySet()) {
            for (Room room : entry.getValue()) {
                if (room.getRoomId().equalsIgnoreCase(updatedRoom.getRoomId())) {
                    existingRoom = room;
                    existingType = entry.getKey();
                    break;
                }
            }
            if (existingRoom != null) {
                break;
            }
        }

        // If room not found, return false
        if (existingRoom == null) {
            return false;
        }

        // If the room type has changed, handle moving it to the new type list
        if (!existingType.equals(updatedRoom.getType())) {
            // Remove from old type list
            roomInventory.get(existingType).remove(existingRoom);

            // Add to new type list (ensure the list exists)
            roomInventory.putIfAbsent(updatedRoom.getType(), new ArrayList<>());
            roomInventory.get(updatedRoom.getType()).add(updatedRoom);
        } else {
            // Simply replace the room in its existing list
            int index = roomInventory.get(existingType).indexOf(existingRoom);
            roomInventory.get(existingType).set(index, updatedRoom);
        }

        return true;
    }
}