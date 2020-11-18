package capstone.amidst.controllers;

import capstone.amidst.domain.*;
import capstone.amidst.models.Game;
import capstone.amidst.models.PlayerAssignedTask;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
public class PlayerAssignedTaskController {

    private final PlayerAssignedTaskService service;
    private final ComputerPlayers computerPlayers;

    public PlayerAssignedTaskController(PlayerAssignedTaskService service, ComputerPlayers computerPlayers) {
        this.service = service;
        this.computerPlayers = computerPlayers;
    }

    // Mappings
    @GetMapping("/assignedTask")
    public List<PlayerAssignedTask> displayAllTasksForAPlayer(@RequestBody Game game) {
        return service.findAPlayersTasks(game.getGameRoomCode(), game.getPlayerId());
    }

    @GetMapping("/assignedTask/{taskId}")
    public ResponseEntity<PlayerAssignedTask> displayATask(@PathVariable int taskId, @RequestBody Game game) {
        PlayerAssignedTask PAT = service.specificTask(game.getGameRoomCode(), taskId);
        if(PAT == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(PAT);
    }

    @GetMapping("/assignedTask/{gameRoomCode}/{playerId}")
    public ResponseEntity<List<PlayerAssignedTask>> displayTasksByPlayerId(@PathVariable String gameRoomCode, @PathVariable int playerId) {
        List<PlayerAssignedTask> PAT = service.findAPlayersTasks(gameRoomCode, playerId);
        if(PAT == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(PAT);
    }

    @PostMapping("/assignedTask")
    public ResponseEntity<Object> addPlayerTask(@RequestBody PlayerAssignedTask PAT) {
        //System.out.println("You've made it to the controller!");

        Result<PlayerAssignedTask> result = service.addTask(PAT);
        if (result.getType() == ResultType.INVALID) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
    }

    @PutMapping("/assignedTask/{taskId}")
    public ResponseEntity<Object> updateTask(@PathVariable int taskId, @RequestBody Game game) {
        PlayerAssignedTask PAT = service.findById(taskId);
        if(taskId != PAT.getTaskId()){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<PlayerAssignedTask> result = service.update(PAT);
        if (result.getType() == ResultType.INVALID) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        computerPlayers.ComputerPlayersMovement(game);

        return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
    }
}
