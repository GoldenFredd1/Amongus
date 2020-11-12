package capstone.amidst.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;

public class Task {
    @PositiveOrZero(message = "Task ID must be 0 or higher.")
    private int taskId;

    @NotBlank(message = "Task name cannot be empty." )
    private String taskName;

    @PositiveOrZero(message = "Room ID must be 0 or higher.")
    private int roomId;


    public Task() {
    }

    public Task(int taskId, String taskName, int roomId){
        this.taskId = taskId;
        this.taskName = taskName;
        this.roomId = roomId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return taskId == task.taskId &&
                roomId == task.roomId &&
                taskName.equals(task.taskName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, taskName, roomId);
    }
}
