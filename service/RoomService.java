package service;

import java.util.List;

import entity.Room;
import repository.RoomRepository;Repository;

public class RoomService {
    private RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public boolean addRoom(String roomId, String type, boolean isAvailable) {
        Room room = new Room(roomId, type, isAvailable);
        return roomRepository.addRoom(room);
    }

    public Room bookRoom(String type) {
        return roomRepository.bookRoom(type);
    }

    public boolean releaseRoom(String roomId) {
        return roomRepository.releaseRoom(roomId);
    }

    public List<Room> getAllRooms() {
        return roomRepository.getAllRooms();
    }

    public void showAvailableRooms() {
        roomRepository.showAvailableRooms();
    }

    public Room getRoomById(String roomId) {
        return roomRepository.getRoomById(roomId);
    }
}