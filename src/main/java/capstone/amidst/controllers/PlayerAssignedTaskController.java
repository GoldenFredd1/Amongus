package capstone.amidst.controllers;

import capstone.amidst.domain.PlayerAssignedTaskService;
import capstone.amidst.models.PlayerAssignedTask;
import capstone.amidst.models.Task;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
public class PlayerAssignedTaskController {

    private final PlayerAssignedTaskService service;

    public PlayerAssignedTaskController(PlayerAssignedTaskService service) {
        this.service = service;
    }

    // Mappings
    @GetMapping("/assignedTask")
    public List<PlayerAssignedTask> displayAll() {
        return service.findAPlayersTasks("HELPME", 1);
    }
}
