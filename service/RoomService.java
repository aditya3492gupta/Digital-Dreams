package service;

import entity.Room;
import repository.RoomRepository;

public class RoomService {
    private RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public boolean addRoom(String roomId, String type, boolean isAvailable, double cost) {
        Room room = new Room(roomId, type, isAvailable, cost);
        return roomRepository.addRoom(room);
    }

    public Room bookRoom(String type) {
        return roomRepository.bookRoom(type);
    }

    /**
     * Books a specific room by its ID.
     * 
     * @param roomId The ID of the room to book
     * @return true if booking was successful, false otherwise
     */
    public boolean bookSpecificRoom(String roomId) {
        Room room = roomRepository.getRoomById(roomId);
        if (room != null && room.isAvailable()) {
            room.setAvailable(false);
            roomRepository.updateRoom(room);
            System.out.println("Room booked: " + room.getRoomId() + " - " + room.getType());
            return true;
        }
        System.out.println("Room " + roomId + " is not available for booking.");
        return false;
    }

    public boolean releaseRoom(String roomId) {
        return roomRepository.releaseRoom(roomId);
    }

    public void getAllRooms() {
        roomRepository.getAllRooms();
    }

    public void showAvailableRooms() {
        roomRepository.showAvailableRooms();
    }

    // delete room by id
    public boolean deleteRoom(String roomId) {
        return roomRepository.deleteRoom(roomId);
    }

    public Room getRoomById(String roomId) {
        return roomRepository.getRoomById(roomId);
    }

    

    public boolean updateRoom(Room room) {
        if (room == null) {
            System.out.println("Cannot update: room is null");
            return false;
        }

        Room existingRoom = roomRepository.getRoomById(room.getRoomId());

        if (existingRoom == null) {
            System.out.println("Cannot update: room not found with ID: " + room.getRoomId());
            return false;
        }

        boolean updated = roomRepository.updateRoom(room);

        if (updated) {
            System.out.println("Successfully updated room: " + room.getRoomId());
            if (!existingRoom.getType().equals(room.getType())) {
                System.out.println("Room type changed from " + existingRoom.getType() + " to " + room.getType());
            }
        } else {
            System.out.println("Failed to update room: " + room.getRoomId());
        }

        return updated;
    }
}