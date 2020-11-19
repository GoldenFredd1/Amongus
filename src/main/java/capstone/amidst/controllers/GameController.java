package capstone.amidst.controllers;

import capstone.amidst.domain.*;
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
    private final ComputerPlayers computerPlayers;
    private final PlayerService playerService;


    public GameController(GameService service, ComputerPlayers computerPlayers, PlayerService playerService) {
        this.service = service;
        this.computerPlayers = computerPlayers;
        this.playerService = playerService;
    }

    // Mappings
    @GetMapping("/game/{gameId}")
    public Game displayByGameID(@PathVariable int gameId){
        return service.findById(gameId);
    }

    @GetMapping("/game/gameOver/{gameRoomCode}")

    public Boolean isGameOver(@PathVariable String gameRoomCode) {
        System.out.println("You've made it to isGameOver in the Game Controller.");
        return service.isGameOver(gameRoomCode);
    }

    @GetMapping("/game/whoWon/{gameRoomCode}")
    public Boolean didImposterWin(@PathVariable String gameRoomCode) {
        System.out.println("You've made it to didImposterWin in the Game Controller.");
        return service.didImposterWin(gameRoomCode);
    }

    @GetMapping("/game/bodyCheck/{gameRoomCode}")
    public Boolean deadBodyCheck(@PathVariable String gameRoomCode){
        Game game = service.findByGameRoomCode(gameRoomCode);
        System.out.println("Checking for a dead body don't mind me..");
        return service.deadBodyInRoomCheck(game);
    }

    @GetMapping("/game/findReal/{playerId}/{gameRoomCode}")
    public Game findByGameUserId(@PathVariable int playerId, @PathVariable String gameRoomCode){
        return service.findByPlayerGame(gameRoomCode, playerId);
    }

    @GetMapping("/game/findPlayers/{gameRoomCode}/{playerId}")
    public List<Player> findListOfPlayersInRoom(@PathVariable int playerId, @PathVariable String gameRoomCode){
        return service.findAllPlayersByCode(gameRoomCode, playerId);
    }

    @PostMapping("/game")
    public ResponseEntity<Object> addGame(@RequestBody Game game) {
        Result<Game> result = service.add(game);
        if (result.getType() == ResultType.INVALID) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
//        playerService.updateResetPlayer(playerService.findById(game.getPlayerId()));
        return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
    }

    @DeleteMapping("/game/{gameId}")
    public ResponseEntity<Void> deleteGame(@PathVariable int gameId) {
        if(service.deleteById(gameId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/game/updateGame/{gameId}")
    public ResponseEntity<Game> update(@PathVariable int gameId, @RequestBody Game game) {
        System.out.println("You made it to the game update??");
        if (gameId != game.getGameId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Game> result = service.update(game);
        if (result.getType() == ResultType.INVALID) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        System.out.println("You updated the game!!!!!");

        //once a piece of the game has been updated ->
        computerPlayers.ComputerPlayersMovement(game);

        return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
    }

}
