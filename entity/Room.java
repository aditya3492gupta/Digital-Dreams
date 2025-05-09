package entity;

public class Room {
    private String roomId;
    private String type; 
    private boolean isAvailable;

    public Room(String roomId, String type, boolean isAvailable) {
        this.roomId = roomId;
        this.type = type;
        this.isAvailable = isAvailable;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId='" + roomId + '\'' +
                ", type='" + type + '\'' +
                ", isAvailable=" + isAvailable +
                '}';
    }
}