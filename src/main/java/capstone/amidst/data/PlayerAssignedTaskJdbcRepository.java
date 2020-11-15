package capstone.amidst.data;

import capstone.amidst.data.mappers.PATMapper;
import capstone.amidst.data.mappers.PlayerAssignedTaskMapper;
import capstone.amidst.models.PlayerAssignedTask;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlayerAssignedTaskJdbcRepository implements PlayerAssignedTaskRepository {

    private final JdbcTemplate jdbcTemplate;

    public PlayerAssignedTaskJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //find all tasks per game
    @Override
    public List<PlayerAssignedTask> findAllByGame(String gameCode){
        final String sql = "select pat.taskId, pat.playerId, pat.isComplete " +
                "from Player_Assigned_Task pat " +
                "join  Player p on p.playerId = pat.playerId " +
                "join Game g on g.playerId = p.playerId " +
                "where g.gameRoomCode = ?;";
        return  jdbcTemplate.query(sql,new PlayerAssignedTaskMapper(),gameCode);
    }

    //find all tasks for a player in a game
    @Override
    public List<PlayerAssignedTask> findAPlayersTasks(String gameCode, int playerId){
        final String sql = "Select pat.taskId, t.taskName, pat.isComplete " +
                "from Player_Assigned_Task pat " +
                "join Task t on t.taskId = pat.taskId " +
                "join  Player p on p.playerId = pat.playerId  " +
                "join Game g on g.playerId = p.playerId  " +
                " where g.gameRoomCode = ? and p.playerId =?;";
        return  jdbcTemplate.query(sql,new PATMapper(),gameCode, playerId);
    }

    //find a specific task based off taskId and gameCode
    @Override
    public PlayerAssignedTask specificTask(String gameCode, int taskId) {
        final String sql = "Select pat.taskId, pat.playerId, pat.isComplete " +
                "from Player_Assigned_Task pat " +
                "join  Player p on p.playerId = pat.playerId  " +
                "join Game g on g.playerId = p.playerId  " +
                "where g.gameRoomCode = ?  and taskId = ?;";
        return jdbcTemplate.query(sql, new PlayerAssignedTaskMapper(),gameCode, taskId)
                .stream()
                .findFirst()
                .orElse(null);
    }
    @Override
    public Boolean updateTask(PlayerAssignedTask PAT){
        final String sql="Update Player_Assigned_Task set " +
                "isComplete = true " +
                "where taskId = ?;";

        return jdbcTemplate.update(sql, PAT.getTaskId()) > 0;
    }
}
