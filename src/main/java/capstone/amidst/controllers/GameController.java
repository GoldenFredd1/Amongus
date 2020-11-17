package capstone.amidst.controllers;

import capstone.amidst.domain.GameService;
import capstone.amidst.domain.PlayerService;
import capstone.amidst.domain.Result;
import capstone.amidst.domain.ResultType;
import capstone.amidst.models.Game;
import capstone.amidst.models.Player;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
public class GameController {

    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    // Mappings
    @GetMapping("/game")
    public Game displayById(@PathVariable int gameId) {
        return service.findById(gameId);
    }

    @GetMapping("/game/{gameRoomCode}")
    public Boolean isGameOver(@PathVariable String gameRoomCode) {
        System.out.println("You've made it to isGameOver in the Game Controller.");
        return service.isGameOver(gameRoomCode);
    }

    @PostMapping("/game")
    public ResponseEntity<Object> addGame(@RequestBody Game game) {
//        System.out.println("You've made it to addGame in the Game Controller.");
        Result<Game> result = service.add(game);
        if (result.getType() == ResultType.INVALID) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
    }

    @DeleteMapping("/game/{gameId}")
    public ResponseEntity<Void> deleteGame(@PathVariable int gameId) {
        if(service.deleteById(gameId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/game/{gameId}")
    public ResponseEntity<Game> update(@PathVariable int gameId, @RequestBody Game game) {
        System.out.println("You made it here??");
        if (gameId != game.getGameId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Game> result = service.update(game);
        if (result.getType() == ResultType.INVALID) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        System.out.println("YOu updated the game!!!!!");
        return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
    }

}
