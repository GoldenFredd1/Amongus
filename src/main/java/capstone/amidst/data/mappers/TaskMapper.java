package capstone.amidst.data.mappers;

import capstone.amidst.models.Player;
import capstone.amidst.models.Task;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskMapper implements RowMapper<Task> {

    @Override
    public Task mapRow(ResultSet resultSet, int i) throws SQLException {
        Task task = new Task();
        task.setTaskId(resultSet.getInt("taskId"));
        task.setTaskName(resultSet.getString("taskName"));
        task.setRoomId(resultSet.getInt("roomId"));
        return task;
    }
}
