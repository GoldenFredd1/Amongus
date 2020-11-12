package capstone.amidst.data;

import capstone.amidst.models.Room;

import java.util.List;

public interface RoomRepository {
    List<Room> findAll();

    Room findById(int roomId);

    Room findByRoomName(String roomName);
}
