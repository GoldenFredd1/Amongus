package capstone.amidst.data.mappers;

import capstone.amidst.models.Room;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomMapper implements RowMapper<Room> {

    @Override
    public Room mapRow(ResultSet resultSet, int i) throws SQLException {
        Room room = new Room();
        room.setRoomId(resultSet.getInt("roomId"));
        room.setRoomName(resultSet.getString("roomName"));
        return room;
    }
}
