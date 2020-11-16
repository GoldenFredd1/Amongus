package capstone.amidst.data;

import capstone.amidst.models.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskTestDouble implements TaskRepository{

    private final ArrayList<Task> taskArrayList = new ArrayList<>();

    public TaskTestDouble() {
        taskArrayList.add(new Task(1,"Pickup trash",1));
        taskArrayList.add(new Task(2,"Collect moldy sandwich",1));
        taskArrayList.add(new Task(3,"Pickout some fabulous shoes",2));
        taskArrayList.add(new Task(4,"Smash bugs with shoes",2));
        taskArrayList.add(new Task(5,"Shoot all the targets at the nerf range",3));
        taskArrayList.add(new Task(6,"Put the dolls to bed",3));
        taskArrayList.add(new Task(7,"Re-wire a fridge",4));
        taskArrayList.add(new Task(8,"Setup a christmas tree",4));
        taskArrayList.add(new Task(9,"Chase down mice with an RC car",5));
        taskArrayList.add(new Task(10,"Check for usable batteries",5));
        taskArrayList.add(new Task(11,"Find a fancy hat",6));
        taskArrayList.add(new Task(12,"Put makeup on a mannequin",6));
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(taskArrayList);
    }

    @Override
    public Task findById(int taskId) {
        for(Task t: taskArrayList){
            if( t.getTaskId()== taskId){
                return t;
            }
        }
        return null;
    }

    @Override
    public List<Task> findByRoomId(int roomId) {
        List<Task> taskInRoom = new ArrayList<>();
        for(Task t: taskArrayList){
            if( t.getRoomId()== roomId){
                taskInRoom.add(t);
            }
        }
        return taskInRoom;
    }
}
