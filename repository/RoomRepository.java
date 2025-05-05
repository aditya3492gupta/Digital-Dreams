package repository;
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

    // Predefined room types and quantities
    private void initializeRooms() {
        roomInventory.put("2AC", new ArrayList<>());
        roomInventory.put("2NAC", new ArrayList<>());
        roomInventory.put("4AC", new ArrayList<>());
        roomInventory.put("4NAC", new ArrayList<>());

        int defaultQuantity = 5; // You can make this configurable
        for (int i = 1; i <= defaultQuantity; i++) {
            roomInventory.get("2AC").add(new Room("2AC" + i, "2AC", true));
            roomInventory.get("2NAC").add(new Room("2NAC" + i, "2NAC", true));
            roomInventory.get("4AC").add(new Room("4AC" + i, "4AC", true));
            roomInventory.get("4NAC").add(new Room("4NAC" + i, "4NAC", true));
        }
    }

    // Get all rooms of a type
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

    // Show current availability
    public void showAvailableRooms() {
        System.out.println("Available Rooms:");
        for (Map.Entry<String, List<Room>> entry : roomInventory.entrySet()) {
            long available = entry.getValue().stream().filter(Room::isAvailable).count();
            System.out.println(entry.getKey() + ": " + available + " available");
        }
    }
}