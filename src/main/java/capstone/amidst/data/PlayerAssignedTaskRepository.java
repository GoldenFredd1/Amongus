package capstone.amidst.data;

import capstone.amidst.models.PlayerAssignedTask;

import java.util.List;

public interface PlayerAssignedTaskRepository {
    //find all tasks per game
    List<PlayerAssignedTask> findAllByGame(String gameCode);

    //find all tasks for a player in a game
    List<PlayerAssignedTask> findAPlayersTasks(String gameCode, int playerId);

    //find a specific task based off taskId
    PlayerAssignedTask specificTask(String gameCode, int taskId);

    Boolean updateTask(PlayerAssignedTask PAT);
}
