package capstone.amidst.data;

import capstone.amidst.data.mappers.RoomMapper;
import capstone.amidst.models.Room;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoomJdbcRepository implements RoomRepository {

    private final JdbcTemplate jdbcTemplate;

    public RoomJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Room> findAll(){
        final String sql = "select roomId, roomName" +
                " from Room;";
        return jdbcTemplate.query(sql, new RoomMapper());
    }

    @Override
    public Room findById(int roomId){
        final String sql = "select roomId, roomName " +
                "from Room " +
                "where roomId = ?";

        return jdbcTemplate.query(sql, new RoomMapper(), roomId).stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Room findByRoomName(String roomName) {
        final String sql = "select roomId, roomName " +
                "from Room " +
                "where roomName = ?";

        return jdbcTemplate.query(sql, new RoomMapper(), roomName).stream()
                .findFirst()
                .orElse(null);
    }

}
