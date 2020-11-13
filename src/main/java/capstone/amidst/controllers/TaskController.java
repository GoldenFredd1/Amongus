package capstone.amidst.controllers;

import capstone.amidst.domain.PlayerService;
import capstone.amidst.domain.TaskService;
import capstone.amidst.models.Player;
import capstone.amidst.models.Task;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    // Mappings
    @GetMapping("/task")
    public List<Task> displayAll() {
        return service.findAll();
    }
}
