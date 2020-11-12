package capstone.amidst.domain;

import capstone.amidst.data.TaskRepository;
import capstone.amidst.models.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository repository;

    public TaskService(TaskRepository repository){
        this.repository = repository;
    }

    //Methods
    public List<Task> findAll(){
        return repository.findAll();
    }

    public Task findById(int taskId){
        return repository.findById(taskId);
    }

    public List<Task> findByRoomId(int roomId){
        return repository.findByRoomId(roomId);
    }

}
