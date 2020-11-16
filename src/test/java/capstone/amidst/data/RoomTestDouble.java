package capstone.amidst.data;

import capstone.amidst.models.Game;
import capstone.amidst.models.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomTestDouble implements RoomRepository {

    private ArrayList<Room> roomArrayList = new ArrayList<>();

    public RoomTestDouble() {
        roomArrayList.add(new Room(1,"Food Court"));
        roomArrayList.add(new Room(2,"Payless ShoeSource"));
        roomArrayList.add(new Room(3,"Toys R Us"));
        roomArrayList.add(new Room(4,"Sears"));
        roomArrayList.add(new Room(5,"Radio Shack"));
        roomArrayList.add(new Room(6,"Neiman Marcus"));
        roomArrayList.add(new Room(7,"The Hallway of Failure"));

    }

    @Override
    public List<Room> findAll() {
        return new ArrayList<>(roomArrayList);
    }

    @Override
    public Room findById(int roomId) {
        for(Room r: roomArrayList){
            if(r.getRoomId() == roomId){
                return r;
            }
        }
        return null;
    }

    @Override
    public Room findByRoomName(String roomName) {
        for(Room r: roomArrayList){
            if(r.getRoomName().equalsIgnoreCase(roomName)){
                return r;
            }
        }
        return null;
    }
}
