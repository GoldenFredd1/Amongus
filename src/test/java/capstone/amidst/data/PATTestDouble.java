package capstone.amidst.data;

import capstone.amidst.models.Game;
import capstone.amidst.models.PlayerAssignedTask;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PATTestDouble implements PlayerAssignedTaskRepository {

    private final ArrayList<PlayerAssignedTask> patArrayList = new ArrayList<>();

    public PATTestDouble(){
        patArrayList.add(new PlayerAssignedTask(1, 1, false));
        patArrayList.add(new PlayerAssignedTask(8, 1, false));
        patArrayList.add(new PlayerAssignedTask(4, 2, false));
        patArrayList.add(new PlayerAssignedTask(10, 2, false));
        patArrayList.add(new PlayerAssignedTask(2, 3, false));
        patArrayList.add(new PlayerAssignedTask(8, 3, false));
        patArrayList.add(new PlayerAssignedTask(3, 4, false));
        patArrayList.add(new PlayerAssignedTask(7, 4, false));
    }

    @Override
    public List<PlayerAssignedTask> findAllByGame(String gameCode) {
        return new ArrayList<>(patArrayList);
    }

    @Override
    public List<PlayerAssignedTask> findAPlayersTasks(String gameCode, int playerId) {
        List<PlayerAssignedTask> specificPlayersList = new ArrayList<>();
        for (PlayerAssignedTask playerAssignedTask : patArrayList) {
            if (playerAssignedTask.getPlayerId() == playerId) {
                specificPlayersList.add(playerAssignedTask);
            }
        }
        return specificPlayersList;
    }

    @Override
    public PlayerAssignedTask findById(int taskId) {
        for(PlayerAssignedTask pat: patArrayList){
            if(pat.getTaskId() == taskId){
                return pat;
            }
        }
        return null;
    }

    @Override
    public PlayerAssignedTask specificTask(String gameCode, int taskId) {
        return null;
    }

    @Override
    public PlayerAssignedTask addTask(PlayerAssignedTask PAT) {
        return null;
    }

    @Override
    public Boolean updateTask(PlayerAssignedTask PAT) {
        PAT.setComplete(true);
        return true;
    }
}
