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
    private final PlayerService playerService;
    private final GameService gameService;

    public PlayerAssignedTaskController(PlayerAssignedTaskService service, ComputerPlayers computerPlayers, PlayerService playerService, GameService gameService) {
        this.service = service;
        this.computerPlayers = computerPlayers;
        this.playerService = playerService;
        this.gameService = gameService;
    }

    // Mappings
    @GetMapping("/assignedTask")
    public List<PlayerAssignedTask> displayAll() {
        return service.findAPlayersTasks("HELPME", 1);
    }



    @GetMapping("/assignedTask/{taskId}")
    public ResponseEntity<PlayerAssignedTask> displayAll(@PathVariable int taskId) {
        PlayerAssignedTask PAT = service.specificTask("HELPME", taskId);
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
    public ResponseEntity<Object> updateTask(@PathVariable int taskId, @RequestBody PlayerAssignedTask PAT, @RequestBody Game game) {
        PAT = service.findById(taskId);
        if(taskId != PAT.getTaskId()){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<PlayerAssignedTask> result = service.update(PAT);
        if (result.getType() == ResultType.INVALID) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //TODO Trigger the computer players when a task is updated by player.
        computerPlayers.ComputerPlayersMovement(game);

        return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
    }
}
