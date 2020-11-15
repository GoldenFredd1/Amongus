package capstone.amidst.data.mappers;

import capstone.amidst.models.PlayerAssignedTask;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PATMapper implements RowMapper<PlayerAssignedTask> {

    @Override
    public PlayerAssignedTask mapRow(ResultSet resultSet, int i) throws SQLException {
        PlayerAssignedTask PAT = new PlayerAssignedTask();
        PAT.setTaskId(resultSet.getInt("taskId"));
        PAT.setTaskName(resultSet.getString("taskName"));
        PAT.setComplete(resultSet.getBoolean("isComplete"));
        return PAT;
    }
}
