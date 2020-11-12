package capstone.amidst.data;

import capstone.amidst.data.mappers.TaskMapper;
import capstone.amidst.models.Task;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskJdbcRepository implements TaskRepository {

    // Fields
    private final JdbcTemplate jdbcTemplate;

    // Constructor
    public TaskJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Task> findAll() {
        final String sql = "select taskId, taskName, roomId" +
                " from Task;";
        return jdbcTemplate.query(sql, new TaskMapper());
    }

    @Override
    public Task findById(int taskId){
        final String sql = "select taskId, taskName, roomId " +
                "from Task" +
                " where taskId = ?";

        return jdbcTemplate.query(sql, new TaskMapper(), taskId).stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Task> findByRoomId(int roomId){
        final String sql = "select taskId, taskName, roomId " +
                "from Task" +
                " where roomId = ?";

        return jdbcTemplate.query(sql, new TaskMapper(), roomId);
    }


}
