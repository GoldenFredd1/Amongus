package capstone.amidst.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;

public class Room {
    @PositiveOrZero(message = "Room ID must be 0 or higher.")
    private int roomId;
    @NotBlank(message = "Room name cannot be empty." )
    private String roomName;

    public Room(){

    }

    public Room(int roomId, String roomName){
        this.roomId = roomId;
        this.roomName = roomName;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return roomId == room.roomId &&
                roomName.equals(room.roomName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, roomName);
    }
}
