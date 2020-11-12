package capstone.amidst.domain;

import capstone.amidst.data.RoomRepository;
import capstone.amidst.models.Room;

import java.util.List;

public class RoomService {
    private final RoomRepository repository;

    public RoomService(RoomRepository repository) {
        this.repository = repository;
    }

    public List<Room> findAll(){
        return repository.findAll();
    }

    public Room findById(int roomId){
        return repository.findById(roomId);
    }

    public Room findByRoomName(String roomName){
        return repository.findByRoomName(roomName);
    }
}
