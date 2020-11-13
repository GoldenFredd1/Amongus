package capstone.amidst.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;

public class PlayerAssignedTask {
    @PositiveOrZero(message = "Task ID must be 0 or higher.")
    private int taskId;
    @PositiveOrZero(message = "Player ID must be 0 or higher.")
    private int playerId;
    @NotNull(message = "Task must be either an complete or incomplete.")
    private boolean isComplete;



    public PlayerAssignedTask() {
    }

    public PlayerAssignedTask(int taskId, int playerId, boolean isComplete) {
        this.taskId = taskId;
        this.playerId = playerId;
        this.isComplete = isComplete;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerAssignedTask that = (PlayerAssignedTask) o;
        return taskId == that.taskId &&
                playerId == that.playerId &&
                isComplete == that.isComplete;
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, playerId, isComplete);
    }
}
