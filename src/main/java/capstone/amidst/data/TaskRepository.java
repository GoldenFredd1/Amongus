package capstone.amidst.data;

import capstone.amidst.models.Task;

import java.util.List;

public interface TaskRepository {
    List<Task> findAll();

    Task findById(int taskId);

    List<Task> findByRoomId(int roomId);
}
